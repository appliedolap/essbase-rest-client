package com.appliedolap.essbase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * One line of an Essbase log, read apart into its fields.
 *
 * <p>Essbase writes ODL (Oracle Diagnostic Logging), which is a timestamp and a run of bracketed
 * fields followed by the message:
 *
 * <pre>
 * [2026-09-10T20:57:04.112+00:00] [Sample] [ERROR:32] [OTLAPI-54] [OTLAPI] [ecid: 178...,0]
 *     [tid: 140680831694400] [REQ_ID: 6aa3199e00000021] [DBNAME: Basic] Cannot find generation...
 * </pre>
 *
 * <p>The first five brackets are positional - timestamp, application, severity, message id,
 * component. The ones after them are named, {@code name: value}, and which of them appear varies by
 * the kind of event: a request carries {@code REQ_ID} and {@code DBNAME}, a startup message carries
 * neither. They are kept in {@link #getFields()} rather than modelled, for the same reason a member's
 * outline row is.
 *
 * <p>A line that doesn't parse is not discarded. It comes back with its text in {@link #getMessage()}
 * and everything else null, because a log you can only read the well-formed half of is worse than no
 * parsing at all.
 */
public final class EssLogEntry {

    /**
     * ODL severities, in the order Oracle ranks them. The log writes each as {@code NAME:level} - the
     * level is a finer grade within the severity, which is why {@code NOTIFICATION:16} and
     * {@code WARNING:1} both occur and the number doesn't order them.
     */
    public enum Severity {

        INCIDENT_ERROR,

        ERROR,

        WARNING,

        NOTIFICATION,

        TRACE,

        /** A severity this doesn't name, or a line that didn't parse. */
        UNKNOWN;

        public static Severity parse(String text) {
            if (text == null) {
                return UNKNOWN;
            }
            String name = text.toUpperCase(Locale.ROOT).replaceAll("[^A-Z]", "");
            for (Severity severity : values()) {
                if (severity.name().replace("_", "").equals(name)) {
                    return severity;
                }
            }
            return UNKNOWN;
        }

        /** Whether this is something gone wrong, as opposed to something merely reported. */
        public boolean isProblem() {
            return this == ERROR || this == INCIDENT_ERROR || this == WARNING;
        }

    }

    private final Instant timestamp;

    private final String application;

    private final Severity severity;

    private final int severityLevel;

    private final String messageId;

    private final String component;

    private final Map<String, String> fields;

    private final String message;

    private final String raw;

    private EssLogEntry(Instant timestamp, String application, Severity severity, int severityLevel,
                        String messageId, String component, Map<String, String> fields, String message, String raw) {
        this.timestamp = timestamp;
        this.application = application;
        this.severity = severity;
        this.severityLevel = severityLevel;
        this.messageId = messageId;
        this.component = component;
        this.fields = fields;
        this.message = message;
        this.raw = raw;
    }

    /** When the event happened, or null for a line that didn't parse. */
    public Instant getTimestamp() {
        return timestamp;
    }

    /** The application the event belongs to, or null on a server-level line. */
    public String getApplication() {
        return application;
    }

    public Severity getSeverity() {
        return severity;
    }

    /** The numeric grade beside the severity - the 32 of {@code ERROR:32} - or -1 if there wasn't one. */
    public int getSeverityLevel() {
        return severityLevel;
    }

    /** The catalogue number, e.g. {@code OTLAPI-54}, which is what you search Oracle's messages for. */
    public String getMessageId() {
        return messageId;
    }

    /** The subsystem that logged it, e.g. {@code OTLAPI}, {@code REQ}, {@code TCP}. */
    public String getComponent() {
        return component;
    }

    /**
     * The named brackets after the positional ones - {@code ecid}, {@code tid}, {@code REQ_ID},
     * {@code DBNAME} - keyed as the log spells them.
     */
    public Map<String, String> getFields() {
        return fields;
    }

    /** The message text, and for an unparseable line the whole of it. */
    public String getMessage() {
        return message;
    }

    /** The line exactly as it was read. */
    public String getRaw() {
        return raw;
    }

    /** Whether the line was understood. A false here still leaves {@link #getMessage()} usable. */
    public boolean isParsed() {
        return timestamp != null;
    }

    @Override
    public String toString() {
        return raw;
    }

    /**
     * Reads a whole log.
     *
     * <p>Handles the byte-order mark Essbase writes at the head of the file, and folds a line that
     * doesn't begin a new entry into the one before it - a message can contain newlines, and a stack
     * trace split across entries is unreadable.
     *
     * @param stream the log, which is closed by this method
     * @return the entries, oldest first
     * @throws IOException if the log can't be read
     */
    public static List<EssLogEntry> parseAll(InputStream stream) throws IOException {
        List<EssLogEntry> entries = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (first) {
                    line = stripByteOrderMark(line);
                    first = false;
                }
                if (startsEntry(line)) {
                    if (current.length() > 0) {
                        entries.add(parse(current.toString()));
                        current.setLength(0);
                    }
                    current.append(line);
                } else if (current.length() > 0) {
                    current.append('\n').append(line);
                } else if (!line.isBlank()) {
                    entries.add(parse(line));
                }
            }
        }
        if (current.length() > 0) {
            entries.add(parse(current.toString()));
        }
        return entries;
    }

    /**
     * Reads one entry. Never throws and never returns null: a line it can't read comes back with
     * {@link #isParsed()} false and the text intact.
     *
     * @param line the line, which may carry folded continuation lines
     * @return the entry
     */
    public static EssLogEntry parse(String line) {
        String raw = line == null ? "" : line;
        List<String> brackets = new ArrayList<>();
        int cursor = readBrackets(raw, brackets);

        if (brackets.size() < 5) {
            return unparsed(raw);
        }
        Instant timestamp = parseTimestamp(brackets.get(0));
        if (timestamp == null) {
            return unparsed(raw);
        }

        String severityText = brackets.get(2);
        int colon = severityText.lastIndexOf(':');
        int level = -1;
        if (colon >= 0) {
            try {
                level = Integer.parseInt(severityText.substring(colon + 1).trim());
            } catch (NumberFormatException e) {
                colon = -1;
            }
        }

        // Everything past the fifth bracket is "name: value", until the brackets stop and the message
        // begins. A bracket without a colon isn't a field, so it belongs to the message.
        Map<String, String> fields = new LinkedHashMap<>();
        for (String bracket : brackets.subList(5, brackets.size())) {
            int separator = bracket.indexOf(':');
            if (separator < 0) {
                break;
            }
            fields.put(bracket.substring(0, separator).trim(), bracket.substring(separator + 1).trim());
        }

        return new EssLogEntry(timestamp,
                emptyToNull(brackets.get(1)),
                Severity.parse(colon >= 0 ? severityText.substring(0, colon) : severityText),
                level,
                emptyToNull(brackets.get(3)),
                emptyToNull(brackets.get(4)),
                Collections.unmodifiableMap(fields),
                raw.substring(Math.min(cursor, raw.length())).trim(),
                raw);
    }

    /**
     * Consumes the run of {@code [...]} groups at the head of a line, stopping at the first thing that
     * isn't one. Returns where the message begins.
     */
    private static int readBrackets(String line, List<String> into) {
        int cursor = 0;
        while (cursor < line.length() && line.charAt(cursor) == '[') {
            int close = line.indexOf(']', cursor);
            if (close < 0) {
                break;
            }
            String bracket = line.substring(cursor + 1, close);
            // Only the first five brackets are positional; past those, a bracket is a field only if it
            // is "name: value". Anything else starts the message, which may itself begin with a bracket.
            if (into.size() >= 5 && bracket.indexOf(':') < 0) {
                break;
            }
            into.add(bracket);
            cursor = close + 1;
            while (cursor < line.length() && line.charAt(cursor) == ' ') {
                cursor++;
            }
        }
        return cursor;
    }

    private static Instant parseTimestamp(String text) {
        try {
            return OffsetDateTime.parse(text.trim()).toInstant();
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static EssLogEntry unparsed(String raw) {
        return new EssLogEntry(null, null, Severity.UNKNOWN, -1, null, null,
                Collections.emptyMap(), raw, raw);
    }

    private static boolean startsEntry(String line) {
        return line.startsWith("[") && parseTimestamp(bracketAt(line)) != null;
    }

    private static String bracketAt(String line) {
        int close = line.indexOf(']');
        return close < 0 ? "" : line.substring(1, close);
    }

    private static String stripByteOrderMark(String line) {
        return line.startsWith("﻿") ? line.substring(1) : line;
    }

    private static String emptyToNull(String text) {
        return text == null || text.isBlank() ? null : text;
    }

}

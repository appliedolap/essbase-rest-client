package com.appliedolap.essbase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * What a batch outline edit did, read out of the log the server sends back.
 *
 * <p>This class exists because <strong>a batch outline edit that fails returns HTTP 200</strong>.
 * Asking to update a member that does not exist comes back as a perfectly ordinary success whose body
 * says {@code Error[1370271]: ... member [NoSuchMember] not exist} and {@code Total member updated : 0}.
 * So does asking to delete one. Anything that checks the status code and moves on has just silently
 * not edited the outline, which for the one API whose whole purpose is changing an outline in bulk is
 * the worst possible failure mode. {@link #isSuccessful()} is the check that means anything here.
 *
 * <p>Worse, an action the server does not recognise - a misspelled element name - is skipped in
 * silence: no error, no warning, and counts of zero. That is why {@link #getTotalChanged()} is worth
 * asserting on in a test even when there were no errors.
 *
 * <p>The log is free text and Oracle may reword it. Everything here degrades to "no messages parsed,
 * counts of -1" rather than throwing, and {@link #getMessages()} always has the server's own words -
 * so a future wording change costs you the structure, never the information.
 */
public class EssBatchOutlineEditResult {

    /**
     * How the server labels a line: {@code Info[1370001]:}, {@code Warn[1370101]:}, {@code Error[1370271]:}.
     */
    public enum Severity {

        INFO,

        WARNING,

        ERROR

    }

    /** One labelled line of the log, kept whole alongside the bits worth reading separately. */
    public static class Message {

        private final Severity severity;

        private final int number;

        private final String text;

        public Message(Severity severity, int number, String text) {
            this.severity = severity;
            this.number = number;
            this.text = text;
        }

        public Severity getSeverity() {
            return severity;
        }

        /** The Essbase message number, e.g. 1370271 for "member not exist", or -1 if the line had none. */
        public int getNumber() {
            return number;
        }

        /** The whole line, as the server wrote it. */
        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    private static final Pattern LABELLED_LINE =
            Pattern.compile("^(Info|Warn|Warning|Error|Fatal)\\[(\\d+)\\]\\s*:\\s*(.*)$");

    private static final Pattern ADDED = Pattern.compile("Total member added\\s*:\\s*(\\d+)");

    private static final Pattern UPDATED = Pattern.compile("Total member updated\\s*:\\s*(\\d+)");

    private static final Pattern DELETED = Pattern.compile("Total member deleted\\s*:\\s*(\\d+)");

    private final String log;

    private final List<Message> messages;

    private final int added;

    private final int updated;

    private final int deleted;

    /**
     * Public so a caller that already has a log - one captured from a job, or replayed from a file -
     * can read it with the same parsing the live call uses.
     *
     * @param log the server's message log, which may be empty
     */
    public EssBatchOutlineEditResult(String log) {
        this.log = log == null ? "" : log;
        this.messages = parseMessages(this.log);
        this.added = parseCount(ADDED, this.log);
        this.updated = parseCount(UPDATED, this.log);
        this.deleted = parseCount(DELETED, this.log);
    }

    private static List<Message> parseMessages(String log) {
        List<Message> parsed = new ArrayList<>();
        for (String line : log.split("\\R")) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            Matcher matcher = LABELLED_LINE.matcher(trimmed);
            if (!matcher.matches()) {
                continue;
            }
            parsed.add(new Message(severityOf(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)), trimmed));
        }
        return Collections.unmodifiableList(parsed);
    }

    private static Severity severityOf(String label) {
        switch (label) {
            case "Warn":
            case "Warning":
                return Severity.WARNING;
            case "Error":
            case "Fatal":
                return Severity.ERROR;
            default:
                return Severity.INFO;
        }
    }

    /** -1 when the log did not carry this count, which is not the same as zero and must not read as it. */
    private static int parseCount(Pattern pattern, String log) {
        Matcher matcher = pattern.matcher(log);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }

    /**
     * Whether the server reported no errors.
     *
     * <p>The check to make on every batch edit, because the HTTP status will not make it for you.
     * Warnings do not count against this: the commonest one is "member already exist", which is how a
     * re-run of an add reports that there was nothing to do - see {@link #hasWarnings()} if that
     * matters to you.
     *
     * @return true if no message came back labelled Error or Fatal
     */
    public boolean isSuccessful() {
        return getErrors().isEmpty();
    }

    /** @return whether anything came back labelled Warn */
    public boolean hasWarnings() {
        return !getWarnings().isEmpty();
    }

    public List<Message> getErrors() {
        return of(Severity.ERROR);
    }

    public List<Message> getWarnings() {
        return of(Severity.WARNING);
    }

    private List<Message> of(Severity severity) {
        List<Message> matching = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSeverity() == severity) {
                matching.add(message);
            }
        }
        return Collections.unmodifiableList(matching);
    }

    /** Every labelled line, in the order the server wrote them. */
    public List<Message> getMessages() {
        return messages;
    }

    /** @return members added, or -1 if the log did not say */
    public int getAdded() {
        return added;
    }

    /** @return members updated, or -1 if the log did not say */
    public int getUpdated() {
        return updated;
    }

    /** @return members deleted, or -1 if the log did not say */
    public int getDeleted() {
        return deleted;
    }

    /**
     * Everything the edit changed.
     *
     * <p>Worth checking even when {@link #isSuccessful()} is true: an action the server did not
     * recognise is skipped without a word, so a document that changes nothing and reports nothing is
     * the signature of a misspelled action.
     *
     * @return added plus updated plus deleted, or -1 if the log did not carry the counts
     */
    public int getTotalChanged() {
        if (added < 0 || updated < 0 || deleted < 0) {
            return -1;
        }
        return added + updated + deleted;
    }

    /** The server's log, whole and unparsed. */
    public String getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Batch outline edit: added " + added + ", updated " + updated + ", deleted " + deleted
                + ", " + getErrors().size() + " error(s), " + getWarnings().size() + " warning(s)";
    }

}

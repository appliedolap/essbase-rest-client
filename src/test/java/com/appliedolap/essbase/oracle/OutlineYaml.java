package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssMember;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Writes a live Essbase outline out in Jaygrid's YAML format.
 *
 * <p>The companion to {@link GridOracle}: a fixture says what Essbase did to a grid, and this says
 * what outline it did it to. Replaying a fixture against a different outline proves nothing, so the
 * two are written together and travel together.
 *
 * <p>Only the parts a grid operation reads are written - names, aliases, and the hierarchy. Formulas,
 * UDAs and the rest are outline facts that no ad hoc operation consults.
 */
public final class OutlineYaml {

    private OutlineYaml() {
    }

    public static void write(EssCube cube, File file) throws IOException {
        File parent = file.getParentFile();
        if (parent != null && !parent.isDirectory() && !parent.mkdirs()) {
            throw new IOException("Could not create " + parent);
        }
        try (PrintWriter out = new PrintWriter(file, "UTF-8")) {
            out.println("# Generated from " + cube.getApplication().getName() + "." + cube.getName()
                    + " by OutlineYaml, to replay grid oracle fixtures against the outline they were");
            out.println("# captured from. Regenerate rather than edit.");
            out.println("defaultAliasTable: Default");
            out.println("dimensions:");
            for (EssMember dimension : cube.getOutline().getDimensions()) {
                out.println("  - name: " + quote(dimension.getName()));
                out.println("    members:");
                // A name repeated inside a dimension is a shared member, and Jaygrid's reader refuses
                // one that carries children - which is also true of the outline, so this only has to
                // not write them.
                Set<String> seen = new HashSet<>();
                for (EssMember child : dimension.getChildren()) {
                    writeMember(out, child, "      ", seen);
                }
            }
        }
    }

    private static void writeMember(PrintWriter out, EssMember member, String indent, Set<String> seen)
            throws IOException {
        out.println(indent + "- name: " + quote(member.getName()));
        String alias = alias(member);
        if (alias != null && !alias.isEmpty() && !alias.equals(member.getName())) {
            out.println(indent + "  alias: " + quote(alias));
        }

        if (!seen.add(member.getName())) {
            return;
        }
        if (member.getChildCount() > 0) {
            out.println(indent + "  children:");
            for (EssMember child : member.getChildren()) {
                writeMember(out, child, indent + "    ", seen);
            }
        }
    }

    /** The default alias, which the properties map carries as {@code aliases = {Default=...}}. */
    private static String alias(EssMember member) {
        Object aliases = member.getProperties().get("aliases");
        if (aliases instanceof Map<?, ?> map) {
            Object value = map.get("Default");
            return value == null ? null : value.toString();
        }
        return null;
    }

    /** Everything is quoted: outline names hold percent signs, dashes, colons and worse. */
    private static String quote(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }

    /** The dimension names, in outline order, for a caller that wants to sanity-check the result. */
    public static Set<String> dimensionNames(EssCube cube) {
        Set<String> names = new LinkedHashSet<>();
        for (EssMember dimension : cube.getOutline().getDimensions()) {
            names.add(dimension.getName());
        }
        return names;
    }

}

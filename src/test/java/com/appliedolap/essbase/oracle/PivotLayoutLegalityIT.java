package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.EssServer;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Asks Essbase whether the grids Jaygrid's pivot produces are grids it reads the same way.
 *
 * <p>Pivot is the one operation with no oracle: the REST pivot action only moves a dimension out of the
 * POV onto an axis, so there is no engine answer to compare a real pivot against - see
 * {@link PivotOracleIT}. This asks the narrower question that can still be answered. A client computing
 * a pivot locally submits the rearranged sheet and lets the server read it, and a sheet the server reads
 * differently from how the client meant it looks perfectly reasonable in the labels: the dimensions
 * come back in places nobody asked for, and the first sign of it is a grid of wrong numbers.
 *
 * <p>It does not say whether the layout Jaygrid chose is the <em>right</em> one - that is a question
 * about matching Smart View, not about the engine. It says the layout is legal and unambiguous.
 *
 * <p>The input is written by {@code GeneratePivotLayouts} in jaygrid and copied here. Regenerate it
 * there when Jaygrid's pivot changes.
 */
public class PivotLayoutLegalityIT {

    private static final File LAYOUTS = new File("src/test/resources/oracle/pivot-layouts.json");

    private EssCube cube;

    @Before
    public void setUp() {
        EssServer server = ConnectionUtils.server();
        try {
            cube = server.getApplication("Vision").getCube("Plan1");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Vision.Plan1 is not on this server", absent);
        }
    }

    @Test
    @Category(DestructiveIntegrationTest.class)
    public void everyLayoutJaygridsPivotProducesIsOneEssbaseReadsTheSameWay() throws Exception {
        JsonNode root = new ObjectMapper().readTree(LAYOUTS);
        List<String> problems = new ArrayList<>();
        int checked = 0;

        for (JsonNode layout : root.get("layouts")) {
            String name = layout.get("name").asText();
            if (layout.has("refused")) {
                // Jaygrid would not perform this pivot at all, so there is no layout to ask about.
                System.out.println("--- " + name + ": refused by Jaygrid (" + layout.get("refused").asText() + ")");
                continue;
            }
            checked++;

            String[][] sheet = sheet(layout.get("cells"));
            EssCubeView view = cube.openCubeView();
            view.setPreferences(GridOracle.DEFAULTS);
            view = cube.openCubeView();
            try {
                view.setLayout(sheet, layout.get("headerRows").asInt(), layout.get("leftColumns").asInt());
            } catch (RuntimeException refused) {
                problems.add(name + ": the server would not read the layout at all - "
                        + refused.getMessage());
                continue;
            }

            List<String> wanted = intended(layout.get("placements"));
            List<String> got = actual(view);
            System.out.println("--- " + name);
            System.out.println("    Jaygrid meant " + wanted);
            System.out.println("    Essbase  read " + got);
            if (!wanted.equals(got)) {
                problems.add(name + ":\n        Jaygrid meant " + wanted + "\n        Essbase  read " + got);
            }
        }

        assertTrue("no layouts were checked - regenerate pivot-layouts.json", checked > 0);
        assertEquals(problems.size() + " of " + checked + " layouts were read differently than meant:\n\n"
                + String.join("\n\n", problems) + "\n", List.of(), problems);
    }

    private static String[][] sheet(JsonNode cells) {
        String[][] sheet = new String[cells.size()][];
        for (int row = 0; row < cells.size(); row++) {
            JsonNode line = cells.get(row);
            sheet[row] = new String[line.size()];
            for (int column = 0; column < line.size(); column++) {
                sheet[row][column] = line.get(column).asText();
            }
        }
        return sheet;
    }

    /** What Jaygrid says it built, as dimension-to-place pairs sorted so the two sides compare. */
    private static List<String> intended(JsonNode placements) {
        List<String> wanted = new ArrayList<>();
        for (JsonNode placement : placements) {
            wanted.add(placement.get("dimension").asText() + "=" + placement.get("region").asText()
                    + placement.get("index").asInt());
        }
        wanted.sort(String::compareTo);
        return wanted;
    }

    /** The same, from the server. POV dimensions are left out: neither side orders them. */
    private static List<String> actual(EssCubeView view) {
        List<String> got = new ArrayList<>();
        for (EssCubeView.DimensionPlacement placement : view.getPlacements()) {
            if (placement.region() != EssCubeView.DimensionPlacement.Region.POV) {
                got.add(placement.name() + "=" + placement.region() + placement.index());
            }
        }
        got.sort(String::compareTo);
        return got;
    }

}

package com.appliedolap.essbase.oracle;

import com.appliedolap.essbase.ConnectionUtils;
import com.appliedolap.essbase.EssCube;
import com.appliedolap.essbase.EssCubeView;
import com.appliedolap.essbase.testing.DestructiveIntegrationTest;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Writing data back through the grid's submit action.
 *
 * <p>Destructive in the literal sense - it writes into Sample.Basic - so it targets one leaf
 * intersection, remembers what was there, and puts it back.
 *
 * <p>What comes back is not the string that went in: {@code 4321} submits and reads back as
 * {@code 4321.0}, because the cell holds a double and the grid renders it. Comparing the text would be
 * testing the formatting, so these compare the number.
 */
@Category(DestructiveIntegrationTest.class)
public class SubmitDataOracleIT {

    /** Sales / Jan / Cola / New York / Actual - a leaf in every dimension, so it is writable. */
    private static final int DATA_ROW = 2;

    private static final int DATA_COLUMN = 1;

    private EssCube cube;

    @Before
    public void setUp() {
        try {
            cube = ConnectionUtils.server().getApplication("Sample").getCube("Basic");
        } catch (RuntimeException absent) {
            Assume.assumeNoException("Sample.Basic is not on this server", absent);
        }
    }

    @Test
    public void aSubmittedValueReadsBack() {
        String before = read(laidOut());
        try {
            submit("4321");
            assertEquals(4321.0, Double.parseDouble(read(laidOut())), 0.0001);
        } finally {
            restore(before);
        }
    }

    /** The empty string is what clears a cell. */
    @Test
    public void submittingNothingClearsTheCell() {
        String before = read(laidOut());
        try {
            submit("1234");
            assertFalse(read(laidOut()).isEmpty());

            submit("");
            assertEquals("", read(laidOut()));
        } finally {
            restore(before);
        }
    }

    /**
     * And {@code #Missing} does not - it writes a zero.
     *
     * <p>Worth a test of its own because it is the classic spreadsheet idiom for clearing a cell and
     * silently means something else here: a caller passing on what a user typed writes a real zero
     * into the cube, which aggregates, where they asked for no value at all. Anything taking the word
     * from a human has to translate it before it gets this far.
     */
    @Test
    public void submittingMissingWritesAZeroInstead() {
        String before = read(laidOut());
        try {
            submit("#Missing");
            assertEquals(0.0, Double.parseDouble(read(laidOut())), 0.0001);
        } finally {
            restore(before);
        }
    }

    private void submit(String value) {
        laidOut().submitData(List.of(new EssCubeView.CellEdit(DATA_ROW, DATA_COLUMN, value)));
    }

    private void restore(String before) {
        submit(before.isEmpty() ? "" : before);
    }

    private EssCubeView laidOut() {
        cube.resetDefaultView();
        EssCubeView view = cube.openCubeView();
        view.setLayout(new String[][] {
                { "", "Cola", "New York", "Actual" },
                { "", "Jan", "", "" },
                { "Sales", "", "", "" },
        }, 2, 1);
        return view;
    }

    private static String read(EssCubeView view) {
        String value = view.getCell(DATA_ROW, DATA_COLUMN);
        return value == null ? "" : value.trim();
    }
}

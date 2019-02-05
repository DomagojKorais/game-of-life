package game.kata;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.function.IntBinaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GridTest {

    private static final int[][] testMatrix = new int[][] {
            {0,0,0,0,0,0,0,0,},
            {0,0,0,0,1,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,0,0,0,0,0,}
    };

    private static final int[][] testMatrixNeighbors = new int[][] {
            {0,0,0,1,1,1,0,0,},
            {0,0,1,3,2,2,0,0,},
            {0,0,1,2,2,2,0,0,},
            {0,0,1,2,2,1,0,0,}
    };

    private static final int[][] testMatrixEvolved = new int[][] {
            {0,0,0,0,0,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,0,0,0,0,0,}
    };

    private void matchBinaryFeatureAgainstGivenMatrix(IntBinaryOperator lambda, int[][] mat) {
        boolean is_matching = true;
        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                is_matching &= (lambda.applyAsInt(i,j) == mat[i][j]);
        assertTrue(is_matching);
    }

    @Test
    public void checkWholeContent() {
        Grid grid = new Grid(testMatrix);
        //grid.printGrid();

        IntBinaryOperator lambda = (int i, int j) -> grid.getCell(i,j).getStatus();
        matchBinaryFeatureAgainstGivenMatrix(lambda, testMatrix);
    }

    @Test
    public void checkAllNeighbors() {
        Grid grid = new Grid(testMatrix);

        IntBinaryOperator lambda = (int i, int j) -> grid.countAliveNeighbors(testMatrix, i, j);
        matchBinaryFeatureAgainstGivenMatrix(lambda, testMatrixNeighbors);
    }

    @Test
    public void checkEvolvedContent() {
        Grid grid = new Grid(testMatrix);
        Grid newGrid = grid.evolve();
        IntBinaryOperator lambda = (int i, int j) -> newGrid.getCell(i,j).getStatus();
        matchBinaryFeatureAgainstGivenMatrix(lambda, testMatrixEvolved);
    }

    @Test
    public void checkPrintGrid() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.

        Grid grid = new Grid(testMatrix);
        grid.printGrid();
        assertEquals("00000000\n" +
                              "00001000\n" +
                              "00011000\n" +
                              "00000000\n",outContent.toString());
    }

}

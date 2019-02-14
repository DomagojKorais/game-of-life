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

    private static final int[][] testMatrixEvolved = new int[][] {
            {0,0,0,0,0,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,0,0,0,0,0,}
    };

    private void matchLambdaResultsAgainstMatrix(IntBinaryOperator lambda, int[][] mat) {
        boolean is_matching = true;
        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                is_matching &= (lambda.applyAsInt(i,j) == mat[i][j]);
        assertTrue(is_matching);
    }

    @Test
    public void checkWholeContent() {
        Grid grid = new Grid(testMatrix);

        IntBinaryOperator lambda = (int i, int j) -> grid.getCell(i,j).getStatus();
        matchLambdaResultsAgainstMatrix(lambda, testMatrix);
        assertEquals(grid.getRows(), testMatrix.length);
        assertEquals(grid.getColumns(), testMatrix[0].length);
    }

    @Test
    public void checkEvolvedContent() {
        Grid grid = new Grid(testMatrix);
        Grid newGrid = grid.evolve();
        IntBinaryOperator lambda = (int i, int j) -> newGrid.getCell(i,j).getStatus();
        matchLambdaResultsAgainstMatrix(lambda, testMatrixEvolved);
    }
 }

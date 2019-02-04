package game.kata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.Assert.*;

public class GridReaderTest {

    private static GridReader reader = new GridReader();

    private String getFileFromResource(String resourceName) throws NullPointerException {
        return Objects.requireNonNull(GridReaderTest.class.getClassLoader().getResource(resourceName)).getFile();
    }

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    @Test
    public void readFileTest() throws NullPointerException, FileNotFoundException {
        String filename = getFileFromResource("sparse_lines.txt");
        assertArrayEquals(reader.readFile(filename),
                new String[] {"Line 1", "Line 2", "Line 3"});
    }

    @Test
    public void parseGrid_shortException() {
        String[] testLines = new String[] {"Generation 1:"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Input too short");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_generationException() {
        String[] testLines = new String[] {"DEADBEEF 1:", "1 1", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Missing generation number declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_generationNumberException() {
        String[] testLines = new String[] {"GeNeRaTioN DEADBEEF:", "1 1", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Wrong generation number format");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_sizeDeclarationException1() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Wrong matrix size declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_sizeDeclarationException3() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 1;", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Wrong matrix size declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_sizeDeclarationFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 X", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Wrong matrix size declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_matrixRowsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "2 1", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Number of matrix rows does not match the declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_matrixColumnsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "*"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Number of matrix columns does not consistently match the declaration");
        reader.parseGrid(testLines);
    }

    @Test
    public void parseGrid_matrixFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "HA"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Invalid matrix format");
        reader.parseGrid(testLines);
    }

    private void matchGridWithMatrixAndGen(Grid grid, int[][] mat, int gen) {
        Cell[][] cellsMatrix = grid.getCellMatrix();
//        assertEquals(testGrid.generation, gen); // TODO: implement generation functionality in Grid and here
        assertEquals(cellsMatrix.length, mat.length); // rows
        assertEquals(cellsMatrix[0].length, mat[0].length); // columns
        boolean match = true;
        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                match &= (grid.getCell(i,j).getStatus() == mat[i][j]);
        assertTrue(match);
    }

    @Test
    public void parseGrid_genericParsingCheck() {
        String[] testLines = new String[] {"GeNeRaTioN     0:", "1  2", ".*"};
        Grid testGrid = reader.parseGrid(testLines);
        int[][] testMat = new int[][] {{0,1}};
        matchGridWithMatrixAndGen(testGrid, testMat, 0);
    }

    @Test
    public void fullParsingCheck() throws FileNotFoundException {
        String filename = getFileFromResource("grid.txt");

        Grid testGrid = reader.parseGridFromFile(filename);

        int[][] testMat = new int[][] {{0,0,0,0,0,0,0,0},
                                       {0,0,0,0,1,0,0,0},
                                       {0,0,0,1,1,0,0,0},
                                       {0,0,0,0,0,0,0,0}};
        matchGridWithMatrixAndGen(testGrid, testMat, 1);
    }
/*
    @Test
    public void allDeathTest() throws FileNotFoundException {

        String filename = getFileFromResource("allZeroGrid.txt");
        Grid testGrid = reader.parseGridFromFile(filename);
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 0); // only for consistency, Java guarantees initialization to zero

        matchGridWithMatrixAndGen(testGrid, testMat, 1);
    }

    @Test
    public void allAliveTest() throws FileNotFoundException {

        String filename = getFileFromResource("allOneGrid.txt");
        Grid testGrid = reader.parseGridFromFile(filename);
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 1);

        matchGridWithMatrixAndGen(testGrid, testMat, 1);
    }*/

}
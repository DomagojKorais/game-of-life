package game.kata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class GridReaderTest {

    private static GridReader reader = new GridReader();

    private char[][] getFileFromResource(String resourceName) throws NullPointerException {
        String fileName = GridReaderTest.class.getClassLoader().getResource(resourceName).getFile();
        return reader.readGrid(fileName);
    }

    private char[][] buildTestGrid(){
        char [][] testGrid = new char[4][8];
        for(int i = 0; i<4; i++){
            for(int j = 0; j<8; j++)
                testGrid[i][j] = '.';
        }
        testGrid[1][4] = '*';
        testGrid[2][3] = '*';
        testGrid[2][4] = '*';

        return testGrid;
    }

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    @Test
    public void readTestGrid() {

        char[][] testGrid = buildTestGrid();
        char[][] fileContent = getFileFromResource("grid.txt");

        assertArrayEquals(testGrid, fileContent);
    }

    @Test
    public void readFileTest() throws NullPointerException, FileNotFoundException {
        String fileName = GridReaderTest.class.getClassLoader().getResource("sparse_lines.txt").getFile();
        assertArrayEquals(reader.readFile(fileName),
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

    @Test
    public void parseGrid_genericParsingCheck() {
        String[] testLines = new String[] {"GeNeRaTioN     0:", "1  2", ".*"};
        Grid testGrid = reader.parseGrid(testLines);
        assertEquals(testGrid.generation, 0);
        assertEquals(testGrid.rows, 1);
        assertEquals(testGrid.columns, 2);
        assertEquals(testGrid.cellMatrix.length, 1);
        assertArrayEquals(testGrid.cellMatrix[0], new int[]{0,1});
    }

    @Test
    public void fullParsingCheck() throws FileNotFoundException {
        String fileName = GridReaderTest.class.getClassLoader().getResource("grid.txt").getFile();

        Grid testGrid = reader.parseGridFromFile(fileName);

        assertEquals(testGrid.generation, 1);
        assertEquals(testGrid.rows, 4);
        assertEquals(testGrid.columns, 8);
        assertEquals(testGrid.cellMatrix.length, 4);
        assertArrayEquals(testGrid.cellMatrix[0], new int[]{0,0,0,0,0,0,0,0});
        assertArrayEquals(testGrid.cellMatrix[1], new int[]{0,0,0,0,1,0,0,0});
        assertArrayEquals(testGrid.cellMatrix[2], new int[]{0,0,0,1,1,0,0,0});
        assertArrayEquals(testGrid.cellMatrix[3], new int[]{0,0,0,0,0,0,0,0});
    }
}
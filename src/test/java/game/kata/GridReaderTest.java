package game.kata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;

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
        String fileName = Objects.requireNonNull(GridReaderTest.class.getClassLoader().getResource("sparse_lines.txt")).getFile();
        assertArrayEquals(reader.readFile(fileName),
                new String[] {"Line 1", "Line 2", "Line 3"});
    }

    @Test
    public void parseGrid_shortException() {
        String[] testLines = new String[] {"Generation 1:"};
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage("Input file too short");
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
        exceptionGrabber.expectMessage("Number of matrix columns does not match the declaration");
        reader.parseGrid(testLines);
    }
}
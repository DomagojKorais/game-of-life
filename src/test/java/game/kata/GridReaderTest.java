package game.kata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.*;

public class GridReaderTest {

    private static final GridReader reader = new GridReader();

    private static final String msgErrShort         = "Input too short";
    private static final String msgErrGeneration    = "Wrong generation number specification format. Format: 'Generation <num>: [possibly comments]'";
    private static final String msgErrDimensions    = "Wrong matrix dimension specification format. Format: '<num_rows> <num_columns>' Numbers must be >= 0";
    private static final String msgErrRowMismatch   = "Number of matrix rows does not match the declaration";
    private static final String msgErrColMismatch   = "Number of matrix columns does not consistently match the declaration";
    private static final String msgErrMatrixFmt     = "Invalid matrix format";

    private String getFileFromResource(String resourceName) throws NullPointerException {
        return Objects.requireNonNull(GridReaderTest.class.getClassLoader().getResource(resourceName)).getFile();
    }

    @Rule
    public final ExpectedException exceptionGrabber = ExpectedException.none();

    private void compareMatchWithMatrixAndGen(Match match, int[][] mat, int gen) {
        assertEquals(match.getGenerationNumber(), gen);
        Grid grid = match.getGrid();
        assertEquals(grid.getRows(), mat.length);
        assertEquals(grid.getColumns(), mat[0].length);
        boolean is_matching = true;
        for (int i = 0; i < grid.getRows(); ++i)
            for (int j = 0; j < grid.getColumns(); ++j)
                is_matching &= (grid.getCell(i,j).getStatus() == mat[i][j]);
        assertTrue(is_matching);
    }

    private void invokeParseGridExpectException(String[] lines, String errMsg) {
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage(errMsg);
        reader.parseGrid(lines);
    }

    private void invokeParseGridAndMatch(String[] lines, int[][] mat, int gen) {
        compareMatchWithMatrixAndGen(reader.parseGrid(lines), mat, gen);
    }

    private void invokeParseGridFromFileAndMatch(String resource, int[][] mat, int gen) throws FileNotFoundException {
        String filename = getFileFromResource(resource);
        compareMatchWithMatrixAndGen(reader.parseGridFromFile(filename), mat, gen);
    }

    @Test
    public void readFileTest() throws NullPointerException, FileNotFoundException {
        String filename = getFileFromResource("sparse_lines.txt");
        assertArrayEquals(reader.readFile(filename),
                new String[] {"Line 1", "Line 2", "Line 3"});
    }

    @Test
    public void parseGrid_shortException() {
        String[] testLines = new String[] {"Generation 1:"};
        invokeParseGridExpectException(testLines, msgErrShort);
    }

    @Test
    public void parseGrid_generationException() {
        String[] testLines = new String[] {"DEADBEEF 1:", "1 1", "*"};
        invokeParseGridExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parseGrid_generationNumberException() {
        String[] testLines = new String[] {"GeNeRaTioN DEADBEEF:", "1 1", "*"};
        invokeParseGridExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parseGrid_generationSneakyException() {
        String[] testLines = new String[] {"GeNeRaTioN 1':", "1 1", "*"};
        invokeParseGridExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parseGrid_generationOptionalColon() {
        String[] testLines = new String[] {"GeNeRaTioN 1", "1 1", "*"};
        invokeParseGridAndMatch(testLines, new int[][] {{1}}, 1);
    }

    @Test
    public void parseGrid_sizeDeclarationException1() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1", "*"};
        invokeParseGridExpectException(testLines, msgErrDimensions);
   }

    @Test
    public void parseGrid_sizeDeclarationException3() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 1;", "*"};
        invokeParseGridExpectException(testLines, msgErrDimensions);
   }

    @Test
    public void parseGrid_sizeDeclarationFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 X", "*"};
        invokeParseGridExpectException(testLines, msgErrDimensions);
    }

    @Test
    public void parseGrid_matrixRowsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "2 1", "*"};
        invokeParseGridExpectException(testLines, msgErrRowMismatch);
    }

    @Test
    public void parseGrid_matrixColumnsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "*"};
        invokeParseGridExpectException(testLines, msgErrColMismatch);
    }

    @Test
    public void parseGrid_matrixFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "HA"};
        invokeParseGridExpectException(testLines, msgErrMatrixFmt);
    }

    @Test
    public void parseGrid_genericParsingCheck() {
        String[] testLines = new String[] {"GeNeRaTioN     0:", "1  2", ".*"};
        int[][] testMat = new int[][] {{0,1}};
        invokeParseGridAndMatch(testLines, testMat, 0);
    }

    @Test
    public void fullParsingCheck() throws FileNotFoundException {
        int[][] testMat = new int[][] {{0,0,0,0,0,0,0,0},
                                       {0,0,0,0,1,0,0,0},
                                       {0,0,0,1,1,0,0,0},
                                       {0,0,0,0,0,0,0,0}};
        invokeParseGridFromFileAndMatch("grid.txt", testMat, 1);
    }

    @Test
    public void allDeadTest() throws FileNotFoundException {
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 0); // only for consistency, Java guarantees initialization to zero

        invokeParseGridFromFileAndMatch("allZeroGrid.txt", testMat, 1);
    }

    @Test
    public void allAliveTest() throws FileNotFoundException {
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 1);

        invokeParseGridFromFileAndMatch("allOneGrid.txt", testMat, 3);
    }

}
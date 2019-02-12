package game.kata;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GridReaderTest {

    private static final GridReader reader = new GridReader();

    private static final String msgErrShort         = "Input too short";
    private static final String msgErrGeneration    = "Wrong generation number specification format. It must be the 1st non-empty line. Format: 'Generation <num>: [optional comments]'";
    private static final String msgErrDimensions    = "Wrong matrix dimension specification format. It must be the 2nd non-empty line. Format: '<num_rows> <num_columns>' Numbers must be > 0";
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

    private void invokeParseExpectException(String[] lines, String errMsg) {
        exceptionGrabber.expect(IllegalArgumentException.class);
        exceptionGrabber.expectMessage(errMsg);
        reader.parse(Arrays.stream(lines));
    }

    private void invokeParseAndMatch(String[] lines, int[][] mat, int gen) {
        compareMatchWithMatrixAndGen(reader.parse(Arrays.stream(lines)), mat, gen);
    }

    private void invokeParseFromFileAndMatch(String resource, int[][] mat, int gen) throws IOException {
        String filename = getFileFromResource(resource);
        compareMatchWithMatrixAndGen(reader.parseFromFile(filename), mat, gen);
    }

    @Test
    public void readFileTest() throws NullPointerException, IOException {
        String filename = getFileFromResource("sparse_lines.txt");
        assertArrayEquals(reader.readFile(filename).toArray(String[]::new),
                new String[] {"", "", "Line 1", "", "  Line 2   ", "    ", "", "", "", "Line 3"});
    }

    @Test
    public void parse_shortException() {
        String[] testLines = new String[] {"Generation 1:"};
        invokeParseExpectException(testLines, msgErrShort);
    }

    @Test
    public void parse_generationException() {
        String[] testLines = new String[] {"DEADBEEF 1:", "1 1", "*"};
        invokeParseExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parse_generationNumberException() {
        String[] testLines = new String[] {"GeNeRaTioN DEADBEEF:", "1 1", "*"};
        invokeParseExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parse_generationSneakyException() {
        String[] testLines = new String[] {"GeNeRaTioN 1':", "1 1", "*"};
        invokeParseExpectException(testLines, msgErrGeneration);
    }

    @Test
    public void parse_generationOptionalColon() {
        String[] testLines = new String[] {"GeNeRaTioN 1", "1 1", "*"};
        invokeParseAndMatch(testLines, new int[][] {{1}}, 1);
    }

    @Test
    public void parse_sizeDeclarationException1() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1", "*"};
        invokeParseExpectException(testLines, msgErrDimensions);
   }

    @Test
    public void parse_sizeDeclarationException3() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 1;", "*"};
        invokeParseExpectException(testLines, msgErrDimensions);
   }

    @Test
    public void parse_sizeDeclarationFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 X", "*"};
        invokeParseExpectException(testLines, msgErrDimensions);
    }

    @Test
    public void parse_sizeDeclarationZeroRowsException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "0 1"};
        invokeParseExpectException(testLines, msgErrDimensions);
    }

    @Test
    public void parse_sizeDeclarationZeroColumnsException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 0", ""};
        invokeParseExpectException(testLines, msgErrDimensions);
    }

    @Test
    public void parse_matrixRowsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "2 1", "*"};
        invokeParseExpectException(testLines, msgErrRowMismatch);
    }

    @Test
    public void parse_matrixColumnsMismatchException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "*"};
        invokeParseExpectException(testLines, msgErrColMismatch);
    }

    @Test
    public void parse_matrixFormatException() {
        String[] testLines = new String[] {"GeNeRaTioN     1:", "1 2", "HA"};
        invokeParseExpectException(testLines, msgErrMatrixFmt);
    }

    @Test
    public void parse_genericParsingCheck() {
        String[] testLines = new String[] {
                "Generation 0:",
                "1 2",
                ".*"
        };
        int[][] testMat = new int[][] {{0,1}};
        invokeParseAndMatch(testLines, testMat, 0);
    }

    @Test
    public void parse_funkyParsingCheck() {
        String[] testLines = new String[] {
                "",
                "   GeNeRaTioN   1337  :    catch-it-all no-exception test",
                "   2  2  ",
                " .*  ",
                "    *.",
                ""
        };
        int[][] testMat = new int[][] {{0,1},{1,0}};
        invokeParseAndMatch(testLines, testMat, 1337);
    }

    @Test
    public void fullParsingCheck() throws IOException {
        int[][] testMat = new int[][] {{0,0,0,0,0,0,0,0},
                                       {0,0,0,0,1,0,0,0},
                                       {0,0,0,1,1,0,0,0},
                                       {0,0,0,0,0,0,0,0}};
        invokeParseFromFileAndMatch("grid.txt", testMat, 1);
    }

    @Test
    public void allDeadTest() throws IOException {
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 0); // only for consistency, Java guarantees initialization to zero

        invokeParseFromFileAndMatch("allZeroGrid.txt", testMat, 1);
    }

    @Test
    public void allAliveTest() throws IOException {
        int[][] testMat = new int[4][8];
        for (int[] row : testMat)
            Arrays.fill(row, 1);

        invokeParseFromFileAndMatch("allOneGrid.txt", testMat, 3);
    }

}
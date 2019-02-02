package game.kata;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class CharsInterpretersTest {

    private static GridReader reader = new GridReader();
    private static CharsInterpreters converter = new CharsInterpreters();

    private int[][] buildTestGrid() {
        int[][] testGrid = new int[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++)
                testGrid[i][j] = 0;
        }

        return testGrid;
    }
    private char[][] getFileFromResource(String resourceName) throws NullPointerException {
        String fileName = GridReaderTest.class.getClassLoader().getResource(resourceName).getFile();
        return reader.readGrid(fileName);
    }

    @Test
    public void allDeathConversion() {

        int[][] testGrid = buildTestGrid();
        char[][] allZero = getFileFromResource("allZeroGrid");


        assertArrayEquals(testGrid,converter.convert(allZero));


    }
}

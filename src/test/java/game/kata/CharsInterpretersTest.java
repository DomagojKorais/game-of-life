package game.kata;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class CharsInterpretersTest {

    private static GridReader reader = new GridReader();
    private static CharsInterpreters converter = new CharsInterpreters();

    private int[][] buildTestGridDeath() {
        int[][] testGrid = new int[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++)
                testGrid[i][j] = 0;
        }

        return testGrid;
    }

    private int[][] buildTestGridAlive() {
        int[][] testGrid = new int[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++)
                testGrid[i][j] = 1;
        }

        return testGrid;
    }


    private char[][] getFileFromResource(String resourceName) throws NullPointerException {
        String fileName = GridReaderTest.class.getClassLoader().getResource(resourceName).getFile();
        return reader.readGrid(fileName);
    }

    @Test
    public void allDeathConversion() {

        int[][] testGrid = buildTestGridDeath();
        char[][] allZero = getFileFromResource("allZeroGrid");


        assertArrayEquals(testGrid,converter.convert(allZero));


    }

    @Test
    public void allAliveConversion() {

        int[][] testGrid = buildTestGridAlive();
        char[][] allOne = getFileFromResource("allOneGrid");


        assertArrayEquals(testGrid,converter.convert(allOne));


    }


    @Test
    public void dimensions() {

        int[] dimensions = {4,8};
        char[][] allOne = getFileFromResource("allOneGrid");
        int nCols = converter.getNumberOfColumns(allOne);
        int nRows = converter.getNumberOfRows(allOne);
        int[] convertedDimensions = {nRows,nCols};

        assertArrayEquals(dimensions,convertedDimensions);


    }
}

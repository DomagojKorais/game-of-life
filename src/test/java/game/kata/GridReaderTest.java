package game.kata;

import org.junit.Test;
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

    @Test
    public void readTestGrid() {

        char[][] testGrid = buildTestGrid();
        char[][] fileContent = getFileFromResource("grid");

        assertArrayEquals(testGrid, fileContent);
    }

}
package game.kata;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GridTest {

    private int[][] buildTestMatrix(){
        int [][] testMatrix = new int[4][8];
        for(int i = 0; i<4; i++){
            for(int j = 0; j<8; j++)
                testMatrix[i][j] = 0;
        }
        testMatrix[1][4] = 1;
        testMatrix[2][3] = 1;
        testMatrix[2][4] = 1;

        return testMatrix;
    }

    @Test
    public void checkCellStatus() throws NullPointerException {

        int[][] testMatrix = buildTestMatrix();
        Grid grid = new Grid(1, testMatrix.length, testMatrix[0].length, testMatrix);
        //grid.printGrid();

        Cell chosenCell = grid.getCell(1,4);
        assertEquals(1, chosenCell.getStatus());
    }

    @Test
    public void check0CellNeighbours() throws NullPointerException {

        int[][] testMatrix = buildTestMatrix();

        Grid grid = new Grid(1, testMatrix.length, testMatrix[0].length, testMatrix);
        //Cell chosenCell = grid.getCell(1,4);

        assertEquals(0, grid.countAliveNeighbors(testMatrix,3,7));
    }

    @Test
    public void check1CellNeighbours() throws NullPointerException {

        int[][] testMatrix = buildTestMatrix();

        Grid grid = new Grid(1, testMatrix.length, testMatrix[0].length, testMatrix);
        //Cell chosenCell = grid.getCell(1,4);

        assertEquals(1, grid.countAliveNeighbors(testMatrix,0,4));
    }

    @Test
    public void check2CellNeighbours() throws NullPointerException {

        int[][] testMatrix = buildTestMatrix();

        Grid grid = new Grid(1, testMatrix.length, testMatrix[0].length, testMatrix);
        //Cell chosenCell = grid.getCell(1,4);

        assertEquals(2, grid.countAliveNeighbors(testMatrix,1,4));
    }


}

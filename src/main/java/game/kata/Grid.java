package game.kata;

import java.util.Arrays;

class Grid {
    private static int rows;
    private static int columns;
    private Cell[][] cellMatrix;

    public Grid(int[][] _intMatrix) {

        rows = _intMatrix.length;
        columns = _intMatrix[0].length;

        cellMatrix = new Cell[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                int aliveNeighbours = countAliveNeighbors(_intMatrix,i,j);
                cellMatrix[i][j] = new Cell(_intMatrix[i][j], aliveNeighbours);
            }
        }
    }

    public int countAliveNeighbors(int[][] _intMatrix, int row, int col){
        int aliveNeighbours = 0;

        // ogni cella viva contribuisce

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++)
                if (row+i>=0 && col+j>=0 && row+i < rows && col+j<columns)
                    aliveNeighbours += _intMatrix[row + i][col + j];
        }
        // The cell needs to be subtracted from
        // its neighbours as it was counted before
        aliveNeighbours -= _intMatrix[row][col];

        return aliveNeighbours;

    }

    public Cell getCell(int i, int j){
        return cellMatrix[i][j];
    }

    public int getRows(){
        return rows;
    }

    public int getColumns(){
        return columns;
    }

    public Cell[][] getCellMatrix(){
        return cellMatrix;
    }

    public void printGrid(){
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j)
                System.out.print(cellMatrix[i][j].getStatus());
            System.out.print("\n");
        }
    }
}

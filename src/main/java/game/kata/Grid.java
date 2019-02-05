package game.kata;
import static game.kata.Game.judge;

class Grid {
    private final int rows;
    private final int columns;
    private final Cell[][] cellMatrix;

    public int getRows()    { return rows; }
    public int getColumns() { return columns; }

    public Grid(int[][] _intMatrix) {

        rows = _intMatrix.length;
        columns = _intMatrix[0].length;

        cellMatrix = new Cell[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                cellMatrix[i][j] = new Cell(_intMatrix[i][j], countAliveNeighbors(_intMatrix,i,j));
            }
        }
    }

    public int countAliveNeighbors(int[][] _intMatrix, int row, int col){
        int aliveNeighbours = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++)
                if (row+i>=0 && col+j>=0 && row+i < rows && col+j<columns)
                    aliveNeighbours += _intMatrix[row + i][col + j];
        }
        aliveNeighbours -= _intMatrix[row][col];

        return aliveNeighbours;

    }

    public Cell getCell(int i, int j){
        return cellMatrix[i][j];
    }

    public void printGrid(){
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j)
                System.out.print(cellMatrix[i][j].getStatus());
            System.out.print("\n");
        }
    }


    public Grid evolve(){
        int[][] newIntMatrix = new int[rows][columns];

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                newIntMatrix[i][j] = cellMatrix[i][j].evolve();
            }
        }

        return new Grid(newIntMatrix);

    }
}

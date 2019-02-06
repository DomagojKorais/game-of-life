package game.kata;
import java.util.Arrays;

class Grid {
    private final int rows;
    private final int columns;
    private final Cell[][] cellMatrix;
    private final int[][] intMatrix;

    public int getRows()    { return rows; }
    public int getColumns() { return columns; }
    private int[][] getIntMatrix(){return intMatrix;}

    public Grid(int[][] _intMatrix) {

        rows = _intMatrix.length;
        columns = _intMatrix[0].length;
        intMatrix = _intMatrix;

        cellMatrix = new Cell[rows][columns];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                cellMatrix[i][j] = new Cell(_intMatrix[i][j], countAliveNeighbors(i,j));
            }
        }

        /*
        cellMatrix = Arrays.stream(_intMatrix)
                .map(() -> new Cell())
                .map(cells -> Arrays.stream(cells).mapToInt(Cell::evolve).toArray())
                .toArray(Cell[rows][columns]::new);
        */
    }

    public int countAliveNeighbors(int row, int col){
        int aliveNeighbours = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++)
                if (row+i>=0 && col+j>=0 && row+i < rows && col+j<columns)
                    aliveNeighbours += intMatrix[row + i][col + j];
        }
        aliveNeighbours -= intMatrix[row][col];

        return aliveNeighbours;

    }

    public Cell getCell(int i, int j){
        return cellMatrix[i][j];
    }


    public void printGrid(){
        Arrays.stream(getIntMatrix()).forEach(row -> {
                            Arrays.stream(row).forEach(System.out::print);
                            System.out.print("\n");
        });

    }


    public Grid evolve(){

        int[][] newIntMatrix = Arrays.stream(cellMatrix)
                .map(cells -> Arrays.stream(cells).mapToInt(Cell::evolve).toArray())
                .toArray(int[][]::new);

        return new Grid(newIntMatrix);

    }
}

package game.kata;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

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
        cellMatrix = IntStream.range(0, rows)
                .mapToObj(i -> IntStream.range(0, columns)
                        .mapToObj(j -> new Cell(intMatrix[i][j],countAliveNeighbours(i,j)))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

    }

    public int countAliveNeighbours(int row, int col){
        int currentCellState = intMatrix[row][col];
        IntPredicate rowIdxAcceptable = i -> (row+i>=0 && row+i<rows);
        IntPredicate colIdxAcceptable = j -> (col+j>=0 && col+j<columns);

        int aliveNeighbours = IntStream.range(-1, 2)
                .filter(rowIdxAcceptable)
                .map(i -> IntStream.range(-1, 2)
                        .filter(colIdxAcceptable)
                        .map(j -> intMatrix[row+i][col+j]).sum()).sum();

        return aliveNeighbours - currentCellState;

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

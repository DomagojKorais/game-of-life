package game.kata;
import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

class Grid {
    private final int rows;
    private final int columns;
    private final Cell[][] cellMatrix;

    public int getRows()    { return rows; }
    public int getColumns() { return columns; }

    public Grid(Cell[][] cellMat) {
        rows = cellMat.length;
        columns = cellMat[0].length;
        cellMatrix = cellMat;
    }

    public Grid(int[][] intMatrix) {
        this(Arrays.stream(intMatrix)
                        .map(row -> Arrays.stream(row)
                                .mapToObj(Cell::new)
                                .toArray(Cell[]::new))
                        .toArray(Cell[][]::new)
        );
    }

    public Cell getCell(int i, int j){
        return cellMatrix[i][j];
    }

    public int countAliveNeighbours(int row, int col) {

        int currentCellState = this.getCell(row,col).getStatus();
        IntPredicate rowIdxAcceptable = i -> (row+i>=0 && row+i<rows);
        IntPredicate colIdxAcceptable = j -> (col+j>=0 && col+j<columns);

        int aliveNeighbours = IntStream.range(-1, 2)
                .filter(rowIdxAcceptable)
                .map(i -> IntStream.range(-1, 2)
                       .filter(colIdxAcceptable)
                       .map(j -> this.getCell(row+i,col+j).getStatus()).sum()).sum();

       return aliveNeighbours - currentCellState;
    }


    public Grid evolve() {
        Cell[][] newCellMatrix = IntStream.range(0, rows).mapToObj((int i) ->
                IntStream.range(0, columns)
                        .mapToObj((int j) -> cellMatrix[i][j].evolve(countAliveNeighbours(i,j)))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);
        return new Grid(newCellMatrix);
    }

}

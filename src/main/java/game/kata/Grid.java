package game.kata;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static game.kata.Game.judge;

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

    public Grid(int[][] intMatrix) { // JUST FOR TESTS
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

    public void printGrid(){
        Arrays.stream(cellMatrix).forEach(row -> {
                            Arrays.stream(row).forEach((Cell c) -> System.out.print(c.getStatus()));
                            System.out.print("\n");
        });

    }


    public int countAliveNeighbours(int row, int col) {       // THIS HAS BEEN TRANSFORMED INTO THE getNeighbourCountMatrix() METHOD BELOW
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


    int[][] getNeighbourCountMatrix(){
        return IntStream.range(0, rows).mapToObj((int i) ->
                IntStream.range(0, columns)
                .map((int j) -> countAliveNeighbours(i,j))
                .toArray())
                .toArray(int[][]::new);
    }


    public Grid evolve() {
        Cell[][] newCellMatrix = IntStream.range(0, rows).mapToObj((int i) ->
                IntStream.range(0, columns)
                        .mapToObj((int j) -> cellMatrix[i][j].evolve(countAliveNeighbours(i,j)))// Cell( ? 0 : 1))//judge(cellMatrix[i][j], neighbourCountMatrix[i][j]) ? 0 : 1))
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);
        return new Grid(newCellMatrix);
    }

}

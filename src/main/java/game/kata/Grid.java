package game.kata;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static game.kata.Game.judge;

class Grid {
    private final int rows;
    private final int columns;
    private final Cell[][] cellMatrix;
//    private final int[][] intMatrix;

    public int getRows()    { return rows; }
    public int getColumns() { return columns; }
//    private int[][] getIntMatrix(){return intMatrix;}

    public Grid(int nrows, int ncolumns, Cell[][] cellMat) {
        rows = nrows;
        columns = ncolumns;
        cellMatrix = cellMat;
    }
    public Grid(int[][] intMatrix) { // DEPRECATED, DANGEROUS EDGE CASES
        this(intMatrix.length, intMatrix[0].length,
                Arrays.stream(intMatrix)
                        .map(row -> Arrays.stream(row)
                                .mapToObj(Cell::new)
                                .toArray(Cell[]::new))
                        .toArray(Cell[][]::new)
                );
    }
//    public Grid(int[][] _intMatrix) { // DEPRECATED, ALTERNATIVE IMPLEMENTATION ABOVE
//        rows = _intMatrix.length;
//        columns = _intMatrix[0].length;
////        intMatrix = _intMatrix;
//        cellMatrix = IntStream.range(0, rows)
//                .mapToObj(i -> IntStream.range(0, columns)
////                        .mapToObj(j -> new Cell(intMatrix[i][j],countAliveNeighbours(i,j)))
//                        .mapToObj(j -> new Cell(_intMatrix[i][j]))
//                        .toArray(Cell[]::new))
//                .toArray(Cell[][]::new);
//    }

//    public int countAliveNeighbours(int row, int col) {       // THIS HAS BEEN TRANSFORMED INTO THE getNeighbourCountMatrix() METHOD BELOW
//        int currentCellState = intMatrix[row][col];
//        IntPredicate rowIdxAcceptable = i -> (row+i>=0 && row+i<rows);
//        IntPredicate colIdxAcceptable = j -> (col+j>=0 && col+j<columns);
//
//        int aliveNeighbours = IntStream.range(-1, 2)
//                .filter(rowIdxAcceptable)
//                .map(i -> IntStream.range(-1, 2)
//                        .filter(colIdxAcceptable)
//                        .map(j -> intMatrix[row+i][col+j]).sum()).sum();
//
//        return aliveNeighbours - currentCellState;
//    }

    public Cell getCell(int i, int j){
        return cellMatrix[i][j];
    }

    public void printGrid(){
        Arrays.stream(cellMatrix).forEach(row -> {
                            Arrays.stream(row).forEach((Cell c) -> System.out.print(c.getStatus()));
                            System.out.print("\n");
        });

    }

    int[][] toExtendedIntMatrix() {
        int[] zeros = new int[columns+2];
        Function<Cell[],int[]> processRow = (Cell[] cell_row) ->
                IntStream.concat( IntStream.concat(
                        IntStream.of(0),
                        Arrays.stream(cell_row).mapToInt(Cell::getStatus) ),
                        IntStream.of(0)
                ).toArray();
        return Stream.concat( Stream.concat(
                Stream.of(zeros),
                Arrays.stream(cellMatrix).map(processRow) ),
                Stream.of(zeros)
        ).toArray(int[][]::new);
    }

    int[][] getNeighbourCountMatrix(){
        int[][] extendedIntMatrix = toExtendedIntMatrix();
        IntBinaryOperator cellNeighbors = (int row, int col) -> {
            int aliveNeighbours = IntStream.range(0, 3)
                    .map(i -> IntStream.range(0, 3)
                            .map(j -> extendedIntMatrix[row+i][col+j]).sum()).sum();
            return aliveNeighbours - extendedIntMatrix[row+1][col+1];
        };
        return IntStream.range(0, rows).mapToObj( (int i) ->
                IntStream.range(0, columns)
                .map((int j) -> cellNeighbors.applyAsInt(i,j))
                .toArray()
        ).toArray(int[][]::new);
    }

    public Grid evolve() {
        final int[][] neighbourCountMatrix = getNeighbourCountMatrix();
        Cell[][] newCellMatrix = IntStream.range(0, rows).mapToObj((int i) ->
                IntStream.range(0, columns).mapToObj(
                        (int j) -> new Cell(judge(cellMatrix[i][j], neighbourCountMatrix[i][j]) ? 0 : 1)
                ).toArray(Cell[]::new)
        ).toArray(Cell[][]::new);
//        int[][] newMatrix = IntStream.range(0, rows).mapToObj((int i) ->      // old int variant
//                IntStream.range(0, columns).map(
//                        (int j) -> (judge(cellMatrix[i][j], neighbourCountMatrix[i][j]) ? 0 : 1)
//                ).toArray()
//        ).toArray(int[][]::new);
        return new Grid(rows, columns, newCellMatrix);
    }

//    public Grid evolve(){     // REPLACED BY THE ABOVE IMPLEMENTATION, WHICH USES ITS OWN NEIGHBOUR CALCULATION
//        int[][] newIntMatrix = Arrays.stream(cellMatrix)
//                .map(cells -> Arrays.stream(cells).mapToInt(Cell::evolve).toArray())
//                .toArray(int[][]::new);
//
//        return new Grid(newIntMatrix);
//    }
}

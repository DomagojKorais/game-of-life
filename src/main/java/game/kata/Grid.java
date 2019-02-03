package game.kata;

class Grid {
    int generation;
    int rows;
    int columns;
    int[][] cellMatrix;

    Grid(int _generation, int _rows, int _columns, int[][] _cellMatrix) {
        generation = _generation;
        rows = _rows;
        columns = _columns;
        cellMatrix = _cellMatrix;
    }
}

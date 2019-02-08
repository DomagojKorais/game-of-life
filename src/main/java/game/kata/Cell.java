package game.kata;

import static game.kata.Game.judge;

public class Cell {
    private final int status;

    public Cell (int inception) {
        this.status = inception;
    }

    Cell evolve(int aliveNeighbours) {
        return new Cell(judge(this, aliveNeighbours) ? 0 : 1);
    }

    public int getStatus() { return status; }
}

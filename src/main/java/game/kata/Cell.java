package game.kata;

import static game.kata.Game.judge;

public class Cell {
    private final int status;

    public Cell (int inception) {
        this.status = inception;
    }

    Cell evolve(int aliveNeighbours) {
        return judge(this, aliveNeighbours);
    }

    public int getStatus() { return status; }
}

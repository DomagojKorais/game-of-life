package game.kata;

import static game.kata.Game.judge;

public class Cell {
    private int status;
    private int aliveNeighbours;

    public Cell (int inception, int neighbours) {
        if (neighbours>8 | neighbours<0 | inception<0 | inception>1)
            throw new IllegalArgumentException("Invalid number of neighbours or invalid initialization value");

        this.status = inception;
        this.aliveNeighbours = neighbours;
    }

    public int evolve() {
        return (judge(this) ? 0 : 1);
   }

    public int getAliveNeighbours() { return aliveNeighbours; }

    public int getStatus() { return status; }
}

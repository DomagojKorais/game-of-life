package game.kata;

public class Cell {
    private int status;
    private int aliveNeighbours;

    public Cell (int inception, int neighbours) {
        if (neighbours>8 | neighbours<0 | inception<0 | inception>1)
            throw new IllegalArgumentException("Invalid number of neighbours or invalid initialization value");

        this.aliveNeighbours = neighbours;
        this.status = inception;
    }

    public int evolve(boolean die) {
        return (die ? 0 : 1);
   }

    public int getAliveNeighbours() { return aliveNeighbours; }

    public int getStatus() { return status; }
}

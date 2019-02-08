package game.kata;

//import static game.kata.Game.judge;

public class Cell {
    private final int status;
//    private int aliveNeighbours;  // NO LONGER NEEDED INSIDE Cell

    public Cell (int inception) {
        this.status = inception;
    }
//    public Cell (int inception, int neighbours) { // NO LONGER RELEVANT
//        if (neighbours>8 | neighbours<0 | inception<0 | inception>1)
//            throw new IllegalArgumentException("Invalid number of neighbours or invalid initialization value");
//
//        this.status = inception;
//        this.aliveNeighbours = neighbours;
//    }

//    public int evolve() {     // REPLACED BY IMPLEMENTATION IN Grid
//        return (judge(this) ? 0 : 1);
//   }

//    public int getAliveNeighbours() { return aliveNeighbours; } // NO LONGER RELEVANT

    public int getStatus() { return status; }
}

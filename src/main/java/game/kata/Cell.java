package game.kata;

public class Cell {
    int  status;
    int aliveNeighbours;

    public Cell (int inception, int neighbours){


        if (neighbours<=8 & neighbours>=0 & inception>=0 & inception <=1) {
            this.aliveNeighbours = neighbours;
            this.status = inception;
        }else{
            throw new IllegalArgumentException("Invalid number of neighbours or invalid initialization value");
        }
    }

}

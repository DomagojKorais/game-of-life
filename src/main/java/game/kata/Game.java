package game.kata;

public class Game {

    public static Cell judge (Cell cell, int aliveNeighbours) {
        boolean deathSentence;

        if (cell.getStatus() == 1)
            deathSentence = (aliveNeighbours < 2 | aliveNeighbours > 3);
        else
            deathSentence = (aliveNeighbours != 3);

        return new Cell(deathSentence ? 0 : 1);
    }
}

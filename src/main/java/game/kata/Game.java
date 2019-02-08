package game.kata;

public class Game {

    public static boolean judge (Cell cell, int aliveNeighbours) {
        boolean deathSentence;

        if (cell.getStatus() == 1)
            deathSentence = (aliveNeighbours < 2 | aliveNeighbours > 3);
        else
            deathSentence = (aliveNeighbours != 3);

        return deathSentence;
    }
}

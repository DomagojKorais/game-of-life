package game.kata;

public class Game {

    public static boolean judge (Cell cell){
        boolean deathSentence;
        final int aliveNeighbors = cell.getAliveNeighbours();

        if (cell.getStatus() == 1)
            deathSentence = (aliveNeighbors < 2 | aliveNeighbors > 3);
        else
            deathSentence = (aliveNeighbors != 3);

        return deathSentence;
    }
}

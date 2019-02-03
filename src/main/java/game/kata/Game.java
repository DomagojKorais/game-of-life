package game.kata;

public class Game {

    public static boolean judge (Cell cell){
        boolean deathSentence;
        if(cell.getStatus()==1 & cell.getAliveNeighbours()<2) {
            deathSentence = true;
            return deathSentence;
        }else if(cell.getStatus()==1 & cell.getAliveNeighbours()==2 | cell.getAliveNeighbours()==3 ) {
            deathSentence = false;
            return deathSentence;
        }else if(cell.getStatus()==1 & cell.getAliveNeighbours()>3) {
            deathSentence = true;
            return deathSentence;
        }else if(cell.getStatus()==0 & cell.getAliveNeighbours()==3){
            deathSentence = false;
            return deathSentence;
        }else{
            deathSentence = true;
            return deathSentence;
        }
    }
}

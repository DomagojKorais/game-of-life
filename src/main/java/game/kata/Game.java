package game.kata;

public class Game {

    public static boolean judge (Cell cell){
        boolean deathSentence;
        if(cell.status==1 & cell.aliveNeighbours<2) {
            deathSentence = true;
            return deathSentence;
        }else if(cell.status==1 & cell.aliveNeighbours==2 | cell.aliveNeighbours==3 ) {
            deathSentence = false;
            return deathSentence;
        }else if(cell.status==1 & cell.aliveNeighbours>3) {
            deathSentence = true;
            return deathSentence;
        }else if(cell.status==0 & cell.aliveNeighbours==3){
            deathSentence = false;
            return deathSentence;
        }else{
            deathSentence = true;
            return deathSentence;
        }
    }
}

package game.kata;
import org.junit.Test;

import static game.kata.Game.judge;
import static org.junit.Assert.assertEquals;

public class GameTest {

    @Test
    public void underPopulation(){
        Cell cell = new Cell(1,1);
        boolean deathSentence = judge(cell);
        assertEquals(deathSentence,true);
    }

    @Test
    public void balancedPopulation(){
        Cell cell = new Cell(1,3);
        boolean deathSentence = judge(cell);
        assertEquals(deathSentence,false);
    }

    @Test
    public void overPopulation(){
        Cell cell = new Cell(1,4);
        boolean deathSentence = judge(cell);
        assertEquals(deathSentence,true);
    }
    @Test
    public void newBorn(){
        Cell cell = new Cell(0,3);
        boolean deathSentence = judge(cell);
        assertEquals(deathSentence,false);
    }

}

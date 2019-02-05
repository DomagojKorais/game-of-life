package game.kata;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CellTest {

    private static final Cell newBorn = new Cell(1,2);

    @Test
    public void checkCellInception(){
        assertEquals(newBorn.getStatus(),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkWrongCellInceptionNeighbours(){
        Cell neverBorn = new Cell(1,10);
        assertEquals(neverBorn.getStatus(),10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkWrongCellInception(){
        Cell neverBorn = new Cell(2,2);
        assertEquals(neverBorn.getStatus(),1);
    }

    @Test
    public void checkSadEvolution(){
        boolean die = true;
        Cell sadCell = new Cell(1,5);
        assertEquals(sadCell.evolve(die),0);
    }

    @Test
    public void checkHappyEvolution(){
        boolean die = false;
        Cell sadCell = new Cell(1,5);
        assertEquals(sadCell.evolve(die),1);
    }




}


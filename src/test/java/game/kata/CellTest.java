package game.kata;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CellTest {

    private static final Cell newBorn = new Cell(1);
    private static final Cell deadCell = new Cell(0);

    @Test
    public void checkCellInception(){
        assertEquals(newBorn.getStatus(),1);
    }

    @Test
    public void checkDeadCell(){
        assertEquals(deadCell.getStatus(),0);
    }

//    @Test(expected = IllegalArgumentException.class) // ALL NO LONGER RELEVANT
//    public void checkWrongCellInceptionNeighbours(){
//        Cell neverBorn = new Cell(1,10);
//        assertEquals(neverBorn.getStatus(),10);
//    }

//    @Test(expected = IllegalArgumentException.class)
//    public void checkWrongCellInception(){
//        Cell neverBorn = new Cell(2,2);
//        assertEquals(neverBorn.getStatus(),1);
//    }

//    @Test
//    public void checkSadEvolution(){
//        Cell sadCell = new Cell(1,5);
//        assertEquals(sadCell.evolve(),0);
//    }

//    @Test
//    public void checkHappyEvolution(){
//        Cell happyCell = new Cell(1,5);
//        assertEquals(happyCell.evolve(),0);
//    }




}


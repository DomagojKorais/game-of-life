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

    @Test
    public void checkSadEvolution(){
        Cell sadCell = new Cell(1);
        assertEquals(sadCell.evolve(5).getStatus(),0);
    }

    @Test
    public void checkHappyEvolution(){
        Cell happyCell = new Cell(1);
        assertEquals(happyCell.evolve(3).getStatus(),1);
    }
}


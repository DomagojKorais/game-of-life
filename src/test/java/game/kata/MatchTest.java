package game.kata;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MatchTest {

    private int[][] buildTestMatrix(){
        return new int[][] {
            {0,0,0,0,0,0,0,0,},
            {0,0,0,0,1,0,0,0,},
            {0,0,0,1,1,0,0,0,},
            {0,0,0,0,0,0,0,0,}
        };
   }

//    @Test
//    public void constructor(){
//        int NumberOfgenerations = 0;
//        int[][] testMatrix = buildTestMatrix();
//        Grid grid = new Grid(testMatrix);
//        Match match = new Match(NumberOfgenerations,grid);
//        assertEquals(match.getGenerations(),NumberOfgenerations);
//    }

    @Test
    public void printGeneration(){
        int NumberOfgenerations = 10;
        int[][] testMatrix = buildTestMatrix();
        Grid grid = new Grid(testMatrix);
        Match match = new Match(grid);
        //Intercept output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        match.printCurrentMatchStatus();
        assertEquals(  "Generation: 0\n" +
                                "\n" +
                                "00000000\n" +
                                "00001000\n" +
                                "00011000\n" +
                                "00000000\n" + "\n\n", outContent.toString());

    }

    @Test
    public void play() {
        int numIterations = 2;
        int[][] testMatrix = buildTestMatrix();
        Grid grid = new Grid(testMatrix);
        Match match = new Match(grid);
        //Intercept output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // After this all System.out.println() statements will come to outContent stream.
        match.play(numIterations);
        assertEquals(  "Generation: 0\n" +
                "\n" +
                "00000000\n" +
                "00001000\n" +
                "00011000\n" +
                "00000000\n"+"\n\n"
                +"Generation: 1\n" +
                "\n" +
                "00000000\n" +
                "00011000\n" +
                "00011000\n" +
                "00000000\n"+"\n\n"
                +"Generation: 2\n" +
                "\n" +
                "00000000\n" +
                "00011000\n" +
                "00011000\n" +
                "00000000\n"+"\n\n", outContent.toString());

    }

}

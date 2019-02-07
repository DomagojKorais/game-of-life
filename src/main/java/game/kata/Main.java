package game.kata;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        GridReader reader = new GridReader();
        String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
        System.out.println(filename);
        Grid grid = reader.parseGridFromFile(filename);

        int iterations = 100;

        Match match = new Match(iterations, grid);

        match.play();

        }
}





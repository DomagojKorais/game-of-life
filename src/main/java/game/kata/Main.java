package game.kata;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //parte non funzionante
        System.out.println("a");
        GridReader reader = new GridReader();
        String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
        System.out.println(filename);
        Grid grid = reader.parseGridFromFile(filename);
        //fine parte non funzionante
        int iterations = 10;
        Match match = new Match(iterations, grid);
        match.play();

        }
}





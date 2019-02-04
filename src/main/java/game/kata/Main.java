package game.kata;

import java.io.FileNotFoundException;
import java.util.Objects;

public class Main {
    public static void main() throws FileNotFoundException {

        String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
        GridReader reader = new GridReader();
        Grid grid = reader.parseGridFromFile(filename);
    }
}
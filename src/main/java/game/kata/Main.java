package game.kata;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        GridReader reader = new GridReader();
        String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
        Match match = reader.parseGridFromFile(filename);
        int iterations = 10;
        match.play(iterations);
    }
}





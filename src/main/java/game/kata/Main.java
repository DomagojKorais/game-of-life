package game.kata;

public class Main {
    public static void main() {

        String path = "grid";
        GridReader reader = new GridReader();
        char[][] matrix = reader.readGrid(path);

    }
}
package game.kata;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {

        try{
            int generations = Integer.parseInt(args[0]);

            GridReader reader = new GridReader();
            String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
            Match match = reader.parseFromFile(filename);
            match.play(generations);

        }

        catch(NumberFormatException e){
            System.out.println("\nYour input is not a number");

        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("\nPlease enter the number of generations");
        }

    }
}





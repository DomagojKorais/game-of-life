package game.kata;

import java.io.IOException;

public class Main {
    private static final String strFormat =
            "Command-line format: java <class> <grid file> <number of iterations>\n" +
            "where:\n" +
            "    <class> is the class file Main from game-of-life;\n" +
            "    <grid file> is the file containing the starting grid data;\n" +
            "    <number of iterations> is the number of iterations to be computed\n" +
            "\n";

    private static final GridReader reader = new GridReader();

    public static void main(String[] args) {
        int generations;
        Match match;

        try {
            generations = Integer.parseInt(args[1]);
            match = reader.parseFromFile(args[0]);

            match.play(generations, false);
        }
        catch (IOException e) {
            System.err.println("\nFile '" + args[0] + "' not found");
            System.exit(1);
        }
        catch (NumberFormatException e) {
            System.err.println("\nInvalid option provided for the number of iterations\n");
            System.err.println(strFormat);
            System.exit(1);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("\nNot enough arguments provided\n");
            System.err.println(strFormat);
            System.exit(1);
        }

    }
}



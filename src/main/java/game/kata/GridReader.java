package game.kata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GridReader {

    public String[] readFile(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        ArrayList<String> validLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine().trim();
            if (currentLine.length() > 0)
                validLines.add(currentLine);
        }
        String[] retval = new String[validLines.size()];
        validLines.toArray(retval);
        return retval;
    }

    public void parseGrid(String[] lines) throws IllegalArgumentException {
        String[] line_words;
        Pattern isolateWords = Pattern.compile("\\s+|\\b(?=[^\\s\\w])|(?<=[^\\s\\w])\\b");
        int generation, rows, columns;

        if (lines.length < 2)
            throw new IllegalArgumentException(("Input file too short"));

        line_words = isolateWords.split(lines[0]);
        if (!line_words[0].equalsIgnoreCase("generation"))
            throw new IllegalArgumentException("Missing generation number declaration");
        try {
            generation = Integer.parseInt(line_words[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong generation number format");
        }

        line_words = isolateWords.split(lines[1]);
        if (line_words.length != 2)
            throw new IllegalArgumentException("Wrong matrix size declaration");
        try {
            rows =    Integer.parseInt(line_words[0]);
            columns = Integer.parseInt(line_words[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong matrix size declaration");
        }

        if (lines.length != 2 + rows)
            throw new IllegalArgumentException("Number of matrix rows does not match the declaration");
        for (int i = 2; i < lines.length; ++i)
            if (lines[i].length() != columns)
                throw new IllegalArgumentException("Number of matrix columns does not match the declaration");
    }

    public char[][] readGrid(String path) {

        char[][] matrix = null;

        try {
            Scanner scanner = new Scanner(new File(path));

            // skip the first row
            scanner.nextLine();

            // read the dimensions
            String[] strs = scanner.nextLine().trim().split("\\s+");
            int rows = Integer.parseInt(strs[0]);
            int cols = Integer.parseInt(strs[1]);

            // build the matrix
            matrix = new char[rows][cols];
            for (int i = 0; i < rows; ++i) {
                String line = scanner.nextLine();
                for (int j = 0; j < cols; ++j) {
                    matrix[i][j] = line.charAt(j);
                    //System.out.print(matrix[i][j]);
                }
                //System.out.print("\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File " + path + " not found;");
        }

        return matrix;
    }


}


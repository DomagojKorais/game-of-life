package game.kata;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.Function;
import java.util.regex.Pattern;

public class GridReader {

    private static final char aliveChar = '*';
    private static final char deadChar  = '.';
    private static final int  aliveInt  = 1;
    private static final int  deadInt   = 0;

    private static final Map<Integer,Integer> translationParseMap = Collections.unmodifiableMap(
            new ConcurrentSkipListMap<Integer,Integer>() {{
                put((int) deadChar, deadInt);   // Keys must be ints because Java
                put((int) aliveChar, aliveInt); // See String::chars for reason
            }}
    );

    String[] readFile(String path) throws FileNotFoundException {
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

    public Grid parseGrid(String[] lines) throws IllegalArgumentException {
        String[] line_words;
        Pattern isolateWords = Pattern.compile("\\s+|\\b(?=[^\\s\\w])|(?<=[^\\s\\w])\\b");
        int generation, rows, columns;

        if (lines.length < 2)
            throw new IllegalArgumentException(("Input too short"));

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
        for (int i = 2; i < lines.length; ++i) {
            if (lines[i].length() != columns)
                throw new IllegalArgumentException("Number of matrix columns does not consistently match the declaration");
        }

        int[][] finalMatrix;
        try {
            Function<String,int[]> translateRow =
                    (String line) -> line.chars().map(translationParseMap::get).toArray();
            finalMatrix =
                    Arrays.stream(lines).skip(2).map(translateRow).toArray(int[][]::new);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid matrix format");
        }
        return new Grid(generation, rows, columns, finalMatrix);
    }

    public Grid parseGridFromFile(String filename) throws FileNotFoundException {
        return parseGrid( readFile(filename) );
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


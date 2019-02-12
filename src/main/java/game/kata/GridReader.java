package game.kata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class GridReader {

    private static final char aliveChar = '*'; // TODO: maybe transform this to a <int,Cell> map?
    private static final char deadChar  = '.';
    private static final int  aliveInt  = 1;
    private static final int  deadInt   = 0;

    private static final Map<Integer,Integer> translationParseMap = Collections.unmodifiableMap(
            new HashMap<Integer,Integer>() {{
                put((int) deadChar, deadInt);   // Keys must be ints because Java
                put((int) aliveChar, aliveInt); // See String::chars for reason
            }}
    );

    String[] readFile(String path) throws IOException {
        Stream<String> flines = Files.lines(Paths.get(path));
        return flines.map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }

    private int parseGenerationRow(String[] line_words) throws IllegalArgumentException {
        final IllegalArgumentException exc = new IllegalArgumentException(
                "Wrong generation number specification format. Format: 'Generation <num>: [possibly comments]'" );
        int generation;

        if (line_words.length < 2 | !line_words[0].equalsIgnoreCase("generation"))
            throw exc;

        try {
            generation = Integer.parseInt(line_words[1]);
        }
        catch (NumberFormatException e) { throw exc; }

        if (line_words.length > 2)
            if (line_words[2].charAt(0) != ':')
                throw exc;

        return generation;
    }

    private int[] parseDimensionRow(String[] line_words) throws IllegalArgumentException {
        final IllegalArgumentException exc = new IllegalArgumentException(
                "Wrong matrix dimension specification format. Format: '<num_rows> <num_columns>' Numbers must be > 0" );
        int rows, columns;

        if (line_words.length != 2)
            throw exc;
        try {
            rows =    Integer.parseInt(line_words[0]);
            columns = Integer.parseInt(line_words[1]);
        }
        catch (NumberFormatException e) { throw exc; }

        if (rows <= 0 | columns <= 0)
            throw exc;

        return new int[] {rows, columns};
    }

    public Match parseGrid(String[] lines) throws IllegalArgumentException {
        final Pattern isolateWords = Pattern.compile("\\s+|(?<=\\S)\\b(?=\\S)");
//        Pattern isolateWords = Pattern.compile("\\s+|\\b(?=[^\\s\\w])|(?<=[^\\s\\w])\\b");
        int generation, rows, columns;

        if (lines.length < 2)
            throw new IllegalArgumentException(("Input too short"));

        generation = parseGenerationRow( isolateWords.split(lines[0]) );

        int[] dimensions = parseDimensionRow( isolateWords.split(lines[1]) );
        rows = dimensions[0];
        columns = dimensions[1];

        if (lines.length != 2 + rows)
            throw new IllegalArgumentException("Number of matrix rows does not match the declaration");
        for (int i = 2; i < lines.length; ++i) {
            if (lines[i].length() != columns)
                throw new IllegalArgumentException("Number of matrix columns does not consistently match the declaration");
        }

        Cell[][] finalMatrix;
        try {
            Function<String,Cell[]> translateRow =
                    (String line) -> line.chars().mapToObj((int i) -> new Cell(translationParseMap.get(i))).toArray(Cell[]::new);
            finalMatrix =
                    Arrays.stream(lines).skip(2).map(translateRow).toArray(Cell[][]::new);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid matrix format");
        }
        return new Match(new Grid(finalMatrix), generation);
    }

    public Match parseGridFromFile(String filename) throws IOException {
        return parseGrid( readFile(filename) );
    }

}


package game.kata;

import java.io.IOException;
import java.nio.file.Files;
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

    private static final Pattern isolateWords = Pattern.compile("\\s+|(?<=\\S)\\b(?=\\S)");
//        Pattern isolateWords = Pattern.compile("\\s+|\\b(?=[^\\s\\w])|(?<=[^\\s\\w])\\b");


    Stream<String> readFile(String path) throws IOException {
        return Files.lines(Paths.get(path));
    }

    private int parseGenerationRow(String gen_line) throws IllegalArgumentException {
        final IllegalArgumentException exc = new IllegalArgumentException(
                "Wrong generation number specification format. It must be the 1st non-empty line. Format: 'Generation <num>: [optional comments]'" );
        int generation;
        final String[] line_words = isolateWords.split(gen_line);

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

    private int[] parseDimensionRow(String dim_line) throws IllegalArgumentException {
        final IllegalArgumentException exc = new IllegalArgumentException(
                "Wrong matrix dimension specification format. It must be the 2nd non-empty line. Format: '<num_rows> <num_columns>' Numbers must be > 0" );
        int rows, columns;
        final String[] line_words = isolateWords.split(dim_line);

        if (line_words.length != 2)
            throw exc;
        try {
            rows    = Integer.parseInt(line_words[0]);
            columns = Integer.parseInt(line_words[1]);
        }
        catch (NumberFormatException e) { throw exc; }

        if (rows <= 0 | columns <= 0)
            throw exc;

        return new int[] {rows, columns};
    }

    public Match parse(Stream<String> s_lines) throws IllegalArgumentException {
        int generation, rows, columns;
        String[] lines = s_lines
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        if (lines.length < 2)
            throw new IllegalArgumentException("Input too short");

        String generation_line = lines[0];
        generation = parseGenerationRow( generation_line );

        String dimensions_line = lines[1];
        int[] dimensions = parseDimensionRow(dimensions_line);
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
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid matrix format");
        }
        return new Match(new Grid(finalMatrix), generation);
    }

    public Match parseFromFile(String filename) throws IOException {
        return parse( readFile(filename) );
    }

}


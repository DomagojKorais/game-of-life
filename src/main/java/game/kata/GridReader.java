package game.kata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class GridReader {

    private static final char aliveChar = '*'; // TODO: maybe transform this to a <int,Cell> map?
    private static final char deadChar  = '.';
    private static final int  aliveInt  = 1;
    private static final int  deadInt   = 0;

    private static final Map<Integer,Integer> translationParseMap = Collections.unmodifiableMap(
            new HashMap<Integer,Integer>() {{
                put((int) deadChar, deadInt);   // Keys must be ints because
                put((int) aliveChar, aliveInt); // String::chars returns ints
            }}
    );

    private static final Map<Integer,String> translationDumpMap = Collections.unmodifiableMap(
            new HashMap<Integer,String>() {{
                put(deadInt, String.valueOf(deadChar));   // Keys must be ints because
                put(aliveInt, String.valueOf(aliveChar)); // String::chars returns ints
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
        final String[] word_list = isolateWords.split(gen_line, 3);

        if (word_list.length < 2 | !word_list[0].equalsIgnoreCase("generation"))
            throw exc;

        try {
            generation = Integer.parseInt(word_list[1]);
        }
        catch (NumberFormatException e) { throw exc; }

        if (word_list.length > 2)
            if (word_list[2].charAt(0) != ':')
                throw exc;

        return generation;
    }

    private int[] parseDimensionsRow(String dim_line) throws IllegalArgumentException {
        final IllegalArgumentException exc = new IllegalArgumentException(
                "Wrong matrix dimension specification format. It must be the 2nd non-empty line. Format: '<num_rows> <num_columns>' Numbers must be > 0" );
        int rows, columns;
        final String[] word_list = isolateWords.split(dim_line, 3);

        if (word_list.length != 2)
            throw exc;
        try {
            rows    = Integer.parseInt(word_list[0]);
            columns = Integer.parseInt(word_list[1]);
        }
        catch (NumberFormatException e) { throw exc; }

        if (rows <= 0 | columns <= 0)
            throw exc;

        return new int[] {rows, columns};
    }

    public Match parse(Stream<String> s_lines) throws IllegalArgumentException {
        int generation, rows, columns;
        Spliterator<String> lines = s_lines
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .spliterator();

        ArrayList<String> first_two_lines = new ArrayList<>();
        lines.tryAdvance(first_two_lines::add); // This is why we need to use the Spliterator
        lines.tryAdvance(first_two_lines::add); // A Stream can't check out the first two rows without closing the Stream

        if (first_two_lines.size() < 2)
            throw new IllegalArgumentException("Input too short");

        generation       = parseGenerationRow( first_two_lines.get(0) );
        int[] dimensions = parseDimensionsRow( first_two_lines.get(1) );
        rows = dimensions[0];
        columns = dimensions[1];

        Cell[][] finalMatrix;
        try {
            Function<String,Cell[]> translateRow = (String line) -> line.chars()
                            .mapToObj((int i) -> new Cell(translationParseMap.get(i)))
                            .toArray(Cell[]::new);
            finalMatrix = StreamSupport.stream(lines, true)
                            .map(translateRow)
                            .toArray(Cell[][]::new);
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("Invalid matrix format. Only allowed characters are . and *");
        }

        if (finalMatrix.length != rows)
            throw new IllegalArgumentException("Number of matrix rows does not match the declaration");
        for (Cell[] finalMatrixRow : finalMatrix) {
            if (finalMatrixRow.length != columns)
                throw new IllegalArgumentException("Number of matrix columns does not consistently match the declaration");
        }

        return new Match(new Grid(finalMatrix), generation);
    }

    public Match parseFromFile(String filename) throws IOException {
        return parse( readFile(filename) );
    }

    public static Stream<String> dump(Match match) {
        Grid grid = match.getGrid();
        Stream<String> firstTwoRows = Stream.of(
                "Generation " + match.getGenerationNumber() + ":",
                grid.getRows() + " " + grid.getColumns()
        );
        Stream<String> matrix = IntStream.range(0, grid.getRows()).mapToObj( (int i) ->
                IntStream.range(0, grid.getColumns()).mapToObj( (int j) ->
                        translationDumpMap.get(grid.getCell(i, j).getStatus())
                        ).collect(Collectors.joining())
                );
        return Stream.concat(firstTwoRows, matrix);
    }
}


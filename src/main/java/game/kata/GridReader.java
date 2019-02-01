package game.kata;

import java.io.*;
import java.util.Scanner;

public class GridReader {

    public char[][] readGrid(String path) {

        char[][] matrix = null;

        try {
            Scanner scanner = new Scanner(new BufferedReader(new FileReader(new File(path))));

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


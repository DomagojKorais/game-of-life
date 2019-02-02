package game.kata;


//Takes in input a matrix of chars and returns a matrix of integers with 1= alive and 0 = death
public class CharsInterpreters {

    private char alive = '*';
    private char death = '.';

    public int[][] convert(char [][] matrix){
        int numberOfColumns = matrix[0].length;
        int numberOfRows = matrix.length;
        int [][] convertedMatrix = new int [numberOfRows][numberOfColumns] ;

        for(int i = 0; i<numberOfRows; i++){
            for(int j = 0; j<numberOfColumns; j++)
                convertedMatrix[i][j] = 0;
        }
        return convertedMatrix;

    }
}






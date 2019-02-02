package game.kata;


//Takes in input a matrix of chars and returns a matrix of integers with 1= alive and 0 = death
public class CharsInterpreters {

    private char alive = '*';
    private char death = '.';
    private int aliveNumeric = 1;
    private int deathNumeric = 0;


    public int[][] convert(char [][] matrix){
        int numberOfColumns = matrix[0].length;
        int numberOfRows = matrix.length;
        int [][] convertedMatrix = new int [numberOfRows][numberOfColumns] ;

        for(int i = 0; i<numberOfRows; i++){
            for(int j = 0; j<numberOfColumns; j++)
                if (matrix[i][j]==death){
                convertedMatrix[i][j] = deathNumeric;
                }else{
                    convertedMatrix[i][j] = aliveNumeric;
                }
        }
        return convertedMatrix;

    }
}






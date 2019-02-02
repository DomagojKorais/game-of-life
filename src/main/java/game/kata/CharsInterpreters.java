package game.kata;


//Takes in input a matrix of chars and returns a matrix of integers with 1= alive and 0 = death
public class CharsInterpreters {

    private char alive = '*';
    private char death = '.';
    private int aliveNumeric = 1;
    private int deathNumeric = 0;


    public int getNumberOfColumns(char [][] matrix){
        return matrix[0].length;
    }

    public int getNumberOfRows(char [][] matrix){
        return matrix.length;
    }

    public int[][] convert(char [][] matrix){
        int numberOfColumns = getNumberOfColumns(matrix);
        int numberOfRows = getNumberOfRows(matrix);
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






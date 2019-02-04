package game.kata;

public class Match {

    private int generations;
    private Grid currentGrid ;

    Match(int generations, Grid initialSetup) {
        this.generations = generations;
        this.currentGrid = initialSetup;

    }

    public int getGenerations() {
        return generations;
    }

    public void printCurrentMatchStatus(){
        System.out.print("Generation: "+generations+"\n\n");
        currentGrid.printGrid();

    }
}

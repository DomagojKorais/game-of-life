package game.kata;

public class Match {

    private int numberOfGenerations;
    private int currentGeneration;
    private Grid currentGrid ;

    Match(int numberOfGenerations, Grid initialSetup) {
        this.numberOfGenerations = numberOfGenerations;
        this.currentGeneration = 0;
        this.currentGrid = initialSetup;

    }

    public int getGenerations() {
        return numberOfGenerations;
    }

    public void printCurrentMatchStatus(){
        System.out.println("Generation: "+currentGeneration+"\n");
        currentGrid.printGrid();
        //System.out.println("\n");

    }

    public void play (){

        for (int i = 0; i < numberOfGenerations; i++) {
            this.currentGeneration = i;
            printCurrentMatchStatus();
            currentGrid =  currentGrid.evolve();
            System.out.println("\n");
        }

    }
}

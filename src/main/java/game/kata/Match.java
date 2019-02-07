package game.kata;

public class Match {

    private int currentGeneration;
    private Grid currentGrid ;

    Match(Grid initialSetup) { this(initialSetup, 0); }

    Match(Grid initialSetup, int generation) {
        currentGeneration = generation;
        currentGrid = initialSetup;
    }

    public int getGenerationNumber() { return currentGeneration; }
    public Grid getGrid() { return currentGrid; }

    public void printCurrentMatchStatus() {
        System.out.println("Generation: " + currentGeneration + "\n");
        currentGrid.printGrid();
        System.out.println("\n");
    }

    private void play_once() {
        currentGeneration += 1;
        currentGrid = currentGrid.evolve();
    }

    public void play (int numberOfIterations) {
        printCurrentMatchStatus();
        for (int i = 0; i < numberOfIterations; i++) {
            play_once();
            printCurrentMatchStatus();
        }
    }
}

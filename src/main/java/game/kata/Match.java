package game.kata;

public class Match {

    private int currentGeneration;
    private Grid currentGrid;

    Match(Grid initialSetup, int generation) {
        currentGeneration = generation;
        currentGrid = initialSetup;
    }

    public int getGenerationNumber() { return currentGeneration; }

    public Grid getGrid() { return currentGrid; }

    public void printCurrentMatchStatus() {
        GridReader.dump(this).forEach(System.out::println);
        System.out.println("\n");
    }

    private void play_once() {
        currentGeneration += 1;
        currentGrid = currentGrid.evolve();
    }

    public void play (int numberOfIterations, boolean printOnlyFinalIteration) {
        for (int i = 0; i < numberOfIterations; i++) {
            if (!printOnlyFinalIteration)
                printCurrentMatchStatus();
            play_once();
        }
        printCurrentMatchStatus();
    }
}

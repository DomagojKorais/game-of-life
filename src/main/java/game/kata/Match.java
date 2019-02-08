package game.kata;

import javax.swing.*;

public class Match {

    private int numberOfGenerations;
    private int currentGeneration;
    private Grid currentGrid ;
    JFrame frame = new JFrame("Game of Life");

    Match(int numberOfGenerations, Grid initialSetup) {
        this.numberOfGenerations = numberOfGenerations;
        this.currentGeneration = 0;
        this.currentGrid = initialSetup;
        int canvasSize = currentGrid.getColumns() * currentGrid.getRows();
        frame.setSize(canvasSize, canvasSize);

    }

    public int getGenerations() {
        return numberOfGenerations;
    }

    public void printCurrentMatchStatus(){
        System.out.println("Generation: "+currentGeneration+"\n");
        currentGrid.printGrid();
    }

    public void play () throws InterruptedException {

        MyNewCanvas displayMatch = new MyNewCanvas();
        //JFrame frame = new JFrame();
        int canvasSizeColumns=currentGrid.getColumns();
        int canvasSizeRows=currentGrid.getRows();
        frame.setSize(canvasSizeColumns, canvasSizeRows);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(displayMatch.view);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        for (int i = 0; i < numberOfGenerations; i++) {
            Thread.sleep(50);
            this.currentGeneration = i;
            printCurrentMatchStatus();
            displayMatch.addNewElement(currentGrid.getIntMatrix());
            currentGrid =  currentGrid.evolve();





            System.out.println("\n");
        }



    }
}

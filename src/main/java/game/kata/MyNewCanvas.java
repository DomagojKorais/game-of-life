package game.kata;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Random;

public class MyNewCanvas
{
    JLabel view;
    BufferedImage surface;
    Random random = new Random();

    public MyNewCanvas()
    {
        surface = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.red);
        g.fillRect(0,0,1000,1000);
        g.setColor(Color.red);

        g.dispose();

    }

    public void addNewElement(int [][] matrix) {

        Graphics g = surface.getGraphics();

        drawGrid(matrix,g);
        g.dispose();
        view.repaint();
    }

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        System.out.println("a");
        GridReader reader = new GridReader();
        String filename = Objects.requireNonNull(Main.class.getClassLoader().getResource("grid.txt")).getFile();
        System.out.println(filename);
        Grid grid = reader.parseGridFromFile(filename);

        int iterations = 10000;

        Match match = new Match(iterations, grid);

        match.play();




    }



    public void drawGrid(int [][] squares, Graphics g){
        int width = 1000/squares.length;

        for (int j = 0; j <squares[0].length ; j++) {
            for (int i = 0; i < squares.length; i++) {
                if (squares[i][j] == 1) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(j * width, i * width, width, width);
            }
        }

    }

}
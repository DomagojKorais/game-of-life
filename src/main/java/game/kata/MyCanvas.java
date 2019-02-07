package game.kata;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class MyCanvas
{
    JLabel view;
    BufferedImage surface;
    Random random = new Random();
    int[][] matrix;


    public MyCanvas(int [][] inputMatrix)
    {
        matrix = inputMatrix;

        surface = new BufferedImage(600,400,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0,0,600,400);
        g.setColor(Color.BLACK);

        g.dispose();

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {



                addNewElement(matrix);
            }
        };
        Timer timer = new Timer(200, listener);
        timer.start();
    }

    public void addNewElement(int[][] matrix) {


        Graphics g = surface.getGraphics();

        paint(matrix,g);
        g.dispose();
        view.repaint();
    }

    public static void main(String[] args)

    {
        int xxx = 1;
        int yyy = 0;
        int [][] matrix = {{yyy,xxx,yyy},{xxx,yyy,xxx}};
        int vertexes = 0;
        // Change this next part later to be dynamic.
        vertexes = 10;
        int canvasSize = vertexes * vertexes;
        JFrame frame = new JFrame();
        MyCanvas canvas = new MyCanvas(matrix);
        for (int i = 0; i < 10; i++) {

            frame.setSize(canvasSize, canvasSize);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(canvas.view);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
        }




    }
    public void paint(int[][] squares,Graphics g) {

        int width = 10;
        for (int j = 0; j <squares[0].length ; j++) {
            for (int i = 0; i < squares.length; i++) {
                if (squares[i][j] == 1) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                g.fillRect(i * width, j * width, width, width);
            }
        }



    }
}
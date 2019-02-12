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


    public MyNewCanvas()
    {
        int dim = 700;
        surface = new BufferedImage(dim,dim,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        Color grey = new Color(125, 125, 125 );
        g.setColor(grey);

        g.fillRect(0,0,dim,dim);
        g.setColor(Color.yellow);

        g.dispose();

    }
    public JFrame MyFrame()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(this.view);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        return frame;

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
        int width = 700/squares.length ;
        int offset = (700-width*squares.length)/2;

        for (int j = 0; j <squares[0].length ; j++) {
            for (int i = 0; i < squares.length; i++) {
                if (squares[i][j] == 1) {
                    g.setColor(Color.orange);
                } else {
                    g.setColor(Color.black);
                }
                g.fillRect(offset+j * width, offset+i * width, width, width);
            }
        }

    }

}
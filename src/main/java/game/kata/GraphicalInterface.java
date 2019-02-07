package game.kata;

import java.awt.*;
import javax.swing.*;

public class GraphicalInterface {

    JFrame frame= new JFrame("Game of life");
    GraphicalInterface(){
        // create a basic JFrame
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.frame.setSize(1000,1000);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);

    }


    public void drawRectangle(int[][] squares) {
        // add panel to main frame
        MyPanel panel = new MyPanel(squares);
        this.frame.add(panel);


    }
}

// create a panel that you can draw on.
class MyPanel extends JPanel {
    int [][] array;
    MyPanel(int[][] array){
        this.array = array;

    }
    public void paint(Graphics g) {
        //array di array. array interni sono le colonne
        int[][] squares = this.array;
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
import javax.swing.*;
import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main extends JFrame{

    public static final int TETRIS_SIDE = 50;
    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static final int ROW_MIN = 4;
    public static final int COL_MIN = 4;
    public static final int ROW_MAX = 17;
    public static final int COL_MAX = 28;
    public static int MAP_ROW;
    public static int MAP_COL;
    public static int WINDOW_HEIGHT;
    public static int WINDOW_WIDTH;


    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);

        MAP_ROW = askForRow(scan);
        MAP_COL = askForCol(scan);
        WINDOW_HEIGHT = TETRIS_SIDE * MAP_ROW + TETRIS_SIDE / 2;
        WINDOW_WIDTH = TETRIS_SIDE * MAP_COL + 100;
        scan.close();

        JFrame window = new JFrame("Tetris");
        GamePlay game = new GamePlay();
        window.add(game);
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setLocation((SCREEN_WIDTH - WINDOW_WIDTH) / 2, (SCREEN_HEIGHT - WINDOW_HEIGHT) / 2);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setVisible(true);
    }

    private static int askForCol(Scanner stdIn) {
        System.out.println(String.format("How many columns of Tetris do you want(%d - %d)? ", COL_MIN, COL_MAX));

        int col = 0;
        try {
            col = stdIn.nextInt();
            while ( col < 1 || col > COL_MAX){
                System.out.println(String.format("How many columns of Tetris do you want(%d - %d)? ", COL_MIN, COL_MAX));
                col = stdIn.nextInt();
            }
        } catch (InputMismatchException e){
            System.out.println("Please input an integer.");
        }

        return col;
    }

    private static int askForRow(Scanner stdIn){


        int row = 0;
        try {
            System.out.println(String.format("How many rows of Tetris do you want(%d - %d)? ", ROW_MIN, ROW_MAX));
            row = stdIn.nextInt();
            while ( row < 1 || row > ROW_MAX){
                System.out.println(String.format("How many rows of Tetris do you want(%d - %d)? ", ROW_MIN, ROW_MAX));
                row = stdIn.nextInt();
            }
        } catch (InputMismatchException e){
            System.out.println("Please input an integer.");
        }

        return row;
    }


}

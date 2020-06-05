import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MapGenerator{
    int[][] map;
    private final int row;
    private final int col;

    MapGenerator(int row, int col){
        this.row = row;
        this.col = col;

        // initialization
        map =  new int [row] [col];
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                map[i][j] = 0;
            }
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.GRAY);

        // the whole map background
        for (int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){

                int x = j * Main.TETRIS_SIDE;
                int y = i * Main.TETRIS_SIDE;

                if (map[i][j] == 1){
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, Main.TETRIS_SIDE, Main.TETRIS_SIDE);
                }
                g.drawRect(x, y, Main.TETRIS_SIDE, Main.TETRIS_SIDE);
            }
        }
    }


    // Adjusting Method ------------------------------------------------------------------------------------------------

    public void updateBrick(int y, int x){
        map[x][y] = 1;
    }

    // clear the full row and move the above rows downwards
    public void clearRow(int row){
        // clear up the row
        for (int i = 0; i < col; i++){
            map[row][i] = 0;
        }

        for (int i = row - 1; i >= 0; i--){
            if (isEmptyRow(i)){ // if the row is empty, then stop clearing immediately
                break;
            } else{ //else, move the above row downwards
                for(int j = 0; j < col; j++){
                    map[i+1][j] = map[i][j];
                }
            }
        }
    }

    // Detecting Method ------------------------------------------------------------------------------------------------

    // check if the given location is within the map bounds
    public boolean withinBounds(int x, int y){
        return xWithinBounds(x)  && yWithinBounds(y);
    }

    public boolean xWithinBounds(int x){
        if (0 <= x && x <= col - 1){
            return true;
        }

        return false;
    }

    public boolean yWithinBounds(int y){
        if (0 <= y && y <= row - 1){
            return true;
        }

        return false;
    }

    // if there exists brick at the current location within bounds
    public boolean brickExist(int x, int y){
        if (map[y][x] == 0){
            return false;
        }

        return true;
    }

    // check if the game is over by checking the most top row
    public boolean brickAtTop(){
        for (int i = 0; i < col; i++){
            if (map[0][i] == 1){
                return true;
            }
        }

        return false;
    }

    // check if the current game has any full rows to clear
    public int hasFullRow(){
        for (int i = 0; i < row; i++){
            if (isFullRow(i)){
                return i;
            }
        }

        return -1;
    }

    // if a row is full
    private boolean isFullRow(int row){
        for(int i = 0; i < col; i ++){
            if (map[row][i] == 0){
                return false;
            }
        }
        return true;
    }

    // if a row is empty
    private boolean isEmptyRow(int row){
        for(int i = 0; i < col; i ++){
            if (map[row][i] == 1){
                return false;
            }
        }
        return true;
    }


}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;
import java.util.Timer;

public class GamePlay extends JPanel implements KeyListener{
    private MapGenerator map;
    private Brick brick;
    private Timer myTimer;
    private TimerTask task;
    private boolean play;
    private int score;

    GamePlay(){
        play = true;
        brick = new Brick();
        map = new MapGenerator(Main.MAP_ROW,Main.MAP_COL);
        this.setFocusable(true);
        addKeyListener(this);

        myTimer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {

                if (!map.brickAtTop()){ // if the game hasn't end

                    if (canDescend()){
                        brick.descend();
                    } else {
                        updateMap(brick.getPoints());

                        while (map.hasFullRow() != -1){
                            map.clearRow(map.hasFullRow());
                            score += 20;
                        }

                        if (!map.brickAtTop()){
                            brick = new Brick();
                        }
                    }
                    repaint();
                } else {
                    myTimer.cancel();
                }
            }
        };

        myTimer.schedule(task, 0, 1000);
    }


    public void paint(Graphics g){
        map.draw(g);
        brick.draw(g);

        drawScore(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            if (canDescend()){
                brick.descend();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP){
            if (canRotate()){
                brick.rotate();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (canMoveRight()) {
                brick.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (canMoveLeft()) {
                brick.moveLeft();
            }
        }

        repaint();
    }

    public void drawScore(Graphics g){
        g.drawString("Score: " + score, Main.TETRIS_SIDE * Main.MAP_COL + 10, 10);
    }
    // Adjusting Method ------------------------------------------------------------------------------------------------

    private void updateMap(double[][] points){
        for (int i = 0; i < Brick.BRICK_NUM; i++){
            double x = brick.getX(points[i]);
            double y = brick.getY(points[i]);
            map.updateBrick((int)x, (int)y);
        }
    }


    // Detecting Method ------------------------------------------------------------------------------------------------
    // The brick can carry out certain action if there isn't brick after the

    private boolean canMoveLeft(){
        for (int i = 0; i < Brick.BRICK_NUM; i++) {
            double x = brick.getX(brick.getPoints()[i]);
            double y = brick.getY(brick.getPoints()[i]);

            double[] leftP = brick.leftPoint(x, y);
            int xx = (int) brick.getX(leftP);
            int yy = (int) brick.getY(leftP);

            if (!map.withinBounds(xx,yy) || map.brickExist(xx,yy)) {
                return false;
            }
        }

        return true;
    }

    private boolean canMoveRight(){
        for (int i = 0; i < Brick.BRICK_NUM; i++) {
            double x = brick.getX(brick.getPoints()[i]);
            double y = brick.getY(brick.getPoints()[i]);

            double[] rightP = brick.rightPoint(x, y);
            int xx = (int) brick.getX(rightP);
            int yy = (int) brick.getY(rightP);

            if (!map.withinBounds(xx,yy) || map.brickExist(xx,yy)) {
                return false;
            }
        }

        return true;
    }

    private boolean canDescend(){
        for (int i = 0; i < Brick.BRICK_NUM; i++) {
            double x = brick.getX(brick.getPoints()[i]);
            double y = brick.getY(brick.getPoints()[i]);

            double[] belowP = brick.belowPoint(x, y);
            int xx = (int) brick.getX(belowP);
            int yy = (int) brick.getY(belowP);

            if (!map.withinBounds(xx,yy) || map.brickExist(xx,yy)) {
                return false;
            }
        }

        return true;
    }

    private boolean canRotate(){
        for (int i = 0; i < Brick.BRICK_NUM; i++) {
            double[] point = brick.getPoints()[i];
            double x = (int) brick.getX(point) - brick.getX(brick.getCentre());
            double y = (int) brick.getY(point) - brick.getY(brick.getCentre());

            double[] rotatedP = brick.rotatedPoint(x,y);
            int xx = (int) brick.getX(rotatedP);
            int yy = (int )brick.getY(rotatedP);

            if (!map.withinBounds(xx,yy) || map.brickExist(xx,yy)) {
                return false;
            }
        }

        return true;
    }



    // Setter and Getter -----------------------------------------------------------------------------------------------

    public int getScore(){
        return score;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}

import javax.swing.*;
import java.awt.*;

public class Brick extends JButton {

    public static final int BRICK_TYPE_NUM = 5;
    public static final int BRICK_NUM = 4;
    public static final double ROTATION_ANGLE = Math.toRadians(-90);

    private int num; // I = 0; L = 1; O = 2; T = 3; Z = 4;
    private double[] centre;
    private double[][] points = new double[BRICK_NUM][2];


    Brick() {
        num = (int) (Math.random() * BRICK_TYPE_NUM);

        switch (num) {
            case 0: //I
                centre = new double[]{Math.floor(Main.MAP_COL / 2) - 0.5, 1.5};
                for (int i = 0; i < BRICK_NUM; i++) {
                    points[i] = new double[]{this.getX(centre) + 0.5, i};
                }
                break;
            case 1: //L
                centre = new double[]{Math.floor(Main.MAP_COL / 2), 1};
                points[0] = new double[]{this.getX(centre) - 1, 1};
                points[1] = new double[]{this.getX(centre) + 1, 1};
                points[2] = new double[]{this.getX(centre), this.getY(centre)};
                points[3] = new double[]{this.getX(centre) + 1, 0};
                break;
            case 2: //O
                centre = new double[]{Math.floor(Main.MAP_COL / 2) - 0.5, 0.5};
                points[0] = new double[]{this.getX(centre) - 0.5, 0};
                points[1] = new double[]{this.getX(centre) + 0.5, 0};
                points[2] = new double[]{this.getX(centre) - 0.5, 1};
                points[3] = new double[]{this.getX(centre) + 0.5, 1};
                break;
            case 3: //T
                centre = new double[]{Math.floor(Main.MAP_COL / 2), 1};
                points[0] = new double[]{this.getX(centre) - 1, 1};
                points[1] = new double[]{this.getX(centre) + 1, 1};
                points[2] = new double[]{this.getX(centre), this.getY(centre)};
                points[3] = new double[]{this.getX(centre), 0};
                break;
            case 4: //Z
                centre = new double[]{Math.floor(Main.MAP_COL / 2), 1};
                points[0] = new double[]{this.getX(centre) - 1, 0};
                points[1] = new double[]{this.getX(centre), 0};
                points[2] = new double[]{this.getX(centre), this.getY(centre)};
                points[3] = new double[]{this.getX(centre) + 1, 1};
                break;
        }
    }

    public void draw(Graphics g) {

        for (int i = 0; i < BRICK_NUM; i++) {
            double x = this.getX(points[i]);
            double y = this.getY(points[i]);

            g.setColor(Color.BLUE);
            g.fillRect((int) (x * Main.TETRIS_SIDE), (int) (y * Main.TETRIS_SIDE), Main.TETRIS_SIDE, Main.TETRIS_SIDE);
            g.drawRect((int) (x * Main.TETRIS_SIDE), (int) (y * Main.TETRIS_SIDE), Main.TETRIS_SIDE, Main.TETRIS_SIDE);
        }
    }

    // Adjusting Method ------------------------------------------------------------------------------------------------

    public void descend() {

        for (int i = 0; i < BRICK_NUM; i++) {
            double x = this.getX(points[i]);
            double y = this.getY(points[i]);

            points[i] = new double[]{x, y + 1};
        }

        double yc = getY(centre) + 1;
        setCentreY(yc);

    }

    public void moveRight() {

        for (int i = 0; i < BRICK_NUM; i++) {
            double x = this.getX(points[i]);
            double y = this.getY(points[i]);

            points[i] = new double[]{x + 1, y};
        }

        double xc = getX(centre) + 1;
        setCentreX(xc);

    }

    public void moveLeft() {

        for (int i = 0; i < BRICK_NUM; i++) {
            double x = this.getX(points[i]);
            double y = this.getY(points[i]);

            points[i] = new double[]{x - 1, y};
        }

        double xc = getX(centre) - 1;
        setCentreX(xc);

    }


    // The formula for rotation is [x' = x*cos - y*sin + c] and [y' = x*sin + y*cos + f];
    public void rotate() {

        for (int i = 0; i < BRICK_NUM; i++) {
            double x = this.getX(points[i]) - this.getX(centre);
            double y = this.getY(points[i]) - this.getY(centre);

            points[i] = new double[]{x * Math.cos(ROTATION_ANGLE) - y * Math.sin(ROTATION_ANGLE) + this.getX(centre),
                    x * Math.sin(ROTATION_ANGLE) + y * Math.cos(ROTATION_ANGLE) + this.getY(centre)};
        }

    }

    // Detecting Method ------------------------------------------------------------------------------------------------
    // Detect if point is out of bounds

    public double[] rotatedPoint(double x, double y) {
        double xx = x * Math.cos(ROTATION_ANGLE) - y * Math.sin(ROTATION_ANGLE) + this.getX(centre);
        double yy = x * Math.sin(ROTATION_ANGLE) + y * Math.cos(ROTATION_ANGLE) + this.getY(centre);
        return new double[]{xx, yy};
    }

    public double[] rightPoint(double x, double y) {
        return new double[]{x + 1, y};
    }

    public double[] leftPoint(double x, double y) {
        return new double[]{x - 1, y};
    }

    public double[] belowPoint(double x, double y) {
        return new double[]{x, y + 1};
    }

    // Getter and Setter -----------------------------------------------------------------------------------------------

    public double getX(double[] point) {
        return point[0];
    }

    public double getY(double[] point) {
        return point[1];
    }

    public double[] getCentre() {
        return centre;
    }

    public double[][] getPoints() {
        return points;
    }

    private void setCentreX(double x) {
        centre[0] = x;
    }

    private void setCentreY(double y) {
        centre[1] = y;
    }

    public String getType() {
        switch (num) {
            case 0:
                return "I";
            case 1:
                return "L";
            case 2:
                return "O";
            case 3:
                return "T";
            case 4:
                return "Z";
        }

        return null;
    }
}

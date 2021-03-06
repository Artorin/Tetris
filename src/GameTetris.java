import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;

public class GameTetris {

        final String TITLE_OF_PROGRAM = "Tetris";
        final int BLOCK_SIZE = 25;
        final int ARC_RADIUS = 6;
        final int FIELD_WIDTH = 10;
        final int FIELD_HEIGHT = 18;
        final int START_LOCATION = 180;
        final int FIELD_DX = 7;
        final int FIELD_DY = 26;
        final int LEFT = 37;
        final int RIGHT = 39;
        final int UP = 38;
        final int DOWN = 40;
        final int SHOW_DELAY = 350;
        final int[][][] SHAPES = {
                {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}, {4, 0x00f0f0}}, // I
                {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}, {4, 0xf0f000}}, // O
                {{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x0000f0}}, // J
                {{0,0,1,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf0a000}}, // L
                {{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0x00f000}}, // S
                {{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xa000f0}}, // T
                {{1,1,0,0}, {0,1,1,0}, {0,0,0,0}, {0,0,0,0}, {3, 0xf00000}}  // Z
        };
        final int[] SCORES = {100, 300, 700, 1500};
        int gameScores = 0;
        int [][] mine = new int [FIELD_HEIGHT + 1][FIELD_WIDTH];
        JFrame frame;
        Canvas canvasPanel = new Canvas();
        Random random = new Random();
        Figure figure = new Figure();
        boolean GameOver = false;
    final int[][] GAME_OVER_MSG = {
            {0,1,1,0,0,0,1,1,0,0,0,1,0,1,0,0,0,1,1,0},
            {1,0,0,0,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,1},
            {1,0,1,1,0,1,1,1,1,0,1,0,1,0,1,0,1,1,1,1},
            {1,0,0,1,0,1,0,0,1,0,1,0,1,0,1,0,1,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,1,0,1,0,1,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,1,0,0,1,0,0,1,1,0,0,1,1,1,0,0},
            {1,0,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,0,1,0},
            {1,0,0,1,0,1,0,1,0,0,1,1,1,1,0,1,1,1,0,0},
            {1,0,0,1,0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
            {0,1,1,0,0,1,0,0,0,0,0,1,1,0,0,1,0,0,1,0}};

    public static void main(String[] args) {
        new GameTetris().go();
    }
    void go() {
        frame = new JFrame(TITLE_OF_PROGRAM);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH * BLOCK_SIZE + FIELD_DX, FIELD_HEIGHT * BLOCK_SIZE + FIELD_DY);
        frame.setLocation(START_LOCATION, START_LOCATION);
        frame.setResizable(false);
        canvasPanel.setBackground(Color.BLACK);

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!GameOver) {
                    if (e.getKeyCode() == DOWN) figure.drop();
                    if (e.getKeyCode() == UP) figure.rotate();
                    if (e.getKeyCode() == LEFT || e.getKeyCode() == RIGHT) figure.move(e.getKeyCode());
                }
                canvasPanel.repaint();
            }
        });

        frame.setVisible(true);

        Arrays.fill(mine[FIELD_HEIGHT], 1);

        // main loop of game
        while (!GameOver) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) {e.printStackTrace(); }
            canvasPanel.repaint();
            if (figure.isTouchGround()) {
                figure.leaveOnTheGround();
                checkFilling();
                figure = new Figure();
                GameOver = figure.isGrossGround();
            } else {figure.stepDown();}
        }
    }

    void checkFilling(){}

    class Figure {
        boolean isTouchGround(){
            return false;
        }
        void leaveOnTheGround(){}
        void drop() {}
        void move(int direction) {}
        void rotate() {}

        boolean isGrossGround() {return false; }

        public void stepDown() {
        }
    }

    class Block {
        private int x, y;
        public Block(int x, int y) {
            setX(x);
            setY(y);
        }

        void setX(int x) {this.x = x;}
        void setY(int y) {this.y = y;}

        int getX() {return x;}
        int getY() {return y;}

        void paint(Graphics g, int color) {
            g.setColor(new Color(color));
            g.drawRoundRect(x*BLOCK_SIZE + 1, y* BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2, ARC_RADIUS, ARC_RADIUS);
        }
    }

    public class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ssss
 * @version 1.0.0
 * @ClassName Snake.java
 * @Description TODO
 * @createTime 2020年 11月 25日 09:23:00
 */

public class Snake {

    public static class Temp {
        private int value;
        private Temp next;

        public int getValue() {
            return value;
        }

        public Temp getNext() {
            return next;
        }

        public void setNext(Temp next) {
            this.next = next;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static Temp head;
    public static Temp tail;

    public static Timer timer;

    public static JFrame frame;
    public static JPanel panel;
    public static JButton[] labels;

    public static int[] snake;

    public static int moveStatus;
    public static int formerMoveStatus;
    public static int currentBlock;
    public static int foodPosition;

    // 可配置 速度
    public static final int SPEED = 200;
    // 可配置 场地大小
    public static final int SIZE_OF_FIELD = 10;
    public static final int SIZE_OF_BLOCKS = SIZE_OF_FIELD * SIZE_OF_FIELD;

    public static final int LEFT_BORDER = 0;
    public static final int RIGHT_BORDER = SIZE_OF_FIELD - 1;
    public static final int UP_BORDER = 0;
    public static final int DOWN_BORDER = SIZE_OF_FIELD - 1;

    public static final int LEFT_RIGHT_DISTANCE = 1;
    public static final int UP_DOWN_DISTANCE = SIZE_OF_FIELD;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public static final int NO_SNAKE = 0;
    public static final int IS_SNAKE = 1;

    public static void main(String[] args) {

        formerMoveStatus = LEFT;
        moveStatus = LEFT;
        snake = new int[SIZE_OF_BLOCKS];
        frame = new JFrame();
        panel = new JPanel();
        labels = new JButton[SIZE_OF_BLOCKS];
        for (int i = 0; i < SIZE_OF_BLOCKS; i++) {
            labels[i] = new JButton();
            labels[i].setBackground(Color.LIGHT_GRAY);
            panel.add(labels[i]);
        }
        frame.setTitle("贪吃蛇");
        frame.setBounds(0, 0, 540, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(SIZE_OF_FIELD, SIZE_OF_FIELD));
        panel.setBounds(0, 0, 500, 500);
        frame.add(panel);
        frame.setVisible(true);

        frame.setFocusable(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (formerMoveStatus == RIGHT) {
                        moveStatus = RIGHT;
                    } else {
                        moveStatus = LEFT;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (formerMoveStatus == LEFT) {
                        moveStatus = LEFT;
                    } else {
                        moveStatus = RIGHT;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (formerMoveStatus == DOWN) {
                        moveStatus = DOWN;
                    } else {
                        moveStatus = UP;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (formerMoveStatus == UP) {
                        moveStatus = UP;
                    } else {
                        moveStatus = DOWN;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        currentBlock = 44;
        snake[currentBlock] = IS_SNAKE;
        labels[currentBlock].setBackground(Color.BLACK);
        currentBlock = 45;
        snake[currentBlock] = IS_SNAKE;
        labels[currentBlock].setBackground(Color.GRAY);
        currentBlock = 46;
        snake[currentBlock] = IS_SNAKE;
        labels[currentBlock].setBackground(Color.GRAY);
        currentBlock = 44;

        foodPosition = 32;
        labels[foodPosition].setBackground(Color.ORANGE);
        Temp t1 = new Temp();
        t1.setValue(46);
        Temp t2 = new Temp();
        t2.setValue(45);
        Temp t3 = new Temp();
        t3.setValue(44);
        tail = t1;
        t1.setNext(t2);
        t2.setNext(t3);
        head = t3;
        timer();
    }

    public static void timer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                move();
            }
        };
        timer.schedule(task, 1000, SPEED);
    }

    public static void move() {
        if (moveStatus == LEFT) {
            if (currentBlock % SIZE_OF_FIELD == LEFT_BORDER) {
                gameOver();
                return;
            }
            currentBlock = currentBlock - LEFT_RIGHT_DISTANCE;
        } else if (moveStatus == RIGHT) {
            if (currentBlock % SIZE_OF_FIELD == RIGHT_BORDER) {
                gameOver();
                return;
            }
            currentBlock = currentBlock + LEFT_RIGHT_DISTANCE;
        } else if (moveStatus == UP) {
            if (currentBlock / SIZE_OF_FIELD == UP_BORDER) {
                gameOver();
                return;
            }
            currentBlock = currentBlock - UP_DOWN_DISTANCE;
        } else if (moveStatus == DOWN) {
            if (currentBlock / SIZE_OF_FIELD == DOWN_BORDER) {
                gameOver();
                return;
            }
            currentBlock = currentBlock + UP_DOWN_DISTANCE;
        }
        if (snake[currentBlock] == IS_SNAKE) {
            gameOver();
            return;
        }
        formerMoveStatus = moveStatus;
        snake[currentBlock] = IS_SNAKE;
        labels[currentBlock].setBackground(Color.BLACK);
        labels[head.getValue()].setBackground(Color.GRAY);
        Temp temp = new Temp();
        head.setNext(temp);
        head = temp;
        temp.setValue(currentBlock);
        if (foodPosition == currentBlock) {
            generateFood();
        } else {
            killTemp();
        }
    }

    public static void killTemp() {
        snake[tail.getValue()] = NO_SNAKE;
        labels[tail.getValue()].setBackground(Color.LIGHT_GRAY);
        tail = tail.getNext();
    }

    public static void gameOver() {
        timer.cancel();
        for (int i = 0; i < SIZE_OF_BLOCKS; i++) {
            labels[i].setBackground(Color.RED);
        }
    }

    public static void generateFood() {
        do {
            foodPosition = (int) (Math.random() * SIZE_OF_BLOCKS);
        }
        while (snake[foodPosition] == IS_SNAKE);
        labels[foodPosition].setBackground(Color.ORANGE);
    }
}
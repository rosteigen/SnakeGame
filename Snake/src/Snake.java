import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;

public class Snake extends JFrame {

    int width = 640;
    int height = 480;
    Point snake;
    Point comida;
    int widthPoint = 10;
    int heightPoint = 10;
    ImagenSnake imagenSnake;
    int direccion;
    long frecuencia = 30;
    ArrayList<Point> lista;
    boolean gameOver = false;

    public Snake() {

        setTitle("Snake");
        setSize(width, height);
        Dimension ventana = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(ventana.width / 2 - width / 2, ventana.height / 2 - height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(false);
        setUndecorated(true);

        Teclas teclas = new Teclas();
        this.addKeyListener(teclas);
        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);
        startGame();
        Momento momento = new Momento();
        Thread comienzo = new Thread(momento);
        comienzo.start();
        setVisible(true);

    }

    public static void main(String[] args) throws Exception {
        Snake s = new Snake();
    }

    /////////////////////

    /////////////////////////////
    public void startGame() {
        comida = new Point(200, 100);
        snake = new Point(320, 240);
        lista = new ArrayList<Point>();
        lista.add(snake);
        crearComida();
    }

    public void crearComida() {
        Random random = new Random();

        comida.x = random.nextInt(630);

        comida.y = random.nextInt(470);

    }

    public void actualizar() {

        lista.add(0, new Point(snake.x, snake.y));
        lista.remove(lista.size() - 1);

        for (int i = 1; i < lista.size(); i++) {
            Point punto = lista.get(i);
            if (snake.x == punto.x && snake.y == punto.y) {
                gameOver = true;
            }
        }

        if ((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10))
                && (snake.y < (comida.y + 10))) {
            lista.add(0, new Point(snake.x, snake.y));
            crearComida();
        }

        imagenSnake.repaint();
    }

    public class ImagenSnake extends JPanel {

        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (gameOver) {
                g.setColor(new Color(105,105,105));
            } else {
                g.setColor(new Color(128,128,128));
            }

            g.fillRect(0, 0, width, height);
            g.setColor(new Color(0, 255, 0));

            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    Point p = (Point) lista.get(i);
                    g.fillRect(p.x, p.y, widthPoint, heightPoint);
                }
            }

            g.setColor(new Color(255, 0, 0));
            g.fillRect(comida.x, comida.y, widthPoint, heightPoint);

            if (gameOver) {
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g.drawString("GAME OVER", 140, 200);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.drawString("SCORE " + (lista.size() - 1), 250, 240);
                g.drawString("R to Start New Game", 200, 320);
                g.drawString("ESC to Exit", 250, 360);
            }

        }

    }

    public class Teclas extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

            else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (direccion != KeyEvent.VK_DOWN) {
                    direccion = KeyEvent.VK_UP;
                }
            }

            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (direccion != KeyEvent.VK_UP) {
                    direccion = KeyEvent.VK_DOWN;
                }
            }

            else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (direccion != KeyEvent.VK_RIGHT) {
                    direccion = KeyEvent.VK_LEFT;
                }
            }

            else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (direccion != KeyEvent.VK_LEFT) {
                    direccion = KeyEvent.VK_RIGHT;
                }
            }

            else if (e.getKeyCode() == KeyEvent.VK_R) {
                gameOver = false;
                startGame();
            }
            else if (e.getKeyCode() == KeyEvent.VK_N) {
                gameOver = true;
                
            }
        }

    }

    public class Momento extends Thread {
        long last = 0;

        public void run() {
            while (true) {
                if (java.lang.System.currentTimeMillis() - last > frecuencia) {
                    if (!gameOver) {
                        if (direccion == KeyEvent.VK_UP) {
                            snake.y = snake.y - heightPoint;
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                        }
                        if (direccion == KeyEvent.VK_DOWN) {
                            snake.y = snake.y + heightPoint;
                            if (snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                            if (snake.y > height) {
                                snake.y = 0;
                            }
                        }
                        if (direccion == KeyEvent.VK_LEFT) {
                            snake.x = snake.x - widthPoint;
                            if (snake.x < 0) {
                                snake.x = width - widthPoint;
                            }
                            if (snake.x > width) {
                                snake.x = 0;
                            }
                        }
                        if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x = snake.x + widthPoint;
                            if (snake.x < 0) {
                                snake.x = width - widthPoint;
                            }
                            if (snake.x > width) {
                                snake.x = 0;
                            }
                        }
                    }

                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }

}

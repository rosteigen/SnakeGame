import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Dimension;
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

    public Snake() {
        setTitle("Snake");
        setSize(width, height);
        Dimension ventana = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(ventana.width / 2 - width / 2, ventana.height / 2 - height / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void startGame(){
        comida = new Point(200,200);
        snake = new Point(width / 2, height / 2);
        crearComida();
    }

    public void crearComida(){
        Random random = new Random();
        comida.x = random.nextInt(width);
        comida.y = random.nextInt(height);
    }

    public void actualizar(){
        imagenSnake.repaint();
        if((snake.x > comida.x-10) && (snake.x > comida.x+10) && (snake.y > comida.y-10) && (snake.y > comida.y+10)){
crearComida();
        }
    }

    public class ImagenSnake extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 255));
            g.fillRect(snake.x, snake.y, widthPoint, heightPoint);

            g.setColor(new Color(255,0,0));
            g.fillRect(comida.x, comida.y, widthPoint, heightPoint);

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
        }

    }

    public class Momento extends Thread {
        long last = 0;

        public void run() {
            while (true) {
                if (java.lang.System.currentTimeMillis() - last > frecuencia) {
                    if (direccion == KeyEvent.VK_UP) {
                        snake.y = snake.y - heightPoint;
                        if(snake.y < 0){
                            snake.y = height - heightPoint;
                        }
                        if(snake.y > height){
                            snake.y = 0;
                        }
                    }
                    if (direccion == KeyEvent.VK_DOWN) {
                        snake.y = snake.y + heightPoint;
                        if(snake.y < 0){
                            snake.y = height - heightPoint;
                        }
                        if(snake.y > height){
                            snake.y = 0;
                        }
                    }
                    if (direccion == KeyEvent.VK_LEFT) {
                        snake.x = snake.x - widthPoint;
                        if(snake.x < 0){
                            snake.x = width - widthPoint;
                        }
                        if(snake.x > width){
                            snake.x = 0;
                        }
                    }
                    if (direccion == KeyEvent.VK_RIGHT) {
                        snake.x = snake.x + widthPoint;
                        if(snake.x < 0){
                            snake.x = width - widthPoint;
                        }
                        if(snake.x > width){
                            snake.x = 0;
                        }
                    }

                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }

}

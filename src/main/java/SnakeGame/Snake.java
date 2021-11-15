package SnakeGame;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Point;
import java.util.Random;

public class Snake extends JFrame {

    int widht = 640;
    int height = 480;
    Point snake, comida;
    int widhtPointSnake = 10;
    int heightPointSnake = 10;
    int widhtPointComida = 10;
    int heightPointComida = 10;
    ImagenSnake imagensnake;
    int direccion;
    long frecuencia = 50;

    public Snake() {

        setTitle("Snake"); // PONEMOS TITULO, TAMAÑO Y VISIBILIDAD
        setSize(widht, height);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // CREAMOS UN DIMENSION PARA UBICAR LA PANTALLA EN EL CENTRO DE NUESTRA PANTALLA
        this.setLocation(dim.width / 2 - widht / 2, dim.height / 2 - height / 2);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ESCAPE PARA CERRAR LA APP

        this.addKeyListener(new Teclas()); //INSTANCIAMOS CLASE TECLAS PARA RECONOCER QUE TECLAS SE PRESIONAN

        snake = new Point(widht / 2, height / 2); // CREAMOS EL PUNTO DE SNAKE EN EL CENTRO DE NUESTRA PANTALLA
        comida = new Point(widht / 2, 200);
        

        imagensnake = new ImagenSnake(); // INSTANCIAMOS LA IMAGEN DE LA SNAKE
        this.getContentPane().add(imagensnake); // AGREGAMOS LA IMAGEN DE NUESTRA SNAKE AL PANEL.

        setVisible(true);

        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public static void main(String[] args) {
        Snake s = new Snake();

    }

    public void Crecimiento() {
        
        /*int aux = 0;
        if (direccion == KeyEvent.VK_LEFT) {
                
            }
            if (direccion == KeyEvent.VK_RIGHT) {
                widhtPointSnake = heightPointSnake;
                heightPointSnake = 10;
            }
            if (direccion == KeyEvent.VK_UP) {

                heightPointSnake = widhtPointSnake;
                widhtPointSnake = 10;

            }
            if (direccion == KeyEvent.VK_DOWN) {

                heightPointSnake = widhtPointSnake;
                widhtPointSnake = 10;
            }*/
        
    }

    public void CrearComida() {
        Random rnd = new Random();

        comida.x = rnd.nextInt(widht-10);
        comida.y = rnd.nextInt(height-10);
        
    }

    public void actualizar() {
        setVisible(true);
        imagensnake.repaint();

        if (snake.x == widht) {
            snake.move(0, snake.y);
        } else if (snake.y == height) {
            snake.move(snake.x, 0);
        } else if (snake.x == 0) {
            snake.move(widht, snake.y);
        } else if (snake.y == 0) {
            snake.move(snake.x, height);
        }

        if ((snake.x > (comida.x - 15)) && (snake.x < (comida.x + 15)) && (snake.y > (comida.y - 15)) && (snake.y < (comida.y + 15))) {
            CrearComida();
            Crecimiento();
            
        }

    }

    public class ImagenSnake extends JPanel {

        public void paintComponent(Graphics g) { // METODO PARA DEFINIR COMO SE VE LA SNAKE
            super.paintComponent(g);

            g.setColor(new Color(0, 255, 0));
            g.fillRect(snake.x, snake.y, widhtPointSnake, heightPointSnake);

            g.setColor(new Color(0, 0, 0));
            g.fillRect(comida.x, comida.y, widhtPointComida, heightPointComida);
        }
    }

    public class Teclas extends KeyAdapter {

        public void keyPressed(KeyEvent e) { // METODO PARA IDENTIFICAR QUE TECLAS SE PRESIONAN
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direccion != KeyEvent.VK_DOWN) {
                        direccion = KeyEvent.VK_UP;  
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direccion != KeyEvent.VK_UP) {
                        direccion = KeyEvent.VK_DOWN;   
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direccion != KeyEvent.VK_RIGHT) {
                        direccion = KeyEvent.VK_LEFT; 
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direccion != KeyEvent.VK_LEFT) {
                        direccion = KeyEvent.VK_RIGHT;
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                default:
                    break;
            }

        }
    }

    public class Momento extends Thread {

        long last = 0;
        int aux = 0;

        public void run() {

            while (true) {
                if ((java.lang.System.currentTimeMillis() - last) > frecuencia) {
                    if (direccion == KeyEvent.VK_LEFT) {
                        snake.x -= widhtPointSnake;
                        
                        aux = heightPointSnake;
                widhtPointSnake += 10;
                
                widhtPointSnake = heightPointSnake;
                heightPointSnake = aux;
                    }
                    if (direccion == KeyEvent.VK_RIGHT) {
                        snake.x += widhtPointSnake;
                    }
                    if (direccion == KeyEvent.VK_UP) {
                        snake.y -= heightPointSnake;
                    }
                    if (direccion == KeyEvent.VK_DOWN) {
                        snake.y += heightPointSnake;
                    }
                    actualizar();
                    last = java.lang.System.currentTimeMillis();
                }
            }
        }
    }

}

package com.main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.TimeUnit;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 1000; // Sets the canvas's width
    public static final int HEIGHT = WIDTH * 9/16; // Sets the canvas's height (16:9)

    public boolean running = false; // Is the game running?
    private Thread gameThread;

    private Ball ball;
    private Paddle paddle1;
    private Paddle paddle2;

    public MainMenu menu;

    public Game() {
        canvasSetup();

        new Window("SimplePong",this);

        initialise();

        this.addKeyListener(new KeyInput(paddle1, paddle2));
        this.addMouseListener(menu);
        this.addMouseMotionListener(menu);
        this.setFocusable(true);
    }

    private void initialise() {
        ball = new Ball();
        paddle1 = new Paddle(Color.GREEN, true);
        paddle2 = new Paddle(Color.RED, false);

        menu = new MainMenu(this);
    }

    private void canvasSetup() { // Configures canvas size
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void run() {
        // THIS GAME LOOP WAS NOT CREATED BY ME: https://github.com/AzizZayed
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                update();
                delta--;
                draw();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void draw() {
        // Initialise draw tools
        BufferStrategy buffer = this.getBufferStrategy();

        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = buffer.getDrawGraphics();

        // Draw background
        drawBackground(g);

        if (menu.active) { menu.draw(g); }

        // Draw ball
        ball.draw(g);

        // Draw paddles/score
        paddle1.draw(g);
        paddle2.draw(g);

        // Finally draw
        g.dispose();
        buffer.show();
    }

    private void drawBackground(Graphics g) {
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH,HEIGHT);

        // Dashed Line
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{10},0);
        g2d.setStroke(dashed);
        g2d.drawLine(WIDTH / 2,0,WIDTH / 2,HEIGHT);
    }

    private void update() {
        if (!menu.active) {
            ball.update(paddle1, paddle2);
            paddle1.update(ball);
            paddle2.update(ball);
        }
    }

    public void start() { // Starts the game
        gameThread = new Thread(this);
        gameThread.start();
        running = true;
    }

    public void stop() { // Stops the game
        try {
            gameThread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int sign(double d) {
        if (d <= 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        new Game();
    }

    public static int ensureRange(int val, int min, int max) {
        return Math.min(Math.max(val, min), max);
    }
}

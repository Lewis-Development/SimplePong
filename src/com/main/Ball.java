package com.main;

import java.awt.*;

public class Ball {
    public static final int SIZE = 16;
    public static boolean HARD = false;

    private int x, y;
    private int xVel, yVel;
    private int SPEED = 5;

    public Ball() {
        reset();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void reset() {
        // Starting position
        x = Game.WIDTH / 2 - SIZE / 2;
        y = Game.HEIGHT / 2 - SIZE / 2;

        // Starting velocities
        xVel = Game.sign(Math.random() * 2.0 - 1);
        yVel = Game.sign(Math.random() * 2.0 - 1);
    }

    public void changeXdir() {
        xVel *= -1;
    }

    public void changeYdir() {
        yVel *= -1;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, SIZE, SIZE);
    }

    public void update(Paddle p1, Paddle p2) {
        // Movement
        if (HARD) { SPEED = 8; };
        x += xVel * SPEED;
        y += yVel * SPEED;

        // Collisions
        if (y + SIZE >= Game.HEIGHT || y <= 0) {
            changeYdir();
        }

        if (x + SIZE >= Game.WIDTH) {
            p1.addScore();
            reset();
        }

        if (x <= 0) {
            p2.addScore();
            reset();
        }
    }
}

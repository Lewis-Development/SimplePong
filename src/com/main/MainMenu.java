package com.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends MouseAdapter {
    public boolean active;

    private Rectangle easyBtn;
    private String easyText = "Easy";
    private boolean eHightlight = false;

    private Rectangle hardBtn;
    private String hardText = "Hard";
    private boolean hHightlight = false;

    private Font font;

    public MainMenu(Game game) {
        active = true; // Main menu is active
        game.start(); // Starts the game

        int w, h, x, y;

        w = 300;
        h = 150;

        y = Game.HEIGHT / 2 - h / 2;
        x = Game.WIDTH / 4 - w / 2;

        easyBtn = new Rectangle(x, y, w, h);

        x = Game.WIDTH * 3/4 - w / 2;
        hardBtn = new Rectangle(x, y, w, h);

        font = new Font("Roboto", Font.PLAIN, 100);
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setFont(font);

        g.setColor(Color.BLACK);
        if (eHightlight) { g.setColor(Color.WHITE); }
        g2d.fill(easyBtn);

        g.setColor(Color.BLACK);
        if (hHightlight) { g.setColor(Color.WHITE); }
        g2d.fill(hardBtn);

        g.setColor(Color.WHITE);
        g2d.draw(easyBtn);
        g2d.draw(hardBtn);

        int strWidth;
        int strHeight;

        strWidth = g.getFontMetrics(font).stringWidth(easyText);
        strHeight = g.getFontMetrics(font).getHeight();

        g.setColor(Color.GREEN);
        g.drawString(easyText, (int) (easyBtn.getX() + easyBtn.getWidth() / 2 - strWidth / 2), (int) (easyBtn.getY() + easyBtn.getHeight() / 2 + strHeight / 4));

        strWidth = g.getFontMetrics(font).stringWidth(hardText);
        strHeight = g.getFontMetrics(font).getHeight();

        g.setColor(Color.RED);
        g.drawString(hardText, (int) (hardBtn.getX() + hardBtn.getWidth() / 2 - strWidth / 2), (int) (hardBtn.getY() + hardBtn.getHeight() / 2 + strHeight / 4));
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();

        if (easyBtn.contains(p)) {
            Ball.HARD = false;
            active = false;
        } else if (hardBtn.contains(p)) {
            Ball.HARD = true;
            active = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        Point p = e.getPoint();

        eHightlight = easyBtn.contains(p);
        hHightlight = hardBtn.contains(p);
    }
}

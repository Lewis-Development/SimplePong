package com.main;

import javax.swing.*;

public class Window {
    public Window(String title, Game game) {
        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exits the application on close
        frame.setResizable(false); // Disables the ability to resize the window
        frame.add(game); // Inherits from Canvas so it can be displayed in the JFrame
        frame.pack(); // Sizes the frame to allow all content to be displayed correctly
        frame.setLocationRelativeTo(null); // Positions the window in the center of the screen
        frame.setVisible(true); // Displays the JFrame window
    }
}

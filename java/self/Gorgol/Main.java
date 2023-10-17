package self.Gorgol;

import self.Gorgol.ui.SwitchPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");

        SwitchPanel switchPanel = new SwitchPanel();
        frame.add(switchPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
package self.Gorgol.engineUtilities;

import self.Gorgol.ui.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

public class Engine {
    private final JFrame frame;
    private final GamePanel gamePanel;
    private final GamePanelKeyListener gamePanelKeyListener;

    public Engine() {
        frame = new JFrame("Game");

        gamePanel = new GamePanel(650, 850);
        frame.add(gamePanel, BorderLayout.CENTER);

        gamePanelKeyListener = new GamePanelKeyListener(gamePanel);
        frame.addKeyListener(gamePanelKeyListener);

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void runEngine() {
        gamePanel.runGameLoop();
    }

}

package self.Gorgol.ui;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(int width, int height) {
        /* panel visualization settings */
        setPreferredSize(new Dimension(width, height));

        /* ---------------------------- */
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}

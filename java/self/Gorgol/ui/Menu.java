package self.Gorgol.ui;


import self.Gorgol.ui.utilities.CustomButton;
import self.Gorgol.ui.utilities.CustomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Menu extends JPanel implements ActionListener {
    public final Dimension sizes;
    private final ActionListener actionListener;

    private CustomPanel optionsPanel;

    public Menu(int width, int height, ActionListener actionListener) {
        super();

        this.sizes = new Dimension(width, height);
        this.actionListener = actionListener;

        setLayout(null);
        setPreferredSize(this.sizes);
        createGUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(139, 155, 180));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("open options command".equals(e.getActionCommand())) {
            optionsPanel.setVisible(!optionsPanel.isVisible());
        }
    }

    private void createGUI() {
        /* Image initialization */
        BufferedImage btnPanelSrc;
        BufferedImage optionsPanelSrc;
        BufferedImage btnSrc;
        Font font;

        try {
            btnPanelSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu bg.png")));
            btnSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu button.png")));
            optionsPanelSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/options bg.png")));

            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/better-vcr4.0.ttf")));
            font = font.deriveFont(13f);
        }
        catch (IOException ex) { throw new RuntimeException(); }
        catch (FontFormatException e) { throw new RuntimeException(e); }
        /* -------------------- */

        /* MAIN MENU BUTTONS */
        CustomPanel btnPanel = new CustomPanel(42, 245, 168, 240, btnPanelSrc);
        add(btnPanel);

        CustomButton btn = new CustomButton(33, 35, 102, 48, "Начать", font, btnSrc, actionListener);
        btn.setActionCommand("start game command");
        btnPanel.add(btn);

        CustomButton btn1 = new CustomButton(33, 96, 102, 48, "Настройки", font, btnSrc, this);
        btn1.setActionCommand("open options command");
        btnPanel.add(btn1);

        CustomButton btn2 = new CustomButton(33, 157, 102, 48, "Выйти", font, btnSrc, actionListener);
        btn2.setActionCommand("exit game command");
        btnPanel.add(btn2);

        /* OPTIONS MENU */
        optionsPanel = new CustomPanel(252, 245, 356, 240, optionsPanelSrc);
        optionsPanel.setVisible(false);
        add(optionsPanel);
    }
}

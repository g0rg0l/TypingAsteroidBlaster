package self.Gorgol.ui;


import self.Gorgol.sound.SoundHolder;
import self.Gorgol.ui.keyboard.Keyboard;
import self.Gorgol.ui.utilities.CustomButton;
import self.Gorgol.ui.utilities.CustomPanel;
import self.Gorgol.ui.utilities.InputField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Menu extends JPanel implements ActionListener {
    public final Dimension sizes;
    private final ActionListener engine;
    private OptionsPanel optionsPanel;
    private BufferedImage backgroundSrc;
    public Keyboard keyboard;
    public InputField inputField;
    private AutoInputController autoInputController;


    public Menu(int width, int height, ActionListener actionListener) {
        super();

        this.sizes = new Dimension(width, height);
        this.engine = actionListener;

        setLayout(null);
        setPreferredSize(this.sizes);
        createGUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(new Color(139, 155, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(backgroundSrc, 0, 0, null);

        inputField.render(g);
    }

    public void update(float dt) {
        autoInputController.update(dt);
        keyboard.update(dt);

        char autoInput = autoInputController.get();
        if (autoInput != Character.MIN_VALUE) {
            inputField.input(autoInput);
            keyboard.press(autoInput, 0.25f);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "open options command" -> { SoundHolder.INSTANCE.play(SoundHolder.Type.SIMPLE_BUTTON_CLICK); optionsPanel.setVisible(!optionsPanel.isVisible()); }
        }
    }

    private void createGUI() {
        /* Image initialization */
        BufferedImage btnPanelSrc;
        BufferedImage optionsPanelSrc;
        BufferedImage btnSrc;
        BufferedImage leftArrowButtonSrc;
        BufferedImage rightArrowButtonSrc;
        BufferedImage questionButtonSrc;
        BufferedImage inputFieldSrc;
        Font font;

        try {
            int n = 1 + (int) (Math.random() * 9);
            backgroundSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/background/" + n + ".png")));

            btnPanelSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu bg.png")));
            btnSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu button.png")));
            optionsPanelSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/options bg.png")));
            leftArrowButtonSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/left arrow button.png")));
            rightArrowButtonSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/right arrow button.png")));
            questionButtonSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/question button.png")));
            inputFieldSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/input field background.png")));

            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/better-vcr4.0.ttf")));
            font = font.deriveFont(13f);
        }
        catch (IOException ex) { throw new RuntimeException(); }
        catch (FontFormatException e) { throw new RuntimeException(e); }
        /* -------------------- */

        /* MAIN MENU BUTTONS */
        CustomPanel btnPanel = new CustomPanel(42, 245, 168, 240, btnPanelSrc);
        add(btnPanel);

        CustomButton btn = new CustomButton(33, 35, 102, 48, "Начать", font, btnSrc, engine);
        btn.setActionCommand("start game command");
        btnPanel.add(btn);

        CustomButton btn1 = new CustomButton(33, 96, 102, 48, "Настройки", font, btnSrc, this);
        btn1.setActionCommand("open options command");
        btnPanel.add(btn1);

        CustomButton btn2 = new CustomButton(33, 157, 102, 48, "Выйти", font, btnSrc, engine);
        btn2.setActionCommand("exit game command");
        btnPanel.add(btn2);


        /* OPTIONS MENU */
        optionsPanel = new OptionsPanel(optionsPanelSrc, font.deriveFont(9f));
        add(optionsPanel);

        CustomButton soundLeftArrowButton = new CustomButton(140, 36, 28, 28, leftArrowButtonSrc, optionsPanel);
        soundLeftArrowButton.addActionListener(engine);
        soundLeftArrowButton.setActionCommand("downgrade music command");
        optionsPanel.add(soundLeftArrowButton);

        CustomButton soundRightArrowButton = new CustomButton(172, 36, 28, 28, rightArrowButtonSrc, optionsPanel);
        soundRightArrowButton.addActionListener(engine);
        soundRightArrowButton.setActionCommand("upgrade music command");
        optionsPanel.add(soundRightArrowButton);

        CustomButton soundQuestionButton = new CustomButton(204, 36, 28, 28, questionButtonSrc, optionsPanel);
        soundQuestionButton.setActionCommand("show sound tip command");
        optionsPanel.add(soundQuestionButton);


        CustomButton difficultyLeftArrowButton = new CustomButton(140, 77, 28, 28, leftArrowButtonSrc, optionsPanel);
        difficultyLeftArrowButton.addActionListener(engine);
        difficultyLeftArrowButton.setActionCommand("downgrade difficulty command");
        optionsPanel.add(difficultyLeftArrowButton);

        CustomButton difficultyRightArrowButton = new CustomButton(172, 77, 28, 28, rightArrowButtonSrc, optionsPanel);
        difficultyRightArrowButton.addActionListener(engine);
        difficultyRightArrowButton.setActionCommand("upgrade difficulty command");
        optionsPanel.add(difficultyRightArrowButton);

        CustomButton difficultyQuestionButton = new CustomButton(204, 77, 28, 28, questionButtonSrc, optionsPanel);
        difficultyQuestionButton.setActionCommand("show difficulty tip command");
        optionsPanel.add(difficultyQuestionButton);


        CustomButton soloAsteroidSpawnButton = new CustomButton(28, 162, 60, 28, "Один", font.deriveFont(8f), btnSrc, optionsPanel);
        soloAsteroidSpawnButton.addActionListener(engine);
        soloAsteroidSpawnButton.setActionCommand("mark to solo command");
        optionsPanel.add(soloAsteroidSpawnButton);

        CustomButton bunchAsteroidSpawnButton = new CustomButton(94, 162, 60, 28, "Группа", font.deriveFont(8f), btnSrc, optionsPanel);
        bunchAsteroidSpawnButton.addActionListener(engine);
        bunchAsteroidSpawnButton.setActionCommand("mark to bunch command");
        optionsPanel.add(bunchAsteroidSpawnButton);

        /* DYNAMIC KEYBOARD */
        keyboard = new Keyboard(100, 606, 450, 121);
        add(keyboard);
        autoInputController = new AutoInputController();

        /* DYNAMIC INPUT FIELD */
        inputField = new InputField(75, 75, 500, 100, inputFieldSrc, font.deriveFont(24f));
    }
}

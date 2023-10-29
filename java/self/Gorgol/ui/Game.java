package self.Gorgol.ui;

import self.Gorgol.effects.EffectsHolder;
import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.AsteroidFactory;
import self.Gorgol.entity.objects.asteroids.words.WordGenerator;
import self.Gorgol.entity.objects.background.BackgroundElementFactory;
import self.Gorgol.sound.SoundHolder;
import self.Gorgol.ui.utilities.CustomButton;
import self.Gorgol.ui.utilities.CustomPanel;
import self.Gorgol.ui.utilities.TipType;
import self.Gorgol.ui.utilities.ToolTip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Game extends JPanel {
    public final Dimension sizes;
    public boolean isGameOvered;
    private final ObjectController objectController;
    private float playerDestroyTime = 0;
    private final AsteroidFactory asteroidFactory;
    private final BackgroundElementFactory backgroundElementFactory;
    private final WordGenerator wordGenerator;
    private final EffectsHolder effectsHolder;
    private final ActionListener engine;
    private final GamePanelInputHandler inputHandler;

    private CustomPanel endGameMenuPanel;
    private ToolTip languageTip;


    public Game(int width, int height, GameProperties properties, ActionListener actionListener) {
        super();

        EffectsHolder.INSTANCE.clear();

        this.sizes = new Dimension(width, height);
        this.engine = actionListener;
        this.isGameOvered = false;

        this.objectController = new ObjectController();
        this.inputHandler = new GamePanelInputHandler(objectController);
        this.asteroidFactory = new AsteroidFactory(
                properties.getAsteroidTimePerSpawn(),
                properties.getMaxAsteroidCountPerSpawn(),
                new Rectangle(0, -height, width, 0)
        );
        this.wordGenerator = new WordGenerator();
        this.backgroundElementFactory = new BackgroundElementFactory(
                10f,
                new Rectangle(0, -height, width, 0)
        );
        this.effectsHolder = EffectsHolder.INSTANCE;

        setLayout(null);
        setPreferredSize(this.sizes);
        createGUI();
    }

    /**
     * Method which renders all graphics on game JPanel per 1 game loop iteration
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sizes.width, sizes.height);

        objectController.renderObjects(g);
        effectsHolder.render(g);

        languageTip.render(g);
    }

    /**
     * Method which realizes all game logic per 1 game loop iteration
     */
    public void update(float dt) {
        if (!objectController.getPlayer().isDestroyed)
            objectController.updateObjects(dt);
        else {
            playerDestroyTime += dt;
            if (playerDestroyTime >= 1.2f) {
                isGameOvered = true;
                endGameMenuPanel.setVisible(true);
            }
        }

        /* creating asteroids */
        if (asteroidFactory.isReadyToCreate(dt)) {
            if (!objectController.getAsteroidHolder().isFull()) {
                objectController.add(wordGenerator.setWords(
                        asteroidFactory.create(objectController.getAsteroidHolder().getAllAliveAsteroids()),
                        objectController.getAsteroidHolder().getAllAliveWords()
                ));
            }
            else asteroidFactory.reset();
        }

        /* creating background elements */
        if (backgroundElementFactory.isReadyToCreate(dt)) objectController.add(backgroundElementFactory.create());

        /* dissolving effects */
        effectsHolder.update(dt);
        languageTip.update(dt);

        /* end game */
        if (!objectController.getPlayer().isDestroyed && objectController.getAsteroidHolder().collidedWithPlayer) {
            objectController.getPlayer().destroy();
            SoundHolder.INSTANCE.stopEngineSound();
            SoundHolder.INSTANCE.play(SoundHolder.Type.PLAYER_DEAD);
        }
    }

    public void processInput(char input) {
        if (objectController.getPlayer().isStaticFirst && objectController.getPlayer().isStaticSecond && playerDestroyTime == 0) {
            if (MenuPanelInputHandler.isLatinLetter(input)) {
                languageTip.show(TipType.LANGUAGE);
                SoundHolder.INSTANCE.play(SoundHolder.Type.LANGUAGE_TIP);
            }
            inputHandler.process(input);
        }
    }

    private void createGUI() {
        BufferedImage endGameMenuPanelSrc;
        BufferedImage btnSrc;
        Font font;

        try {
            endGameMenuPanelSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu bg.png")));
            btnSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/main menu button.png")));

            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/better-vcr4.0.ttf")));
            font = font.deriveFont(13f);
        }
        catch (IOException | FontFormatException ex) { throw new RuntimeException(); }


        endGameMenuPanel = new CustomPanel(241, 305, 168, 240, endGameMenuPanelSrc);
        add(endGameMenuPanel);
        endGameMenuPanel.setVisible(false);

        CustomButton btn = new CustomButton(33, 35, 102, 48, "Рестарт", font, btnSrc, engine);
        btn.setActionCommand("restart game command");
        endGameMenuPanel.add(btn);

        CustomButton btn1 = new CustomButton(33, 96, 102, 48, "В меню", font, btnSrc, engine);
        btn1.setActionCommand("end game command");
        endGameMenuPanel.add(btn1);

        CustomButton btn2 = new CustomButton(33, 157, 102, 48, "Выйти", font, btnSrc, engine);
        btn2.setActionCommand("exit game command");
        endGameMenuPanel.add(btn2);


        languageTip = new ToolTip(535, 735, 85, 85, font.deriveFont(10f), 5);
    }
}

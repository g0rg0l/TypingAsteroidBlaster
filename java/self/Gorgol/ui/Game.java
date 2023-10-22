package self.Gorgol.ui;

import self.Gorgol.effects.EffectsHolder;
import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.AsteroidFactory;
import self.Gorgol.entity.objects.asteroids.words.WordGenerator;
import self.Gorgol.entity.objects.background.BackgroundElementFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Game extends JPanel {
    public final Dimension sizes;
    public boolean isGameOvered;
    private final ObjectController objectController;
    private final AsteroidFactory asteroidFactory;
    private final BackgroundElementFactory backgroundElementFactory;
    private final WordGenerator wordGenerator;
    private final EffectsHolder effectsHolder;
    private final ActionListener engine;
    private final GamePanelInputHandler inputHandler;

    private JButton endGameButton;


    public Game(int width, int height, ActionListener actionListener) {
        super();

        EffectsHolder.INSTANCE.clear();

        this.sizes = new Dimension(width, height);
        this.engine = actionListener;
        this.isGameOvered = false;

        this.objectController = new ObjectController();
        this.inputHandler = new GamePanelInputHandler(objectController);
        this.asteroidFactory = new AsteroidFactory(5f, new Rectangle(0, -height, width, 0));
        this.wordGenerator = new WordGenerator();
        this.backgroundElementFactory = new BackgroundElementFactory(10f, new Rectangle(0, -height, width, 0));
        this.effectsHolder = EffectsHolder.INSTANCE;

        setPreferredSize(this.sizes);
        createGUI();
    }

    /**
     * Method which renders all graphics on game JPanel per 1 game loop iteration
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, sizes.width, sizes.height);

        objectController.renderObjects(g);
        effectsHolder.render(g);
    }

    /**
     * Method which realizes all game logic per 1 game loop iteration
     */
    public void update(float dt) {
        objectController.updateObjects(dt);

        /* creating asteroids */
        if (asteroidFactory.isReadyToCreate(dt)) {
            objectController.add(wordGenerator.setWords(
                    asteroidFactory.create(),
                    objectController.getAsteroidHolder().getAllAliveWords()
            ));
        }

        /* creating background elements */
        if (backgroundElementFactory.isReadyToCreate(dt)) objectController.add(backgroundElementFactory.create());

        /* dissolving effects */
        effectsHolder.update(dt);

        if (!isGameOvered && objectController.getAsteroidHolder().collidedWithPlayer) {
            isGameOvered = true;
            endGameButton.setVisible(true);
            endGameButton.setEnabled(true);
        }
    }

    public void processInput(char input) {
        inputHandler.process(input);
    }

    private void createGUI() {
        endGameButton = new JButton("End game");
        endGameButton.setVisible(false);
        endGameButton.setEnabled(false);
        endGameButton.addActionListener(engine);
        endGameButton.setActionCommand("end game command");
        this.add(endGameButton);
    }
}

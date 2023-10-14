package self.Gorgol.engineUtilities;

import self.Gorgol.effects.EffectsHolder;
import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.AsteroidFactory;
import self.Gorgol.entity.objects.asteroids.words.WordGenerator;
import self.Gorgol.entity.objects.background.BackgroundElementFactory;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public final Dimension sizes;
    private Thread gameLoop;
    private final ObjectController objectController;
    private final AsteroidFactory asteroidFactory;
    private final BackgroundElementFactory backgroundElementFactory;
    private final WordGenerator wordGenerator;
    private final EffectsHolder effectsHolder;


    public GamePanel(int width, int height) {
        /* panel visualization settings */
        this.sizes = new Dimension(width, height);
        setPreferredSize(this.sizes);
        /* ---------------------------- */

        this.objectController = new ObjectController();
        this.asteroidFactory = new AsteroidFactory(5f, new Rectangle(0, -height, width, 0));
        this.wordGenerator = new WordGenerator();
        this.backgroundElementFactory = new BackgroundElementFactory(10f, new Rectangle(0, -height, width, 0));
        this.effectsHolder = EffectsHolder.INSTANCE;

        setupGameLoop();
    }

    public void runGameLoop() {
        gameLoop.start();
    }

    public ObjectController getObjectController() { return objectController; }

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
    private void update(float dt) {
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
    }

    /**
     * The main game loop, that controls FPS, repainting and updating game's objects
     */
    private void setupGameLoop() {
        gameLoop = new Thread(() -> {
            final int FRAMES_PER_SECOND = 144;
            final long TIME_BETWEEN_UPDATES = 1000000000 / FRAMES_PER_SECOND;
            final int MAX_UPDATES_BETWEEN_RENDER = 1;

            long lastUpdateTime = System.nanoTime();
            long currTime = System.currentTimeMillis();

            while (true) {
                long now = System.nanoTime();
                long elapsedTime = System.currentTimeMillis() - currTime;
                currTime += elapsedTime;

                /* Updating (can be more than 1 times if that needed) */
                int updateCount = 0;
                while (now - lastUpdateTime >= TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BETWEEN_RENDER) {
                    float dt = (float) elapsedTime / 1000;
                    this.update(dt);
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }

                /* Waiting for needed time to this frame */
                if (now - lastUpdateTime >= TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }

                /* Rendering */
                this.repaint();

                /* Yielding thread while possible */
                long lastRenderTime = now;
                while (now - lastRenderTime < TIME_BETWEEN_UPDATES && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    now = System.nanoTime();
                }
            }
        });
    }
}

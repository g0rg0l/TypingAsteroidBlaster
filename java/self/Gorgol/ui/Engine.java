package self.Gorgol.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;

public class Engine implements ActionListener {
    private Game gamePanel;
    private Menu menuPanel;
    private final JFrame window;

    public Engine(JFrame window) {
        this.window = window;

        createGUI();
        setupGameLoop();
    }

    /**
     * Все компоненты игры будут отсылать ивенты в Engine, а этот метод их обрабатывает
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "exit game command" -> System.exit(0);
            case "start game command" -> goToNewGame();
            case "end game command" -> goToMenu();
        }
    }

    /**
     * Перерисовка всех экранов, вызываемая из sub-потока
     */
    private void repaint() {
        if (gamePanel != null && gamePanel.isVisible()) gamePanel.repaint();
        if (menuPanel.isVisible()) menuPanel.repaint();
    }

    /**
     * Update всех компонентов игры, вызываемая из sub-потока
     */
    private void update(float dt) {
        if (gamePanel != null && !gamePanel.isGameOvered) gamePanel.update(dt);
        if (menuPanel.isVisible()) menuPanel.update(dt);
    }

    /**
     * Инициализация sub-потока
     */
    private void setupGameLoop() {
        Thread gameLoop = new Thread(() -> {
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

        gameLoop.start();
    }

    /**
     * Метод, начинающий новую игру.
     * Закрывает меню и создаёт новую игру и отображает её на экране
     */
    private void goToNewGame() {
        menuPanel.setVisible(false);

        gamePanel = new Game(650, 850, this);
        window.add(gamePanel, BorderLayout.CENTER);
    }

    /**
     * Метод, возвращения в главное меню.
     * Закрывает законченную игру и отображает меню на экране
     */
    private void goToMenu() {
        gamePanel.setVisible(false);

        menuPanel.setVisible(true);
    }

    private void createGUI() {
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (gamePanel != null && !gamePanel.isGameOvered) {
                    gamePanel.processInput(e.getKeyChar());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (menuPanel.isVisible()) {
                    menuPanel.keyboard.press(e.getKeyChar());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (menuPanel.isVisible()) {
                    menuPanel.keyboard.release(e.getKeyChar());
                }
            }
        });

        menuPanel = new Menu(650, 850, this);
        window.add(menuPanel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        Engine engine = new Engine(frame);

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setFocusable(true);
    }
}

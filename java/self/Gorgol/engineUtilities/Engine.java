package self.Gorgol.engineUtilities;

import javax.swing.*;
import java.awt.*;

public class Engine implements IEngineParams {
    private final JFrame frame;
    private final GamePanel scene;
    private final GamePanelKeyListener gamePanelKeyListener;

    public Engine() {
        this.frame = new JFrame(IEngineParams.TITLE);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(IEngineParams.WINDOW_WIDTH, IEngineParams.WINDOW_HEIGHT);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setLocation(
                dim.width / 2 - IEngineParams.WINDOW_WIDTH / 2,
                dim.height / 2 - IEngineParams.WINDOW_HEIGHT / 2
        );

        this.scene = new GamePanel(IEngineParams.WINDOW_WIDTH, IEngineParams.WINDOW_HEIGHT);
        this.gamePanelKeyListener = new GamePanelKeyListener(this.scene);
        this.frame.addKeyListener(gamePanelKeyListener);

        this.frame.add(this.scene);

        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public void runEngine() {
        scene.runGameLoop();
    }

}

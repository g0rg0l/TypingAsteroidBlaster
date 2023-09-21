package self.Gorgol.engineUtilities;

import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.Player;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;
import self.Gorgol.entity.utilities.MathGameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanelKeyListener implements KeyListener {
    private final Player player;
    private final AsteroidHolder asteroidHolder;

    public GamePanelKeyListener(GamePanel panel) {
        ObjectController objectController = panel.getObjectController();
        this.player = objectController.getPlayer();
        this.asteroidHolder = objectController.getAsteroidHolder();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());

        if (e.getKeyChar() == 'f') {
            player.rotate(MathGameLogic.getAngleFromPlayerToAsteroid(player, asteroidHolder.selectRandom()));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

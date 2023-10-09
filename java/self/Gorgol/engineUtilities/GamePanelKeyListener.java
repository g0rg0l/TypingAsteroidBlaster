package self.Gorgol.engineUtilities;

import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.bullets.BulletsHolder;
import self.Gorgol.entity.objects.player.Player;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanelKeyListener implements KeyListener {
    private final Player player;
    private final AsteroidHolder asteroidHolder;

    private Asteroid selected = null;

    public GamePanelKeyListener(GamePanel panel) {
        ObjectController objectController = panel.getObjectController();
        this.player = objectController.getPlayer();
        this.asteroidHolder = objectController.getAsteroidHolder();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char ch = e.getKeyChar();

        /* Selecting */
        if (selected == null) {
            Asteroid typed = asteroidHolder.getByFirstChar(ch);
            /* Unexpected input */
            if (typed == null) {

            }
            /* Select correctly */
            else {
                typed.isSelected = true;
                typed.word.typeNext();
                selected = typed;
                checkWordToComplete();
            }
        }
        /* Typing already selected */
        else {
            /* Process next char */
            if (selected.word.match(ch)) {
                selected.word.typeNext();
                checkWordToComplete();
            }

            /* Wrong input, select is lost */
            else {
                selected.isSelected = false;
                selected = null;
            }
        }
    }

    private void checkWordToComplete() {
        if (selected.word.isCompleted()) {
            player.attack(player.body.getAngleTo(selected.body));
            selected = null;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

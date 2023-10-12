package self.Gorgol.engineUtilities;

import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.player.Player;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanelKeyListener implements KeyListener {
    private final Player player;
    private final AsteroidHolder asteroidHolder;
    private final CrossHair crossHair;

    private Asteroid selected = null;

    public GamePanelKeyListener(GamePanel panel) {
        ObjectController objectController = panel.getObjectController();
        this.player = objectController.getPlayer();
        this.asteroidHolder = objectController.getAsteroidHolder();
        this.crossHair = objectController.getCrossHair();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char ch = Character.toLowerCase(e.getKeyChar());

        if (Character.isAlphabetic(ch)) {
            /* Selecting */
            if (selected == null) {
                Asteroid typed = asteroidHolder.getByChar(ch);
                /* Unexpected input */
                if (typed == null) {

                }
                /* Select correctly */
                else {
                    crossHair.callToAsteroid(typed);
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
                    crossHair.incorrectUnCall();
                    selected = null;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    private void checkWordToComplete() {
        if (selected.word.isCompleted()) {
            player.attack(selected);
            crossHair.simpleUnCall();
            selected = null;
        }
    }
}

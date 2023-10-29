package self.Gorgol.ui;

import self.Gorgol.entity.objects.CrossHair;
import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;
import self.Gorgol.entity.objects.player.Player;
import self.Gorgol.sound.SoundHolder;

import java.awt.event.KeyEvent;


public class GamePanelInputHandler {
    private final Player player;
    private final AsteroidHolder asteroidHolder;
    private final CrossHair crossHair;

    private Asteroid selected = null;

    public GamePanelInputHandler(ObjectController objectController) {
        this.player = objectController.getPlayer();
        this.asteroidHolder = objectController.getAsteroidHolder();
        this.crossHair = objectController.getCrossHair();
    }

    public void process(char ch) {
        if (ch == KeyEvent.VK_ESCAPE) {
            crossHair.simpleUnCall();
            selected = null;
        }
        else if (Character.isAlphabetic(ch)) {
            ch = Character.toLowerCase(ch);

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
                    SoundHolder.INSTANCE.play(SoundHolder.Type.SELECT_ASTEROID);
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
                    SoundHolder.INSTANCE.play(SoundHolder.Type.INCORRECT_INPUT);
                }
            }
        }
    }

    private void checkWordToComplete() {
        if (selected.word.isCompleted()) {
            player.attack(selected);
            crossHair.simpleUnCall();
            selected = null;
        }
    }
}

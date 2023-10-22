package self.Gorgol.ui;

import self.Gorgol.entity.objects.CrossHair;
import self.Gorgol.entity.objects.ObjectController;
import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;
import self.Gorgol.entity.objects.player.Player;


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

    private void checkWordToComplete() {
        if (selected.word.isCompleted()) {
            player.attack(selected);
            crossHair.simpleUnCall();
            selected = null;
        }
    }
}

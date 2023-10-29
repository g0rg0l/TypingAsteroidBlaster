package self.Gorgol.sound;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class SoundHolder {
    public static SoundHolder INSTANCE = new SoundHolder();

    public float VOLUME = -20;

    private final HashMap<Type, URL> audioResources;
    private final ArrayList<Sound> repeatedSounds;

    public enum Type {
        ENGINE, PLAYER_DEAD,
        ASTEROID_EXPLOSION, ROCKET_LAUNCHING,
        SIMPLE_BUTTON_CLICK, GAME_RUN_BUTTON_CLICK, LANGUAGE_TIP,
        KEYBOARD_TYPED, KEYBOARD_RELEASED,
        SELECT_ASTEROID, INCORRECT_INPUT
    }

    private SoundHolder() {
        this.audioResources = new HashMap<>();
        this.repeatedSounds = new ArrayList<>();

        audioResources.put(Type.ASTEROID_EXPLOSION, Objects.requireNonNull(getClass().getResource("/sounds/asteroid explosion.wav")));
        audioResources.put(Type.ROCKET_LAUNCHING, Objects.requireNonNull(getClass().getResource("/sounds/rocket launching.wav")));
        audioResources.put(Type.SIMPLE_BUTTON_CLICK, Objects.requireNonNull(getClass().getResource("/sounds/simple button click.wav")));
        audioResources.put(Type.GAME_RUN_BUTTON_CLICK, Objects.requireNonNull(getClass().getResource("/sounds/game run button click.wav")));
        audioResources.put(Type.KEYBOARD_TYPED, Objects.requireNonNull(getClass().getResource("/sounds/keyboard key pressed.wav")));
        audioResources.put(Type.KEYBOARD_RELEASED, Objects.requireNonNull(getClass().getResource("/sounds/keyboard key released.wav")));
        audioResources.put(Type.ENGINE, Objects.requireNonNull(getClass().getResource("/sounds/engine.wav")));
        audioResources.put(Type.PLAYER_DEAD, Objects.requireNonNull(getClass().getResource("/sounds/player dead.wav")));
        audioResources.put(Type.LANGUAGE_TIP, Objects.requireNonNull(getClass().getResource("/sounds/language tip.wav")));
        audioResources.put(Type.SELECT_ASTEROID, Objects.requireNonNull(getClass().getResource("/sounds/select asteroid.wav")));
        audioResources.put(Type.INCORRECT_INPUT, Objects.requireNonNull(getClass().getResource("/sounds/incorrect input.wav")));
    }

    public void play(Type type) {
        new Thread(new Sound(audioResources.get(type), VOLUME)).start();
    }

    public void play(Type type, boolean isRepeated) {
        Sound sound = new Sound(audioResources.get(type), isRepeated, VOLUME);
        repeatedSounds.add(sound);

        new Thread(sound).start();
    }

    public void stopEngineSound() {
        for (Iterator<Sound> iterator = repeatedSounds.iterator(); iterator.hasNext(); ) {
            Sound sound = iterator.next();

            if (sound.isRepeated()) {

                sound.forceStop();
                iterator.remove();
                break;
            }
        }
    }

    public void setVolume(float volume) { VOLUME = volume; }
}

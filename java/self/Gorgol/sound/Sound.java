package self.Gorgol.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound implements Runnable {

    private final Clip audioClip;
    private boolean isRepeated;
    private final float volume;

    public Sound(URL audioFile, float volume) {
        this.isRepeated = false;
        this.volume = volume;

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(ais);
        }
        catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            throw new RuntimeException();
        }
    }

    public Sound(URL audioFile, boolean isRepeated, float volume) {
        this(audioFile, volume);
        this.isRepeated = isRepeated;
    }

    @Override
    public void run() {
        if (isRepeated) {
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);

        audioClip.setFramePosition(0);
        audioClip.start();
    }

    public void forceStop() {
        if (audioClip.isActive()) {
            audioClip.stop();
        }
    }

    public boolean isRepeated() { return isRepeated; }
}

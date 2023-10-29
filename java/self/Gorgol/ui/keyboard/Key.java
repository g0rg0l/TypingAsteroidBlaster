package self.Gorgol.ui.keyboard;

import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.sound.SoundHolder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Key {
    private final BufferedImage image;
    private final String keyCode;
    private BufferedImage currentImage;
    private HitBox body;
    private boolean isPressed = false;
    private float currentTime = 0;
    private float autoRelease = 0;

    public Key(float x, float y, float width, float height, BufferedImage image, String keyCode) {
        this.image = image;
        this.keyCode = keyCode;
        this.body = new HitBox(x * 1.5f, y * 1.5f, width * 1.5f, height * 1.5f);
        this.currentImage = image.getSubimage(0, 0, image.getWidth() / 3, image.getHeight());
    }

    public void render(Graphics g) {
        g.drawImage(currentImage,
                (int) body.x, (int) body.y, (int) (body.x + body.width), (int) (body.y + body.height),
                0, 0, currentImage.getWidth(), currentImage.getHeight(),
                null);
    }

    public void update(float dt) {
        if (isPressed && autoRelease != 0) {
            currentTime += dt;

            if (currentTime >= autoRelease) {
                release();
            }
        }
    }

    public void press() {
        if (!isPressed) {
            SoundHolder.INSTANCE.play(SoundHolder.Type.KEYBOARD_TYPED);

            currentImage = image.getSubimage(
                    image.getWidth() / 3, 0,
                    image.getWidth() / 3, image.getHeight()
            );
            isPressed = true;
            autoRelease = 0;
        }
    }

    public void press(float autoRelease) {
        press();

        currentTime = 0;
        this.autoRelease = autoRelease;
    }

    public void release() {
        if (isPressed) {
            SoundHolder.INSTANCE.play(SoundHolder.Type.KEYBOARD_RELEASED);

            currentImage = image.getSubimage(
                    0, 0,
                    image.getWidth() / 3, image.getHeight()
            );

            isPressed = false;
        }
    }

    public String getKeyCode() { return keyCode; }
}

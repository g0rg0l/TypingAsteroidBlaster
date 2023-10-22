package self.Gorgol.ui.keyboard;

import self.Gorgol.entity.utilities.HitBox;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Key {
    private final BufferedImage image;
    private final String keyCode;
    private BufferedImage currentImage;
    private HitBox body;
    private boolean isPressed = false;

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

    public void press() {
        if (!isPressed) {
            currentImage = image.getSubimage(
                    image.getWidth() / 3, 0,
                    image.getWidth() / 3, image.getHeight()
            );
            isPressed = true;
        }
    }

    public void release() {
        if (isPressed) {
            currentImage = image.getSubimage(
                    0, 0,
                    image.getWidth() / 3, image.getHeight()
            );

            isPressed = false;
        }
    }

    public String getKeyCode() { return keyCode; }
}

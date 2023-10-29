package self.Gorgol.ui.utilities;

import self.Gorgol.entity.utilities.HitBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Bar {
    private BufferedImage background;
    private BufferedImage foreground;
    private final HitBox body;
    private float scale;

    public Bar(float x, float y, float width, float height) {
        this.body = new HitBox(x, y, width, height);
        this.scale = 0.5f;

        createGUI();
    }

    public Bar(float x, float y, float width, float height, float scale) {
        this(x, y, width, height);
        this.scale = scale;
    }

    public void render(Graphics g) {
        g.drawImage(background,
                (int) body.x, (int) body.y,
                (int) (body.x + body.width), (int) (body.y + body.height),
                0, 0,
                background.getWidth(), background.getHeight(),
                null);

        g.drawImage(foreground,
                (int) body.x, (int) body.y,
                (int) (body.x + body.width * scale), (int) (body.y + body.height),
                0, 0,
                foreground.getWidth(), foreground.getHeight(),
                null);
    }

    public void downGrade() {
        scale = scale > 0.01 ? scale - 0.1f : scale;
    }

    public void upgrade() {
        scale = scale < 1 ? scale + 0.1f : scale;
    }

    private void createGUI() {
        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/bar background.png")));
            foreground = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/bar foreground.png")));
        }
        catch (IOException ex) { throw new RuntimeException(); }
    }
}

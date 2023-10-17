package self.Gorgol.entity.objects.background;

import self.Gorgol.entity.utilities.IRenderer;
import self.Gorgol.entity.utilities.IUpdatable;
import self.Gorgol.entity.utilities.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Background implements IUpdatable, IRenderer {
    public final PhysicsBody body;
    private final BufferedImage src;

    public Background(float x, float y, float width, float height, float speed) {
        this.body = new PhysicsBody(x, y, width, height, speed);

        try {
            this.src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/background/static background.png")));
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public void render(Graphics g) {
        if (body.y > 0) {
            g.drawImage(src,
                    0, 0, (int) body.width, (int) body.height,
                    0, (int) body.y, src.getWidth(), (int) (body.y + body.height),
                    null);
        }
        else {
            g.drawImage(src,
                    0, (int) -body.y, (int) body.width, (int) body.height,
                    0, 0, src.getWidth(), (int) (body.y + body.height),
                    null);
            g.drawImage(src,
                    0, 0, (int) body.width, (int) -body.y,
                    0, src.getHeight() + (int) body.y, src.getWidth(), src.getHeight(),
                    null);
        }
    }

    @Override
    public void update(float dt) {
        body.y -= body.speed * dt;

        if (-body.y >= body.height) {
            body.y = src.getHeight() - body.height;
        }
    }
}

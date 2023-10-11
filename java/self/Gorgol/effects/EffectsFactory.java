package self.Gorgol.effects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EffectsFactory {
    public static EffectsFactory INSTANCE = new EffectsFactory();
    private final BufferedImage bulletExplodeSrc;

    private EffectsFactory() {
        try {
            bulletExplodeSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/bullet explode.png")));
        }
        catch (IOException ex) { throw new RuntimeException(); }
    }

    public Effect create(EffectsType type,
                         float x, float y,
                         float width, float height) {
        Effect effect;
        switch (type) {
            case BULLET_EXPLODE -> effect = new Effect(
                    x, y,
                    width, height,
                    bulletExplodeSrc, EffectsType.BULLET_EXPLODE,
                    5, 0.05f
            );
            default -> effect = null;
        }

        return effect;
    }
}

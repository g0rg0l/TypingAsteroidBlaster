package self.Gorgol.effects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class EffectsFactory {
    public static EffectsFactory INSTANCE = new EffectsFactory();
    private final BufferedImage bulletExplodeSrc;
    private final BufferedImage playerExplodeSrc;

    private EffectsFactory() {
        try {
            bulletExplodeSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/shooting/bullet explode.png")));
            playerExplodeSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/player explode.png")));
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
            case PLAYER_EXPLODE -> effect = new Effect(
                    x, y,
                    width, height,
                    playerExplodeSrc, EffectsType.PLAYER_EXPLODE,
                    49, 0.025f
            );

            default -> effect = null;
        }

        return effect;
    }
}

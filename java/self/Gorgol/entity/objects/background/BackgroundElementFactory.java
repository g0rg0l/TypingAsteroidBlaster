package self.Gorgol.entity.objects.background;

import self.Gorgol.entity.utilities.AbstractAnimatedObjectFactory;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BackgroundElementFactory extends AbstractAnimatedObjectFactory {
    private final BufferedImage bigBlueStarSrc;
    private final BufferedImage bigRedStarSrc;
    private final BufferedImage blackHoleSrc;
    private final BufferedImage rotaryStarSrc;
    private final BufferedImage smallRotaryStarSrc;
    private final BufferedImage planetSrc;

    public BackgroundElementFactory(float timePerSpawn, Rectangle spawnArea) {
        super(timePerSpawn, spawnArea, 150, 75, 3);

        try {this.bigBlueStarSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/big blue star.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.bigRedStarSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/big red star.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.blackHoleSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/black hole.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.rotaryStarSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/rotary star.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.smallRotaryStarSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/small rotary star.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.planetSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/planet.png")));}
        catch (IOException e) { throw new RuntimeException(); }
    }

    @Override
    protected AnimatedObject createRandomInstance() {
        PhysicsBody rb = getRandomPhysicsBody();

        int n = (int) (Math.random() * 6);
        switch (n) {
            case 0 -> { return new BackgroundElement(rb.x, rb.y, 640, 360, rb.speed, bigBlueStarSrc, 9); }
            case 1 -> { return new BackgroundElement(rb.x, rb.y, 640, 360, rb.speed, bigRedStarSrc, 9); }
            case 2 -> { return new BackgroundElement(rb.x, rb.y, 80*2, 80*2, rb.speed, blackHoleSrc, 9); }
            case 3 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 63*2, rb.speed, rotaryStarSrc, 9); }
            case 4 -> { return new BackgroundElement(rb.x, rb.y, 9*2, 17*2, rb.speed, smallRotaryStarSrc, 9); }
            case 5 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 96*2, rb.speed, planetSrc, 77); }
            default -> { return null; }
        }
    }
}

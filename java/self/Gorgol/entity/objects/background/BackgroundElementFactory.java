package self.Gorgol.entity.objects.background;

import self.Gorgol.entity.utilities.AbstractAnimatedObjectFactory;
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
    private final BufferedImage planet1Src;
    private final BufferedImage planet2Src;
    private final BufferedImage planet3Src;
    private final BufferedImage planet4Src;

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

        try {this.planet1Src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/planet_1.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.planet2Src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/planet_2.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.planet3Src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/planet_3.png")));}
        catch (IOException e) { throw new RuntimeException(); }

        try {this.planet4Src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/planet_4.png")));}
        catch (IOException e) { throw new RuntimeException(); }
    }

    public final BackgroundElement[] create() {
        int n = (int) (1 + Math.random() * maxEntityPerSpawn);
        BackgroundElement[] entities = new BackgroundElement[n];

        for (int i = 0; i < n; i++)
            entities[i] = createRandomInstance();

        return entities;
    }

    protected BackgroundElement createRandomInstance() {
        PhysicsBody rb = getRandomPhysicsBody();

        int n = (int) (Math.random() * 9);
        switch (n) {
            case 0 -> { return new BackgroundElement(rb.x, rb.y, 640, 360, rb.speed, bigBlueStarSrc, 9); }
            case 1 -> { return new BackgroundElement(rb.x, rb.y, 640, 360, rb.speed, bigRedStarSrc, 9); }
            case 2 -> { return new BackgroundElement(rb.x, rb.y, 80*2, 80*2, rb.speed, blackHoleSrc, 9); }
            case 3 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 63*2, rb.speed, rotaryStarSrc, 9); }
            case 4 -> { return new BackgroundElement(rb.x, rb.y, 9*2, 17*2, rb.speed, smallRotaryStarSrc, 9); }
            case 5 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 96*2, rb.speed, planet1Src, 77); }
            case 6 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 96*2, rb.speed, planet2Src, 77); }
            case 7 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 96*2, rb.speed, planet3Src, 77); }
            case 8 -> { return new BackgroundElement(rb.x, rb.y, 96*2, 96*2, rb.speed, planet4Src, 77); }
            default -> { return null; }
        }
    }
}

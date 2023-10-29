package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.utilities.AbstractAnimatedObjectFactory;
import self.Gorgol.entity.utilities.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class AsteroidFactory extends AbstractAnimatedObjectFactory {
    private final Font font;


    public AsteroidFactory(float timePerSpawn, int maxEntityPerSpawn, Rectangle spawnArea) {
        super(timePerSpawn, spawnArea, 150, 75, maxEntityPerSpawn);

        try {
            this.src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/asteroids/asteroid.png")));
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/better-vcr4.0.ttf")));
            this.font = font.deriveFont(12f);
        }
        catch (IOException e) { throw new RuntimeException(); }
        catch (FontFormatException e) { throw new RuntimeException(e); }
    }

    public Asteroid[] create(Asteroid[] aliveAsteroids) {
        int n = (int) (1 + Math.random() * maxEntityPerSpawn);
        Asteroid[] entities = new Asteroid[n];

        for (int i = 0; i < n; i++) {
            Asteroid asteroid = createRandomInstance();
            while (!isAvailable(asteroid, entities) || !isAvailable(asteroid, aliveAsteroids)) {
                asteroid = createRandomInstance();
            }

            entities[i] = asteroid;
        }

        return entities;
    }

    private Asteroid createRandomInstance() {
        PhysicsBody randomBody = getRandomPhysicsBody();
        float size = randomBody.width;

        return new Asteroid(randomBody.x, randomBody.y, size, size, 50f, src, font);
    }

    private boolean isAvailable(Asteroid asteroid, Asteroid[] existing) {
        for (Asteroid ex : existing) {
            if (ex != null && ex.body.intersects(asteroid.body)) return false;
        }

        return true;
    }
}

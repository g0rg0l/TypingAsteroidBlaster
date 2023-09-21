package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.utilities.AbstractAnimatedObjectFactory;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.PhysicsBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class AsteroidFactory extends AbstractAnimatedObjectFactory {
    public AsteroidFactory(float timePerSpawn, Rectangle spawnArea) {
        super(timePerSpawn, spawnArea, 150, 75, 5);

        try {
            this.src = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/asteroid.png")));
        }
        catch (IOException e) { throw new RuntimeException(); }
    }

    @Override
    protected AnimatedObject createRandomInstance() {
        PhysicsBody randomBody = getRandomPhysicsBody();

        float size = randomBody.width;
        return new Asteroid(randomBody.x, randomBody.y, size, size, randomBody.speed, src);
    }
}
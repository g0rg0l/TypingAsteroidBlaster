package self.Gorgol.entity.utilities;


import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractAnimatedObjectFactory {
    protected BufferedImage src = null;
    protected float currentTime = 0;
    protected final float timePerSpawn;
    protected final int maxEntityPerSpawn;

    protected final Rectangle spawnArea;
    protected final float maxSize;
    protected final float minSize;

    public AbstractAnimatedObjectFactory(float timePerSpawn,
                                         Rectangle spawnArea, float maxSize, float minSize,
                                         int maxEntityPerSpawn) {
        this.timePerSpawn = timePerSpawn;
        this.spawnArea = spawnArea;
        this.maxSize = maxSize;
        this.minSize = minSize;
        this.maxEntityPerSpawn = maxEntityPerSpawn;
    }

    public final boolean isReadyToCreate(float dt) {

        if ((currentTime += dt) >= timePerSpawn) {
            currentTime = 0;
            return true;
        }

        return false;
    }

    public final AnimatedObject[] create() {
        int n = (int) (1 + Math.random() * maxEntityPerSpawn);
        AnimatedObject[] entities = new AnimatedObject[n];

        for (int i = 0; i < n; i++)
            entities[i] = createRandomInstance();

        return entities;
    }

    protected final PhysicsBody getRandomPhysicsBody() {
        float width = (float) (minSize + Math.random() * (maxSize - minSize));
        float height = (float) (minSize + Math.random() * (maxSize - minSize));
        float speed = (float) (45.f + Math.random() * 10.f);

        float x = (float) (spawnArea.x + Math.random() * (spawnArea.width - spawnArea.x) - width);
        float y = (float) (spawnArea.y + Math.random() * (spawnArea.height - spawnArea.y) - height);
        x = Math.max(x, spawnArea.x);
        y = Math.max(y, spawnArea.y);

        return new PhysicsBody(x, y, width, height, speed);
    }

    protected abstract AnimatedObject createRandomInstance();
}

package self.Gorgol.entity.utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedObject implements IRenderer, IUpdatable {

    private int currentAnimationStep;
    private final int totalAnimationSteps;
    private final float animationTimeStep;
    private float currentAnimationTime;
    private final int animationStepPixelSize;
    private final BufferedImage image;
    public final PhysicsBody body;

    public AnimatedObject(float x, float y, float width, float height, BufferedImage image,
                          int totalAnimationSteps, float animationTimeStep) {
        this.body = new PhysicsBody(x, y, width, height, 0.f);
        this.image = image;
        this.totalAnimationSteps = totalAnimationSteps;
        this.animationTimeStep = animationTimeStep;
        this.currentAnimationStep = 0;
        this.currentAnimationTime = 0.f;
        this.animationStepPixelSize = image.getWidth() / totalAnimationSteps;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image,
                (int) (body.x) , (int) (body.y),
                (int) (body.x + body.width), (int) (body.y + body.height),
                currentAnimationStep * animationStepPixelSize, 0,
                (currentAnimationStep + 1) * animationStepPixelSize, image.getHeight(),
                null
        );
    }

    @Override
    public void update(float dt) {
        if ((currentAnimationTime += dt) >= animationTimeStep) {
            currentAnimationStep = (++currentAnimationStep) % totalAnimationSteps;
            currentAnimationTime = 0;
        }
    }

    protected boolean isLastAnimationStep() {
        return totalAnimationSteps == currentAnimationStep + 1;
    }
}

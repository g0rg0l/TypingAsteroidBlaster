package self.Gorgol.entity.objects.background;

import self.Gorgol.entity.utilities.AnimatedObject;

import java.awt.image.BufferedImage;

public class BackgroundElement extends AnimatedObject {
    public BackgroundElement(float x, float y, float width, float height, float speed,
                             BufferedImage image, int totalAnimationSteps) {
        super(x, y, width, height, image, totalAnimationSteps, 0.07f);
        this.body.speed = speed;
    }

    @Override
    public void update(float dt) {
        super.update(dt);

        move(0, body.speed, dt);
    }

    private void move(float dx, float dy, float dt) {
        body.x += dx * dt;
        body.y += dy * dt;
    }
}

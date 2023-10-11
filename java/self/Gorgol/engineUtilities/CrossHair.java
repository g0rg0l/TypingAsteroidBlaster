package self.Gorgol.engineUtilities;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CrossHair extends AnimatedObject {
    private Asteroid calledTo;
    private float gap;

    public CrossHair(BufferedImage image) {
        super(0, 0, 36f, 36f, image, 13, 0.035f);
        this.calledTo = null;
        this.gap = 36;
    }

    @Override
    public void render(Graphics g) {
        if (calledTo != null) {
            super.render(g);
        }
    }

    @Override
    public void update(float dt) {
        if (calledTo != null) {
            if (!isLastAnimationStep()) {
                super.update(dt);
            }

            setCalledPosition();
        }
    }

    public void callToAsteroid(Asteroid asteroid) {
        resetAnimation();
        calledTo = asteroid;

        /* resizing by sizes of calling instance */
        float k = calledTo.hitBox.width / body.width;

        body.height = (body.height + gap) * k;
        body.width = (body.width + gap) * k;
    }

    public void unCall() {
        calledTo = null;
    }

    private void setCalledPosition() {
        Vector2f center = calledTo.body.getCenter();

        body.x = center.x - body.width / 2;
        body.y = center.y - body.height / 2;
    }

    private void resetAnimation() {
        currentAnimationStep = 0;
        currentAnimationTime = 0;
    }
}

package self.Gorgol.engineUtilities;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CrossHair extends AnimatedObject {
    private final BufferedImage incorrectCrossHairSrc;
    private final BufferedImage baseImage;
    private Asteroid calledTo;
    private boolean isIncorrectCondition;
    private final float gap;
    private final float incorrectConditionTime;
    private float currentTime;

    public CrossHair(BufferedImage image) {
        super(0, 0, 36f, 36f, image, 13, 0.035f);
        this.calledTo = null;
        this.gap = 30;
        this.incorrectConditionTime = 0.3f;
        this.isIncorrectCondition = false;
        this.currentTime = 0.f;
        this.baseImage = image;

        try {
            incorrectCrossHairSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/crosshair red.png")));
        } catch (IOException ex) { throw new RuntimeException(); }
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

            if (isIncorrectCondition) {
                currentTime += dt;
                shake();

                if (currentTime >= incorrectConditionTime) {
                    isIncorrectCondition = false;
                    image = baseImage;
                    simpleUnCall();
                }
            }
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

    public void incorrectUnCall() {
        currentTime = 0;
        isIncorrectCondition = true;
        image = incorrectCrossHairSrc;
    }

    public void simpleUnCall() {
        calledTo = null;
    }

    private void setCalledPosition() {
        Vector2f center = calledTo.body.getCenter();

        body.x = center.x - body.width / 2;
        body.y = center.y - body.height / 2;
    }

    private void shake() {
        body.x += (float) (Math.random() > 0.5 ? 1.5 : -1.5);
        body.y += (float) (Math.random() > 0.5 ? 1.5 : -1.5);
    }

    private void resetAnimation() {
        currentAnimationStep = 0;
        currentAnimationTime = 0;
        currentTime = 0;
        isIncorrectCondition = false;
        image = baseImage;
    }
}

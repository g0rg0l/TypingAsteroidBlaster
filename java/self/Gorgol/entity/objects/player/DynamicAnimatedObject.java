package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.utilities.AnimatedObject;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DynamicAnimatedObject extends AnimatedObject {
    private AffineTransform tx;
    protected BufferedImage croppedImage;

    public DynamicAnimatedObject(float x, float y, float width, float height,
                                 BufferedImage image,
                                 int totalAnimationSteps, float animationTimeStep) {

        super(x, y, width, height, image, totalAnimationSteps, animationTimeStep);
        this.tx = AffineTransform.getTranslateInstance( body.x, body.y);
    }

    public void setAffineTransform(AffineTransform tx) { this.tx = tx; }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(croppedImage, tx, null);
    }

    @Override
    public void update(float dt) {
        if ((currentAnimationTime += dt) >= animationTimeStep) {
            currentAnimationStep = (++currentAnimationStep) % totalAnimationSteps;
            currentAnimationTime = 0;

            croppedImage = image.getSubimage(
                    currentAnimationStep * animationStepPixelSize, 0,
                    animationStepPixelSize, image.getHeight()
            );
        }
    }
}

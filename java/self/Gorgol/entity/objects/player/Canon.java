package self.Gorgol.entity.objects.player;

import java.awt.image.BufferedImage;

public class Canon extends DynamicAnimatedObject {
    private boolean isShooting;

    public Canon(float x, float y, float width, float height, BufferedImage image) {
        super(x, y, width, height, image, 7, 0.05f);
        this.isShooting = false;
        this.croppedImage = image.getSubimage(
                currentAnimationStep * animationStepPixelSize, 0,
                animationStepPixelSize, image.getHeight()
        );
    }

    public void shoot() {
        isShooting = true;
    }

    @Override
    public void update(float dt) {
        if (isShooting) {
            super.update(dt);
            if (currentAnimationTime == 0 && currentAnimationStep == 0) isShooting = false;
        }
    }

    public boolean isShooting() { return isShooting; }
}

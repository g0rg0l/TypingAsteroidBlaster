package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.objects.bullets.Bullet;
import self.Gorgol.entity.objects.bullets.BulletsHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Canon extends DynamicAnimatedObject {
    private boolean isShooting;
    private final BufferedImage bulletImage;
    private BulletsHolder bulletsHolder;

    public Canon(float x, float y, float width, float height, BufferedImage image) {
        super(x, y, width, height, image, 7, 0.05f);
        this.isShooting = false;
        this.croppedImage = image.getSubimage(
                currentAnimationStep * animationStepPixelSize, 0,
                animationStepPixelSize, image.getHeight()
        );

        try {
            bulletImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/bullet.png")));
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    public void shoot(float angleDeg) {
        isShooting = true;
        bulletsHolder.add(new Bullet(body.x + body.width / 2, body.y + body.height / 2, angleDeg, bulletImage));
    }

    @Override
    public void update(float dt) {
        if (isShooting) {
            super.update(dt);
            if (currentAnimationTime == 0 && currentAnimationStep == 0) isShooting = false;
        }
    }

    public boolean isShooting() { return isShooting; }

    public void setBulletsHolder(BulletsHolder bulletsHolder) { this.bulletsHolder = bulletsHolder; }
}

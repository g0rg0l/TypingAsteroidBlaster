package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.bullets.Bullet;
import self.Gorgol.entity.objects.bullets.BulletsHolder;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.sound.SoundHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Canon extends DynamicAnimatedObject {
    private boolean isShooting;
    private Asteroid target;
    private final float firstShootTime;
    private float firstShootAngle;
    private final float secondShootTime;
    private float secondShootAngle;
    private float currentShootTime;
    private int bulletsCount;
    private final BufferedImage bulletImage;
    private BulletsHolder bulletsHolder;

    public Canon(float x, float y, float width, float height, BufferedImage image) {
        super(x, y, width, height, image, 7, 0.08f);
        this.isShooting = false;
        this.firstShootTime = 0.16f;
        this.secondShootTime = 0.24f;
        this.currentShootTime = 0;
        this.bulletsCount = 2;
        this.croppedImage = image.getSubimage(
                currentAnimationStep * animationStepPixelSize, 0,
                animationStepPixelSize, image.getHeight()
        );

        try {
            bulletImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/shooting/bullet.png")));
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    public void shoot(Asteroid asteroid) {
        isShooting = true;
        target = asteroid;

        HitBox firstBulletHitBox = new HitBox(body.x + 24, body.y + 30, 32, 32);
        HitBox secondBulletHitBox = new HitBox(body.x + 60, body.y + 30, 32, 32);
        firstShootAngle = (float) Math.toDegrees(firstBulletHitBox.getAngleTo(asteroid.body));
        secondShootAngle =(float) Math.toDegrees(secondBulletHitBox.getAngleTo(asteroid.body));

        currentShootTime = 0;
        bulletsCount = 0;

        SoundHolder.INSTANCE.play(SoundHolder.Type.ROCKET_LAUNCHING);
    }

    @Override
    public void update(float dt) {
        if (isShooting) {
            super.update(dt);
            currentShootTime += dt;

            if (currentShootTime >= firstShootTime && bulletsCount == 0) {
                bulletsHolder.add(new Bullet(body.x + 24, body.y + 30, firstShootAngle, target, bulletImage));

                bulletsCount++;
            }
            if (currentShootTime >= secondShootTime && bulletsCount == 1) {
                bulletsHolder.add(new Bullet(body.x + 60, body.y + 30, secondShootAngle, target, bulletImage));

                bulletsCount++;
            }

            if (currentAnimationTime == 0 && currentAnimationStep == 0) isShooting = false;
        }
    }

    public boolean isShooting() { return isShooting; }

    public void setBulletsHolder(BulletsHolder bulletsHolder) { this.bulletsHolder = bulletsHolder; }
}

package self.Gorgol.entity.objects.bullets;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.player.DynamicAnimatedObject;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.Vector2f;

import java.awt.image.BufferedImage;

public class Bullet extends DynamicAnimatedObject {
    private final float angle;
    public final Asteroid target;
    private boolean isExploded;
    public final HitBox hitBox;

    public Bullet(float x, float y, float angleDeg, Asteroid target, BufferedImage image) {
        super(x, y, 32, 32, image, 4, 0.05f);
        this.body.speed = 5f;
        this.angle = angleDeg;
        this.target = target;
        this.isExploded = false;
        this.hitBox = new HitBox(x + 11 ,y + 8, 10f, 12f);
    }

    public void move(Vector2f d) {
        body.x += d.x;
        body.y += d.y;

        hitBox.x += d.x;
        hitBox.y += d.y;

        updateTx();
    }

    public void explode() { isExploded = true; }
    public boolean isExploded() { return isExploded; }

    @Override
    public void update(float dt) {
        super.update(dt);

        move(new Vector2f(
                body.speed * (float) Math.cos(Math.toRadians(angle) - Math.PI/2),
                body.speed * (float) Math.sin(Math.toRadians(angle) - Math.PI/2)
        ));
    }

    private void updateTx() {
        tx.setToTranslation(body.x, body.y);

        double locationX = (double) body.width / 2;
        double locationY = (double) body.height / 2;
        tx.rotate(Math.toRadians(angle), locationX, locationY);
    }
}

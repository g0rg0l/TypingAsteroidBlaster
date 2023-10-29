package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.IUpdatable;

import java.awt.geom.AffineTransform;

public class RotationComponent implements IUpdatable {
    private final HitBox body;
    private AffineTransform tx;
    private final float speed = 16;
    private float target;
    private Asteroid targetAsteroid;
    private float current;
    private float rotationDelta;
    public boolean isRotating;
    public boolean isReturning;
    public boolean isWaitingForShoot;

    public RotationComponent(HitBox body) {
        this.body = body;
        this.tx = AffineTransform.getTranslateInstance(body.x, body.y);
        this.isRotating = false;
        this.isReturning = false;
        this.isWaitingForShoot = false;
    }

    public void rotateTo(Asteroid asteroid) {
        targetAsteroid = asteroid;
        target = (float) Math.toDegrees(body.getAngleTo(asteroid.body));
        current = target;
        rotationDelta = target / speed;
        isRotating = true;
    }

    public void returnToDefault() {
        isWaitingForShoot = false;
        isReturning = true;
    }

    public void move(float dx, float dy) {
        body.x += dx;
        body.y += dy;

        tx.translate(dx, dy);
    }

    public AffineTransform getTx() { return tx; }

    public Asteroid getTarget() {
        return targetAsteroid;
    }

    @Override
    public void update(float dt) {
        if (isRotating || isReturning) {
            current -= rotationDelta;

            /* rotating affine transform matrix */
            double locationX = (double) body.width / 2;
            double locationY = (double) body.height / 2;
            tx.rotate(Math.toRadians(rotationDelta), locationX, locationY);
            /* --------------- */

            if (isRotating) {
                if (Math.abs(current) <= 0.01) {
                    isRotating = false;
                    isWaitingForShoot = true;
                    current = -target;
                    rotationDelta = -rotationDelta;
                }
            }
            else if (isReturning) {
                if (Math.abs(current) <= 0.01) {
                    isReturning = false;
                }
            }
        }
    }
}

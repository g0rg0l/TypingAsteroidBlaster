package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.IUpdatable;

import java.awt.geom.AffineTransform;

public class RotationComponent implements IUpdatable {
    private final HitBox body;
    private final AffineTransform tx;
    private final float speed = 16;
    private float target;
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

    public void rotate(float angle) {
        target = (float) Math.toDegrees(angle);
        current = target;
        rotationDelta = target / speed;
        isRotating = true;
    }

    public void returnToDefault() {
        isWaitingForShoot = false;
        isReturning = true;
    }

    public AffineTransform getTx() { return tx; }

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

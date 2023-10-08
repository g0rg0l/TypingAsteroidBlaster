package self.Gorgol.entity.utilities;

import java.awt.*;

public class HitBox {
    public float x;
    public float y;
    public float width;
    public float height;

    public HitBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(HitBox hitBox) {
        return getRectangle().intersects(hitBox.getRectangle());
    }

    public Rectangle getRectangle() { return new Rectangle((int) x, (int) y, (int) width, (int) height); }

    public Vector2f getCenter() { return new Vector2f(x + width / 2, y + height / 2); }

    public float getAngleTo(HitBox another) {
        Vector2f thisCenter = this.getCenter();
        Vector2f anotherCenter = another.getCenter();

        float x1 = thisCenter.x;
        float y1 = thisCenter.y;
        float x2 = anotherCenter.x;
        float y2 = anotherCenter.y;

        double angle = Math.PI / 2 + Math.atan2(y2 - y1, x2 - x1);

        return (float) angle;
    }
}

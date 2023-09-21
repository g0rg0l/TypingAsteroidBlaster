package self.Gorgol.entity.utilities;

public class PhysicsBody extends HitBox {
    public float speed;

    public PhysicsBody(float x, float y, float width, float height, float speed) {
        super(x, y, width, height);
        this.speed = speed;
    }
}

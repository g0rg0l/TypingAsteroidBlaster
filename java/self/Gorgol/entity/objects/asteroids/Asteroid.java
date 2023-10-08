package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.objects.asteroids.words.Word;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Asteroid extends AnimatedObject {
    public AsteroidType type;
    public Word word = null;
    public boolean isSelected = false;
    private boolean farFromPlayer = true;
    private Vector2f target;

    public Asteroid(float x, float y, float width, float height, float speed,
                    BufferedImage image) {

        super(x, y, width, height, image, 8, 0.035f);
        this.body.speed = speed;
        this.type = AsteroidType.BASE;
    }

    @Override
    public void update(float dt) {
        if (word.isCompleted()) explode();

        if (type != AsteroidType.BASE) {
            super.update(dt);
            if (isLastAnimationStep()) type = AsteroidType.EXPLODED;
        }
        else {
            if (farFromPlayer) move(0, body.speed, dt);
            else moveToTarget(dt);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);


        int stringWidth = g.getFontMetrics().stringWidth(word.string);
        int stringHeight = g.getFontMetrics().getHeight();

        g.drawString(word.getAlivePart(), (int) (body.x + body.width / 2 - stringWidth / 2),
                (int) (body.y + body.height - stringHeight));

        if (isSelected) {
            g.setColor(Color.GRAY);
            g.drawRect((int) body.x, (int) body.y, (int) body.width, (int) body.height);
        }
    }

    public void explode() {
        type = AsteroidType.EXPLODING;
    }

    public void markCloseToPlayer(Vector2f playerCenterPosition) {
        farFromPlayer = false;
        target = playerCenterPosition;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public boolean isVisible() {
        return body.y >= 0;
    }

    private void move(float dx, float dy, float dt) {
        body.x += dx * dt;
        body.y += dy * dt;
    }

    private void moveToTarget(float dt) {
        float asterCenterX = body.x + body.width / 2;
        float asterCenterY = body.y + body.height / 2;

        float dX = target.x - asterCenterX;
        float dY = target.y - asterCenterY;

        float invLength = (float) Math.sqrt(dX * dX + dY * dY);
        float ndX = dX / invLength;
        float ndY = dY / invLength;

        body.x += body.speed * ndX * dt;
        body.y += body.speed * ndY * dt;
    }
}

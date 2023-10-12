package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.objects.asteroids.words.Word;
import self.Gorgol.entity.utilities.AnimatedObject;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Asteroid extends AnimatedObject {
    public AsteroidType type;
    public final HitBox hitBox;
    public Word word = null;
    private boolean farFromPlayer = true;
    private Vector2f target;
    public int bulletCollided = 0;

    private Font textFont;

    public Asteroid(float x, float y, float width, float height, float speed,
                    BufferedImage image) {

        super(x, y, width, height, image, 8, 0.035f);
        this.body.speed = speed;

        final float k = this.body.width / 96f;
        this.hitBox = new HitBox(
                this.body.x + 29f *  k, this.body.y + 32 * k,
                38 * k, 33 * k
        );
        this.type = AsteroidType.BASE;

        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Asteroid.class.getResourceAsStream("/fonts/better-vcr4.0.ttf")));
            textFont = textFont.deriveFont(12f);
        } catch (FontFormatException | IOException ex) { throw new RuntimeException(); }
    }

    @Override
    public void update(float dt) {
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

        g.setFont(textFont);

        FontMetrics m = g.getFontMetrics();
        int stringAliveWidth = m.stringWidth(word.getAlivePart());
        int stringDeadWidth = m.stringWidth(word.getDeadPart());
        int stringHeight = m.getHeight();
        int totalWidth = stringAliveWidth + stringDeadWidth;

        int posX = (int) (body.x + body.width / 2);
        int posY = (int) (body.y + body.height - stringHeight);

        g.setColor(Color.DARK_GRAY);
        g.drawString(word.getDeadPart(),
                posX - totalWidth / 2,
                posY
        );

        g.setColor(Color.WHITE);
        g.drawString(word.getAlivePart(),
                posX - totalWidth / 2 + stringDeadWidth,
                posY
        );
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
        return body.y + body.height >= 0;
    }

    private void move(float dx, float dy, float dt) {
        body.x += dx * dt;
        body.y += dy * dt;

        hitBox.x += dx * dt;
        hitBox.y += dy * dt;
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

        hitBox.x += body.speed * ndX * dt;
        hitBox.y += body.speed * ndY * dt;
    }
}

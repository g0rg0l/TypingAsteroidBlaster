package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.objects.bullets.Bullet;
import self.Gorgol.entity.utilities.HitBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AsteroidHolder {
    private final ArrayList<Asteroid> asteroids;
    private final float yDeath = 600;

    public AsteroidHolder() {
        this.asteroids = new ArrayList<>();
    }

    public void update(float dt, HitBox playerHitBox, Bullet[] bullets) {
        for (Iterator<Asteroid> iter = asteroids.iterator(); iter.hasNext();) {
            Asteroid object = iter.next();

            object.update(dt);

            if (object.type == AsteroidType.BASE) {
                if (object.body.intersects(playerHitBox)) {
                    System.exit(0);
                }

                for (Bullet bullet : bullets) {
                    if (object.word.isCompleted() && object.body.intersects(bullet.body)) {
                        object.explode();
                        bullet.explode();
                    }
                }

                if (object.body.y + object.body.height >= yDeath) {
                    object.markCloseToPlayer(playerHitBox.getCenter());
                }
            }
            else if (object.type == AsteroidType.EXPLODED) iter.remove();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).render(g);
        }
    }

    public void add(Asteroid asteroid) { asteroids.add(asteroid); }

    public Asteroid getByFirstChar(char ch) {
        for (Asteroid asteroid : asteroids)
            if (asteroid.isVisible() && asteroid.word.match(ch)) return asteroid;

        return null;
    }
}

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
                /* Collision between asteroid and player */
                if (object.hitBox.intersects(playerHitBox) && !object.word.isCompleted()) {
                    System.exit(0);
                }

                /* Collision between asteroid and bullet */
                for (Bullet bullet : bullets) {
                    if (bullet.target == object && bullet.body.intersects(object.hitBox)) {
                        bullet.explode();
                        object.bulletCollided++;
                        if (object.bulletCollided == 2) object.explode();
                    }
                }

                /* Detecting does asteroid close to player and is it moving to it */
                if (object.hitBox.y + object.hitBox.height >= yDeath) {
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

    public Asteroid getByChar(char ch) {
        ArrayList<Asteroid> a = getOnlyByChar(ch);

        if (a.isEmpty()) return null;
        else if (a.size() == 1) return a.get(0);
        else {
            float maxY = -1;
            Asteroid nearest = null;

            for (Asteroid asteroid : a) {
                if (asteroid.body.y + asteroid.body.height > maxY) {
                    nearest = asteroid;
                    maxY = asteroid.body.y + asteroid.body.height;
                }
            }

            return nearest;
        }
    }

    private ArrayList<Asteroid> getOnlyByChar(char ch) {
        ArrayList<Asteroid> a = new ArrayList<>();

        for (Asteroid asteroid : asteroids)
            if (asteroid.isVisible() && asteroid.word.match(ch)) a.add(asteroid);

        return a;
    }
}

package self.Gorgol.entity.objects.asteroids;

import self.Gorgol.entity.objects.asteroids.words.Word;
import self.Gorgol.entity.objects.bullets.Bullet;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.sound.SoundHolder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class AsteroidHolder {
    private final ArrayList<Asteroid> asteroids;
    private final float yDeath = 600;
    public boolean collidedWithPlayer = false;

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
                    collidedWithPlayer = true;
                }

                /* Collision between asteroid and bullet */
                for (Bullet bullet : bullets) {
                    if (bullet.target == object && bullet.hitBox.intersects(object.hitBox)) {
                        bullet.explode();
                        object.bulletCollided++;
                        if (object.bulletCollided == 2) {
                            object.explode();
                            SoundHolder.INSTANCE.play(SoundHolder.Type.ASTEROID_EXPLOSION);
                        }
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

    public Word[] getAllAliveWords() {
        Word[] words = new Word[asteroids.size()];

        for (int i = 0; i < asteroids.size(); i++)
            words[i] = asteroids.get(i).word;

        return words;
    }

    public Asteroid[] getAllAliveAsteroids() {
        Asteroid[] result = new Asteroid[asteroids.size()];

        for (int i = 0; i < asteroids.size(); i++)
            result[i] = asteroids.get(i);

        return result;
    }

    public boolean isFull() {
        return asteroids.size() >= 18;
    }

    private ArrayList<Asteroid> getOnlyByChar(char ch) {
        ArrayList<Asteroid> a = new ArrayList<>();

        for (Asteroid asteroid : asteroids)
            if (asteroid.isVisible() && asteroid.word.match(ch)) a.add(asteroid);

        return a;
    }
}

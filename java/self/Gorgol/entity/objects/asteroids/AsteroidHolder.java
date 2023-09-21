package self.Gorgol.entity.objects.asteroids;

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

    public void update(float dt, HitBox playerHitBox) {
        for (Iterator<Asteroid> iter = asteroids.iterator(); iter.hasNext();) {
            Asteroid object = iter.next();

            object.update(dt);

            if (object.type == AsteroidType.BASE) {
                if (object.body.intersects(playerHitBox)) {
                    object.explode();
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

    public Asteroid selectRandom() {
        for (int i = 0; i < asteroids.size(); i++) {
            if (asteroids.get(i).isSelected) asteroids.get(i).isSelected = false;
        }

        if (!asteroids.isEmpty()) {
            int n = (int) (Math.random() * asteroids.size());
            Asteroid randomAsteroid = asteroids.get(n);
            randomAsteroid.isSelected = true;

            return randomAsteroid;
        }
        else return null;
    }

    public void add(Asteroid asteroid) { asteroids.add(asteroid); }
}

package self.Gorgol.entity.objects.bullets;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BulletsHolder {
    private final ArrayList<Bullet> bullets;

    public BulletsHolder() {
        this.bullets = new ArrayList<>();
    }

    public void update(float dt) {
        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
            Bullet bullet = iterator.next();

            if (!bullet.isExploded()) {
                bullet.update(dt);
            }
            else iterator.remove();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }

    public void add(Bullet bullet) {
        bullets.add(bullet);
    }

    public Bullet[] getAllBullets() {
        Bullet[] b = new Bullet[bullets.size()];

        for (int i = 0; i < bullets.size(); i++)
            b[i] = bullets.get(i);

        return b;
    }
}

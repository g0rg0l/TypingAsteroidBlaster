package self.Gorgol.entity.objects;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;
import self.Gorgol.entity.objects.background.Background;
import self.Gorgol.entity.objects.background.BackgroundElement;
import self.Gorgol.entity.objects.background.BackgroundElementHolder;
import self.Gorgol.entity.objects.bullets.Bullet;
import self.Gorgol.entity.objects.bullets.BulletsHolder;
import self.Gorgol.entity.objects.player.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class ObjectController {
    private final AsteroidHolder asteroidHolder;
    private final BackgroundElementHolder backgroundElementHolder;
    private final BulletsHolder bulletsHolder;
    private final Player player;
    private final Background background;
    private final CrossHair crossHair;

    public ObjectController() {
        this.player = new Player(650 / 2.f - 48, 850 - 96.f, 96.f, 96.f);
        this.bulletsHolder = new BulletsHolder();
        this.player.getCanon().setBulletsHolder(this.bulletsHolder);

        this.background = new Background(0, 0, 650, 850, 35);
        this.asteroidHolder = new AsteroidHolder();
        this.backgroundElementHolder = new BackgroundElementHolder();

        try {
            BufferedImage chImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/crosshair.png")));
            this.crossHair = new CrossHair(chImage);
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    public void renderObjects(Graphics g) {
        background.render(g);
        backgroundElementHolder.render(g);
        player.render(g);
        asteroidHolder.render(g);
        bulletsHolder.render(g);
        crossHair.render(g);
    }

    public void updateObjects(float dt) {
        player.update(dt);
        bulletsHolder.update(dt);
        asteroidHolder.update(dt, player.hitBox, bulletsHolder.getAllBullets());
        backgroundElementHolder.update(dt);
        background.update(dt);
        crossHair.update(dt);
    }

    public void add(Object object)  {
        if (object instanceof Asteroid) { asteroidHolder.add((Asteroid) object); }
        if (object instanceof BackgroundElement) { backgroundElementHolder.add((BackgroundElement) object); }
        if (object instanceof Bullet) { bulletsHolder.add((Bullet) object); }
    }

    public void add(Object[] objects) { for (Object object : objects) add(object); }

    public Player getPlayer() { return player; }
    public AsteroidHolder getAsteroidHolder() { return asteroidHolder; }
    public CrossHair getCrossHair() { return crossHair; }
}

package self.Gorgol.entity.objects;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.asteroids.AsteroidHolder;
import self.Gorgol.entity.objects.background.Background;
import self.Gorgol.entity.objects.background.BackgroundElement;
import self.Gorgol.entity.objects.background.BackgroundElementHolder;

import java.awt.*;


public class ObjectController {
    private final AsteroidHolder asteroidHolder;
    private final BackgroundElementHolder backgroundElementHolder;
    private final Player player;
    private final Background background;

    public ObjectController() {
        this.player = new Player(650 / 2.f - 48, 850 - 96.f, 96.f, 96.f);
        this.background = new Background(0, 0, 650, 850, 35);
        this.asteroidHolder = new AsteroidHolder();
        this.backgroundElementHolder = new BackgroundElementHolder();
    }

    public void renderObjects(Graphics g) {
        background.render(g);
        backgroundElementHolder.render(g);
        player.render(g);
        asteroidHolder.render(g);
    }

    public void updateObjects(float dt) {
        asteroidHolder.update(dt, player.body);
        backgroundElementHolder.update(dt);
        background.update(dt);
    }

    public void add(Object object)  {
        if (object instanceof Asteroid) { asteroidHolder.add((Asteroid) object); }
        if (object instanceof BackgroundElement) { backgroundElementHolder.add((BackgroundElement) object); }
    }

    public void add(Object[] objects) { for (Object object : objects) add(object); }

    public Player getPlayer() { return player; }

    public AsteroidHolder getAsteroidHolder() { return asteroidHolder; }
}

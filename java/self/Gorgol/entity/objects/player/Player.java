package self.Gorgol.entity.objects.player;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.IRenderer;
import self.Gorgol.entity.utilities.IUpdatable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements IRenderer, IUpdatable {
    private Image shipImage;
    private Image shipEngine;
    private final DynamicAnimatedObject shipEngineFire;
    private final Canon canon;
    private final ArrayList<Asteroid> attackStack;

    public final HitBox body;
    public final HitBox hitBox;
    private final RotationComponent rotationComponent;


    public Player(float x, float y, float width, float height) {
        body = new HitBox(x ,y, width, height);
        hitBox = new HitBox(x + 18 ,y + 22, 60f, 52f);
        this.rotationComponent = new RotationComponent(body);
        this.attackStack = new ArrayList<>();

        try {
            shipImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ship.png")));
            shipImage = shipImage.getScaledInstance((int) body.width, (int) body.height, Image.SCALE_DEFAULT);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            shipEngine = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/engine.png")));
            shipEngine = shipEngine.getScaledInstance((int) body.width, (int) body.height, Image.SCALE_DEFAULT);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            BufferedImage shipEngineFireImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/engine effect idle1.png")));
            shipEngineFire = new DynamicAnimatedObject(x, y, width, height, shipEngineFireImage, 3, 0.035f);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            BufferedImage canonImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/canon.png")));
            canon = new Canon(x, y, width, height, canonImage);
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        shipEngineFire.render(g);
        g2d.drawImage(shipImage, rotationComponent.getTx(), null);
        g2d.drawImage(shipEngine, rotationComponent.getTx(), null);
        canon.render(g);

        g2d.setColor(Color.GRAY);
        g2d.drawRect((int) body.x, (int) body.y, (int) body.width, (int) body.height);

        g2d.setColor(Color.RED);
        g2d.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    public void attack(Asteroid asteroid) {
        attackStack.add(asteroid);
    }

    @Override
    public void update(float dt) {
        rotationComponent.update(dt);
        if (rotationComponent.isWaitingForShoot && !canon.isShooting()) {
            canon.shoot(rotationComponent.getTarget());
        }

        canon.update(dt);
        if (!canon.isShooting() && rotationComponent.isWaitingForShoot) rotationComponent.returnToDefault();

        /* processing new request to attack */
        if (!rotationComponent.isRotating && !rotationComponent.isReturning &&
            !canon.isShooting() && !attackStack.isEmpty()) {

            rotationComponent.rotateTo(attackStack.get(0));
            attackStack.remove(0);
        }

        shipEngineFire.update(dt);
        shipEngineFire.setAffineTransform(rotationComponent.getTx());
        canon.setAffineTransform(rotationComponent.getTx());
    }

    public Canon getCanon() { return canon; }
}

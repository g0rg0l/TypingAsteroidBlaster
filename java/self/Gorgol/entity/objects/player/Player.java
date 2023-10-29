package self.Gorgol.entity.objects.player;

import self.Gorgol.effects.EffectsFactory;
import self.Gorgol.effects.EffectsHolder;
import self.Gorgol.effects.EffectsType;
import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.IRenderer;
import self.Gorgol.entity.utilities.IUpdatable;
import self.Gorgol.sound.SoundHolder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Player implements IRenderer, IUpdatable {
    private Image shipImage;
    private Image shipEngine;
    private DynamicAnimatedObject shipEngineFire;
    private final Canon canon;
    private final RotationComponent rotationComponent;
    private final ArrayList<Asteroid> attackStack;

    public final HitBox body;
    public final HitBox hitBox;
    public boolean isDestroyed;

    public boolean isStaticFirst;
    public boolean isStaticSecond;
    private float distanceToStaticFirst;
    private float distanceToStaticSecond;


    public Player(float x, float y, float width, float height) {
        this.distanceToStaticFirst = 250;
        this.distanceToStaticSecond = 50;

        body = new HitBox(x, y + distanceToStaticFirst - distanceToStaticSecond, width, height);
        hitBox = new HitBox(x + 18 ,y + 22, 60f, 52f);
        this.rotationComponent = new RotationComponent(body);
        this.attackStack = new ArrayList<>();
        this.isDestroyed = false;
        this.isStaticFirst = false;
        this.isStaticSecond = true;

        try {
            shipImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/ship.png")));
            shipImage = shipImage.getScaledInstance((int) body.width, (int) body.height, Image.SCALE_DEFAULT);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            shipEngine = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/engine.png")));
            shipEngine = shipEngine.getScaledInstance((int) body.width, (int) body.height, Image.SCALE_DEFAULT);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            BufferedImage shipEngineFireImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/engine effect powered.png")));
            shipEngineFire = new DynamicAnimatedObject(x, y, width, height, shipEngineFireImage, 4, 0.035f);
        } catch (IOException ex) { throw new RuntimeException(); }
        try {
            BufferedImage canonImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/canon.png")));
            canon = new Canon(x, y, width, height, canonImage);
        } catch (IOException ex) { throw new RuntimeException(); }

        SoundHolder.INSTANCE.play(SoundHolder.Type.ENGINE, true);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (!isDestroyed) {
            shipEngineFire.render(g);
            canon.render(g);
            g2d.drawImage(shipImage, rotationComponent.getTx(), null);
            g2d.drawImage(shipEngine, rotationComponent.getTx(), null);
        }
    }

    public void attack(Asteroid asteroid) {
        attackStack.add(asteroid);
    }

    @Override
    public void update(float dt) {
        if (!isDestroyed) {
            if (!isStaticFirst || !isStaticSecond) {
                if (!isStaticFirst) {
                        float dy = -75 * dt;
                        rotationComponent.move(0, dy);
                    hitBox.y += dt;
                    distanceToStaticFirst += dy;

                    if (distanceToStaticFirst <= 0) {
                        isStaticFirst = true;
                        isStaticSecond = false;

                        try {
                            BufferedImage shipEngineFireImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/engine effect powered.png")));
                            shipEngineFire = new DynamicAnimatedObject(body.x, body.y, body.width, body.height, shipEngineFireImage, 4, 0.055f);
                        } catch (IOException ex) { throw new RuntimeException(); }
                    }
                }
                if (!isStaticSecond) {
                    float dy = 25 * dt;
                    rotationComponent.move(0, dy);
                    hitBox.y += dt;
                    distanceToStaticSecond -= dy;

                    if (distanceToStaticSecond <= 0) {
                        isStaticSecond = true;

                        try {
                            BufferedImage shipEngineFireImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/player and stuff/engine effect idle.png")));
                            shipEngineFire = new DynamicAnimatedObject(body.x, body.y, body.width, body.height, shipEngineFireImage, 3, 0.035f);
                        } catch (IOException ex) { throw new RuntimeException(); }
                    }
                }
            }

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
    }

    public void destroy() {
        isDestroyed = true;

        EffectsHolder.INSTANCE.add(EffectsFactory.INSTANCE.create(
                EffectsType.PLAYER_EXPLODE,
                body.x - body.width / 2, body.y - body.height / 2,
                body.width * 2, body.height * 2
        ));
    }

    public Canon getCanon() { return canon; }
}

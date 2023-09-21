package self.Gorgol.entity.objects;

import self.Gorgol.entity.utilities.HitBox;
import self.Gorgol.entity.utilities.IRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Objects;

public class Player implements IRenderer {
    private Image shipImage;
    private AffineTransform tx;
    public final HitBox body;

    public Player(float x, float y, float width, float height) {
        body = new HitBox(x ,y, width, height);
        this.tx = AffineTransform.getTranslateInstance( body.x, body.y);


        try {
            shipImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ship.png")));
            shipImage = shipImage.getScaledInstance((int) body.width, (int) body.height, Image.SCALE_DEFAULT);
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(shipImage, tx, null);

        g2d.setColor(Color.GRAY);
        g2d.drawRect((int) body.x, (int) body.y, (int) body.width, (int) body.height);
    }

    public void rotate(float angle) {
        double locationX = (double) body.width / 2;
        double locationY = (double) body.height / 2;

        tx = AffineTransform.getTranslateInstance( body.x, body.y);
        tx.rotate(angle, locationX, locationY);

    }
}

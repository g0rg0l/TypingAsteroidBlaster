package self.Gorgol.ui.utilities;

import self.Gorgol.entity.utilities.HitBox;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Tip {
    private final BufferedImage background;
    private final HitBox body;
    private boolean isVisible;
    private final Font font;
    private TipType type;

    public Tip(float x, float y, float width, float height, Font font) {

        try {
            background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/tip background.png")));
        }
        catch (IOException ex) { throw new RuntimeException(); }

        this.font = font;
        this.body = new HitBox(x, y, width, height);
        this.isVisible = false;
    }

    public void render(Graphics g) {
        if (isVisible) {
            g.drawImage(background,
                    (int) body.x, (int) body.y,
                    (int) (body.x + body.width), (int) (body.y + body.height),

                    0, 0,
                    background.getWidth(), background.getHeight(),
                    null);

            g.setFont(font);
            g.setColor(Color.BLACK);

            int x = (int) body.x + 5;
            int y = (int) body.y + 10 + g.getFontMetrics().getHeight();

            if (type == TipType.SOUND) {
                g.drawString("Регулирует", x, y);
                g.drawString("громкость", x, y + g.getFontMetrics().getHeight());
                g.drawString("музыки.", x, y + 2 * g.getFontMetrics().getHeight());
            }
            else if (type == TipType.DIFFICULTY) {
                g.drawString("Регулирует", x, y);
                g.drawString("сложность:", x, y + g.getFontMetrics().getHeight());
                g.drawString("скорость", x, y + 2 * g.getFontMetrics().getHeight());
                g.drawString("появления", x, y + 3 * g.getFontMetrics().getHeight());
                g.drawString("астероидов", x, y + 4 * g.getFontMetrics().getHeight());
                g.drawString("и их", x, y + 5 * g.getFontMetrics().getHeight());
                g.drawString("кол-во.", x, y + 6 * g.getFontMetrics().getHeight());
            }
            else if (type == TipType.LANGUAGE) {
                g.drawString("Включена", x, y);
                g.drawString("неверная:", x, y + g.getFontMetrics().getHeight());
                g.drawString("раскладка", x, y + 2 * g.getFontMetrics().getHeight());
                g.drawString("клавиатуры.", x, y + 3 * g.getFontMetrics().getHeight());
            }
        }
    }

    public void show(TipType type) {
        isVisible = true;
        this.type = type;
    }

    public void hide() {
        isVisible = false;
    }

    public boolean isVisible() { return isVisible; }
    public TipType getType() { return type; }
}

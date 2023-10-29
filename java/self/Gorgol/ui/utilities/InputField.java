package self.Gorgol.ui.utilities;

import self.Gorgol.entity.utilities.HitBox;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static self.Gorgol.ui.MenuPanelInputHandler.isLatinLetter;

public class InputField {
    private final HitBox body;
    private final BufferedImage src;
    private final Font font;
    private String label;

    public InputField(float x, float y, float width, float height,
                      BufferedImage src, Font font) {
        this.src = src;
        this.label = "";
        this.font = font;
        this.body = new HitBox(x, y, width, height);
    }

    public void render(Graphics g) {
        g.drawImage(src,
                (int) body.x, (int) body.y,
                (int) (body.x + body.width), (int) (body.y + body.height),

                0, 0,
                src.getWidth(), src.getHeight(),
                null
                );

        if (!label.isEmpty()) {
            String labelPartToRender = getPossibleToRenderSubString();

            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(labelPartToRender,
                    (int) (body.x + 20),
                    (int) (body.y + 2.75 * g.getFontMetrics().getHeight())
            );
        }
    }

    public void input(char ch) {
        if (Character.isDigit(ch) || isLatinLetter(ch) ||
        ch == '+' || ch == '_' || ch == '~' || ch == KeyEvent.VK_BACK_SPACE ||
        ch == '|' || ch == '{' || ch == ':' || ch == '"' || ch == '}' ||
        ch == '<' || ch == '>' || ch == '?' || ch == ' ') {
            putCorrectInput(ch);
        }
    }

    private void putCorrectInput(char ch) {
        if (ch == KeyEvent.VK_BACK_SPACE) {
            if (!label.isEmpty()) {
                label = label.substring(0, label.length() - 1);
            }
        }
        else {
            label += ch;
        }
    }

    private String getPossibleToRenderSubString() {
        int maxCharsInRow = 25;

        if (label.length() <= maxCharsInRow) return label;
        else return label.substring(label.length() - maxCharsInRow);
    }
}

package self.Gorgol.ui.utilities;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomPanel extends JPanel {
    private final BufferedImage src;
    private Color color;


    public CustomPanel(float x, float y, float width, float height, BufferedImage src) {
        super();
        this.src = src;
        this.color = null;

        setLayout(null);
        setOpaque(false);
        setBounds((int) x, (int) y, (int) width, (int) height);
        setSize(new Dimension((int) width, (int) height));
    }

    public CustomPanel(float x, float y, float width, float height) {
        this(x, y, width, height, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (src == null) {
            if (color != null) {
                g.setColor(color);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
        else {
            g.drawImage(src,
                    0, 0, getWidth(), getHeight(),
                    0, 0, src.getWidth(), src.getHeight(),
                    null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void setColor(Color color) { this.color = color; }
}

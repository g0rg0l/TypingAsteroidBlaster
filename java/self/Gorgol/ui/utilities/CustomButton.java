package self.Gorgol.ui.utilities;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class CustomButton extends JComponent implements MouseListener {
    private final BufferedImage src;
    private BufferedImage currentImage;
    private final ArrayList<ActionListener> actionListeners;
    private String actionCommand;
    private final String label;
    private final Font font;

    public CustomButton(float x, float y, float width, float height,
                        String label, Font font,
                        BufferedImage src,
                        ActionListener actionListener) {
        super();
        this.src = src;
        this.label = label;
        this.font = font;
        this.actionListeners = new ArrayList<>();
        this.actionListeners.add(actionListener);
        this.actionCommand = "";
        this.currentImage = src.getSubimage(0, 0, src.getWidth(), src.getHeight() / 3);

        setBounds((int) x, (int) y, (int) width, (int) height);
        enableInputMethods(true);
        setSize((int) width, (int) height);
        setFocusable(true);
        addMouseListener(this);
    }

    public CustomButton(float x, float y, float width, float height,
                        BufferedImage src, ActionListener actionListener) {
        this(x, y, width, height, "", null, src, actionListener);
    }

    public void setActionCommand(String command) { actionCommand = command; }

    public void addActionListener(ActionListener actionListener) { actionListeners.add(actionListener); }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(currentImage,
                0, 0, getWidth(), getHeight(),
                0, 0, currentImage.getWidth(), currentImage.getHeight(),
                null);

        if (!label.isEmpty()) {
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(label,
                    getWidth() / 2 - g.getFontMetrics().stringWidth(label) / 2,
                    (int) (getHeight() / 1.5)
            );
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!actionCommand.isEmpty()) {
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(
                        new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                actionCommand,
                                e.getWhen(), e.getModifiersEx())
                );
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        currentImage = src.getSubimage(0, src.getHeight() / 3, src.getWidth(), src.getHeight() / 3);

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        currentImage = src.getSubimage(0, 0, src.getWidth(), src.getHeight() / 3);

        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        currentImage = src.getSubimage(0, 2 * src.getHeight() / 3, src.getWidth(), src.getHeight() / 3);

        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        currentImage = src.getSubimage(0, 0, src.getWidth(), src.getHeight() / 3);

        repaint();
    }
}

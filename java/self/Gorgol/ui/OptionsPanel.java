package self.Gorgol.ui;

import self.Gorgol.entity.utilities.Vector2f;
import self.Gorgol.sound.SoundHolder;
import self.Gorgol.ui.utilities.Bar;
import self.Gorgol.ui.utilities.CustomPanel;
import self.Gorgol.ui.utilities.Tip;
import self.Gorgol.ui.utilities.TipType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OptionsPanel extends CustomPanel implements ActionListener {
    private BufferedImage musicIcon;
    private BufferedImage difficultyIcon;
    private BufferedImage spawnTypeMark;
    private BufferedImage tipBackgroundSrc;
    private Vector2f spawnTypeMarkPosition;
    private Bar musicBar;
    private Bar difficultyBar;
    private Tip tip;

    public OptionsPanel(BufferedImage src, Font font) {
        super(252, 245, 356, 240, src);
        this.spawnTypeMarkPosition = new Vector2f(118, 148);

        createGUI(font);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(musicIcon, 30, 45, null);
        g.drawImage(difficultyIcon, 28, 85, null);
        g.drawImage(spawnTypeMark, (int) spawnTypeMarkPosition.x, (int) spawnTypeMarkPosition.y, null);

        musicBar.render(g);
        difficultyBar.render(g);
        tip.render(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "downgrade music command" -> musicBar.downGrade();
            case "upgrade music command" -> musicBar.upgrade();
            case "downgrade difficulty command" -> difficultyBar.downGrade();
            case "upgrade difficulty command" -> difficultyBar.upgrade();
            case "mark to solo command" -> spawnTypeMarkPosition = new Vector2f(54, 148);
            case "mark to bunch command" -> spawnTypeMarkPosition = new Vector2f(118, 148);
            case "show sound tip command" -> {
                SoundHolder.INSTANCE.play(SoundHolder.Type.SIMPLE_BUTTON_CLICK);
                if (!tip.isVisible() || tip.getType() != TipType.SOUND) tip.show(TipType.SOUND);
                else tip.hide();
            }
            case "show difficulty tip command" -> {
                SoundHolder.INSTANCE.play(SoundHolder.Type.SIMPLE_BUTTON_CLICK);
                if (!tip.isVisible() || tip.getType() != TipType.DIFFICULTY) tip.show(TipType.DIFFICULTY);
                else tip.hide();
            }
        }
    }

    private void createGUI(Font font) {
        setVisible(false);

        try {
            musicIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/music icon.png")));
            difficultyIcon = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/difficulty icon.png")));
            spawnTypeMark = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/mark to spawn type.png")));
            tipBackgroundSrc = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/ui/tip background.png")));
        }
        catch (IOException ex) { throw new RuntimeException(); }

        musicBar = new Bar(48, 38, 88, 24, 0.7f);
        difficultyBar = new Bar(48, 79, 88, 24, 0.5f);
        tip = new Tip(243, 37, 85, 150, font);
    }
}

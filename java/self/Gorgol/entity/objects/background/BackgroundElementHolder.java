package self.Gorgol.entity.objects.background;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class BackgroundElementHolder {
    private final ArrayList<BackgroundElement> backgroundElements;

    public BackgroundElementHolder() {
        this.backgroundElements = new ArrayList<>();
    }

    public void update(float dt) {
        for (Iterator<BackgroundElement> iter = backgroundElements.iterator(); iter.hasNext();) {
            BackgroundElement object = iter.next();

            object.update(dt);

            if (object.body.y >= 850) iter.remove();

        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        for (int i = 0; i < backgroundElements.size(); i++) {
            backgroundElements.get(i).render(g2d);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void add(BackgroundElement element) { backgroundElements.add(element); }
}

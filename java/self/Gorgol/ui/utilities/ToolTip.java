package self.Gorgol.ui.utilities;

import java.awt.*;

public class ToolTip extends Tip{
    private final float visibleTime;
    private float currentTime;

    public ToolTip(float x, float y, float width, float height, Font font, float visibleTime) {
        super(x, y, width, height, font);
        this.visibleTime = visibleTime;
        this.currentTime = 0;
    }

    public void update(float dt) {
        if (isVisible()) {
            currentTime += dt;

            if (currentTime >= visibleTime) {
                currentTime = 0;
                hide();
            }
        }
    }
}

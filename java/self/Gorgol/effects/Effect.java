package self.Gorgol.effects;

import self.Gorgol.entity.utilities.AnimatedObject;

import java.awt.image.BufferedImage;

public class Effect extends AnimatedObject {
    private final EffectsType type;

    public Effect(float x, float y,
                  float width, float height,
                  BufferedImage image, EffectsType type,
                  int totalAnimationSteps, float animationTimeStep) {
        super(x, y, width, height, image, totalAnimationSteps, animationTimeStep);
        this.type = type;
    }

    public boolean isDone() {
        return isLastAnimationStep();
    }
}

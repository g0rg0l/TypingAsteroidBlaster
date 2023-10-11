package self.Gorgol.effects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EffectsHolder {
    public static EffectsHolder INSTANCE = new EffectsHolder();
    private final ArrayList<Effect> aliveEffects;

    private EffectsHolder() {
        this.aliveEffects = new ArrayList<>();
    }

    public void add(Effect effect) {
        aliveEffects.add(effect);
    }

    public void update(float dt) {
        for (Iterator<Effect> iterator = aliveEffects.iterator(); iterator.hasNext();) {
            Effect effect = iterator.next();

            effect.update(dt);
            if (effect.isDone()) iterator.remove();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < aliveEffects.size(); i++) {
            aliveEffects.get(i).render(g);
        }
    }
}

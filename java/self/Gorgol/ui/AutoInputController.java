package self.Gorgol.ui;

import java.awt.event.KeyEvent;

public class AutoInputController {

    private int index = 0;
    private float currentTime = 0f;
    private boolean isReadyToInput = false;
    private final float timeoutDur = 10f;
    private boolean isTimeOut = false;

    public void update(float dt) {
        currentTime += dt;

        float timeOfOneChar = 0.1f;
        if (currentTime >= timeOfOneChar) isReadyToInput = true;
    }

    public char get() {
        String label = "TYPING ASTEROID BLASTERRRR";
        label = label + Character.toString(KeyEvent.VK_BACK_SPACE).repeat(label.length());
        char ch = Character.MIN_VALUE;

        if (isReadyToInput) {
            if (isTimeOut) {
                if (currentTime >= timeoutDur) {
                    isTimeOut = false;
                    currentTime = 0;
                }
            }
            else {
                currentTime = 0;
                isReadyToInput = false;
                ch = label.charAt(index);
                index = ++index % label.length();

                if (index == 0) {
                    isTimeOut = true;
                }
            }
        }

        return ch;
    }
}

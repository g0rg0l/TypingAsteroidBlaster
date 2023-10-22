package self.Gorgol.ui.keyboard;

import self.Gorgol.ui.utilities.CustomPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Keyboard extends CustomPanel {
    private final Key[] keys;
    private int index;
    private final Map<String, BufferedImage> keySrcMap;
    private final KeyboardInputHandler inputHandler;

    public Keyboard(float x, float y, float width, float height) {
        super(x, y, width, height);

        this.inputHandler = new KeyboardInputHandler();
        this.keys = new Key[60];
        this.index = 0;
        this.keySrcMap = new HashMap<>();

        initKeysSrc();
        initKeyboard();
    }

    public void press(char input) {
        String keyCode = inputHandler.processInput(input);

        if (!keyCode.isEmpty()) {
            for (Key key : keys) {
                if (key.getKeyCode().equals(keyCode)) {
                    key.press();
                    break;
                }
            }
        }
    }

    public void release(char input) {
        String keyCode = inputHandler.processInput(input);

        if (!keyCode.isEmpty()) {
            for (Key key : keys) {
                if (key.getKeyCode().equals(keyCode)) {
                    key.release();
                    break;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < keys.length; i++) {
            keys[i].render(g);
        }
    }

    private void initKeysSrc() {
        final String[] keysString = {
                "TILDE", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "PLUS", "UNDERSCORE", "BACKSPACE",
                "TAB", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "PIPE", "OPENCURLY", "ENTER",
                "CAPS", "A", "S", "D", "F", "G", "H", "J", "K", "L", "COLON", "QUOTE", "CLOSECURLY",
                "SHIFT", "Z", "X", "C", "V", "B", "N", "M", "LESSTHAN", "GREATERTHAN", "QUESTIONMARK", "SHIFTBIGGER",
                "CTRLL", "WINDOWSL", "ALT", "SPACE", "ALTGR", "WINDOWSR", "CTRLR"
        };

        try {
            for (String s : keysString) {
                keySrcMap.put(s, ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/keys/" + s + ".png"))));
            }
            keySrcMap.put("EMPTY", ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/keys/EMPTY.png"))));
        } catch (IOException ex) { throw new RuntimeException(); }
    }

    private void initKeyboard() {
        initKey(new Key(0, 0, 19, 21, keySrcMap.get("TILDE"), "TILDE"));
        initKey(new Key(19, 0, 19, 21, keySrcMap.get("1"), "1"));
        initKey(new Key(38, 0, 19, 21, keySrcMap.get("2"), "2"));
        initKey(new Key(57, 0, 19, 21, keySrcMap.get("3"),"3"));
        initKey(new Key(76, 0, 19, 21, keySrcMap.get("4"),"4"));
        initKey(new Key(95, 0, 19, 21, keySrcMap.get("5"),"5"));
        initKey(new Key(114, 0, 19, 21, keySrcMap.get("6"),"6"));
        initKey(new Key(133, 0, 19, 21, keySrcMap.get("7"),"7"));
        initKey(new Key(152, 0, 19, 21, keySrcMap.get("8"),"8"));
        initKey(new Key(171, 0, 19, 21, keySrcMap.get("9"),"9"));
        initKey(new Key(190, 0, 19, 21, keySrcMap.get("0"),"0"));
        initKey(new Key(209, 0, 19, 21, keySrcMap.get("PLUS"),"PLUS"));
        initKey(new Key(228, 0, 19, 21, keySrcMap.get("UNDERSCORE"),"UNDERSCORE"));
        initKey(new Key(247, 0, 53, 21, keySrcMap.get("BACKSPACE"),"BACKSPACE"));

        initKey(new Key(0, 15, 33, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(33, 15, 19, 21, keySrcMap.get("Q"),"Q"));
        initKey(new Key(52, 15, 19, 21, keySrcMap.get("W"),"W"));
        initKey(new Key(71, 15, 19, 21, keySrcMap.get("E"),"E"));
        initKey(new Key(90, 15, 19, 21, keySrcMap.get("R"),"R"));
        initKey(new Key(109, 15, 19, 21, keySrcMap.get("T"),"T"));
        initKey(new Key(128, 15, 19, 21, keySrcMap.get("Y"),"Y"));
        initKey(new Key(147, 15, 19, 21, keySrcMap.get("U"),"U"));
        initKey(new Key(166, 15, 19, 21, keySrcMap.get("I"),"I"));
        initKey(new Key(185, 15, 19, 21, keySrcMap.get("O"),"O"));
        initKey(new Key(204, 15, 19, 21, keySrcMap.get("P"),"P"));
        initKey(new Key(223, 15, 19, 21, keySrcMap.get("PIPE"),"PIPE"));
        initKey(new Key(242, 15, 19, 21, keySrcMap.get("OPENCURLY"),"OPENCURLY"));
        initKey(new Key(261, 15, 39, 36, keySrcMap.get("ENTER"),"ENTER"));

        initKey(new Key(0, 30, 41, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(41, 30, 19, 21, keySrcMap.get("A"),"A"));
        initKey(new Key(60, 30, 19, 21, keySrcMap.get("S"),"S"));
        initKey(new Key(79, 30, 19, 21, keySrcMap.get("D"),"D"));
        initKey(new Key(98, 30, 19, 21, keySrcMap.get("F"),"F"));
        initKey(new Key(117, 30, 19, 21, keySrcMap.get("G"),"G"));
        initKey(new Key(136, 30, 19, 21, keySrcMap.get("H"),"H"));
        initKey(new Key(155, 30, 19, 21, keySrcMap.get("J"),"J"));
        initKey(new Key(174, 30, 19, 21, keySrcMap.get("K"),"K"));
        initKey(new Key(193, 30, 19, 21, keySrcMap.get("L"),"L"));
        initKey(new Key(212, 30, 19, 21, keySrcMap.get("COLON"),"COLON"));
        initKey(new Key(231, 30, 19, 21, keySrcMap.get("QUOTE"),"QUOTE"));
        initKey(new Key(250, 30, 19, 21, keySrcMap.get("CLOSECURLY"),"CLOSECURLY"));

        initKey(new Key(0, 45, 49, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(68, 45, 19, 21, keySrcMap.get("X"),"X"));
        initKey(new Key(49, 45, 19, 21, keySrcMap.get("Z"),"Z"));
        initKey(new Key(87, 45, 19, 21, keySrcMap.get("C"),"C"));
        initKey(new Key(106, 45, 19, 21, keySrcMap.get("V"),"V"));
        initKey(new Key(125, 45, 19, 21, keySrcMap.get("B"),"B"));
        initKey(new Key(144, 45, 19, 21, keySrcMap.get("N"),"N"));
        initKey(new Key(163, 45, 19, 21, keySrcMap.get("M"),"M"));
        initKey(new Key(182, 45, 19, 21, keySrcMap.get("LESSTHAN"),"LESSTHAN"));
        initKey(new Key(201, 45, 19, 21, keySrcMap.get("GREATERTHAN"),"GREATERTHAN"));
        initKey( new Key(220, 45, 19, 21, keySrcMap.get("QUESTIONMARK"),"QUESTIONMARK"));
        initKey(new Key(239, 45, 61, 21, keySrcMap.get("EMPTY"),"EMPTY"));

        initKey(new Key(0, 60, 41, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(41, 60, 19, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(60, 60, 33, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(93, 60, 98, 21, keySrcMap.get("SPACE"),"SPACE"));
        initKey(new Key(191, 60, 49, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(240, 60, 19, 21, keySrcMap.get("EMPTY"),"EMPTY"));
        initKey(new Key(259, 60, 41, 21, keySrcMap.get("EMPTY"),"EMPTY"));
    }

    private void initKey(Key key) {
        keys[index++] = key;
    }
}

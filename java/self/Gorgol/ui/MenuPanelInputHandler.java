package self.Gorgol.ui;

import java.awt.event.KeyEvent;

public class MenuPanelInputHandler {
    public static String processInput(char ch) {
        String code = "";

        if (Character.isDigit(ch)) {
            code = Character.toString(ch);
        }
        else if (Character.isAlphabetic(ch) && isLatinLetter(ch)) {
            code = Character.toString(Character.toUpperCase(ch));
        }
        else if (ch == '+') code = "PLUS";
        else if (ch == '_') code = "UNDERSCORE";
        else if (ch == '~') code = "TILDE";
        else if (ch == KeyEvent.VK_BACK_SPACE) code = "BACKSPACE";
        else if (ch == KeyEvent.VK_ENTER) code = "ENTER";
        else if (ch == '|') code = "PIPE";
        else if (ch == '{') code = "OPENCURLY";
        else if (ch == ':') code = "COLON";
        else if (ch == '"') code = "QUOTE";
        else if (ch == '}') code = "CLOSECURLY";
        else if (ch == '<') code = "LESSTHAN";
        else if (ch == '>') code = "GREATERTHAN";
        else if (ch == '?') code = "QUESTIONMARK";
        else if (ch == ' ') code = "SPACE";

        return code;
    }

    public static boolean isLatinLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
}

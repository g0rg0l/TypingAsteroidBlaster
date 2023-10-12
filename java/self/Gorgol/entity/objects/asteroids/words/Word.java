package self.Gorgol.entity.objects.asteroids.words;

public class Word {
    public String string;
    public int typedChars = 0;

    public Word(String string) {
        this.string = string;
    }

    public String getAlivePart() {
        return string.substring(typedChars);
    }
    public String getDeadPart() {
        return string.substring(0, typedChars);
    }

    public boolean match(char ch) {
        return !isCompleted() && string.substring(typedChars).charAt(0) == ch;
    }

    public void typeNext() {
        typedChars++;
    }

    public boolean isCompleted() {
        return typedChars == string.length();
    }
}

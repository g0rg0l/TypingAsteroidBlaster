package self.Gorgol.entity.objects.asteroids.words;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.utilities.AnimatedObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class WordGenerator {
    private final ArrayList<String> words;

    public WordGenerator() {

        try {
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(getClass().getResource("/language/russian.txt")).openStream()
                    ));

            this.words = new ArrayList<>();

            String line;
            while ((line = bf.readLine()) != null) { words.add(line); }
            bf.close();

        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public Asteroid[] setWords(Asteroid[] asteroids) {
        Word[] exWords = new Word[asteroids.length];

        for (int i = 0; i < exWords.length; i++) {
            Word word = create();
            while (!isAvailable(word, exWords)) word = create();
            exWords[i] = word;
            asteroids[i].setWord(word);
        }

        return asteroids;
    }

    public Word create() {
        return new Word(getRandomStringWord());
    }

    private String getRandomStringWord() {
        return words.get((int) (Math.random() * words.size()));
    }

    private boolean isAvailable(Word word, Word[] existing) {
        for (Word ex : existing)
            if (ex != null && ex.string.substring(0, 2).equals(word.string.substring(0, 2))) return false;

        return true;
    }
}

package self.Gorgol.entity.objects.asteroids.words;

import self.Gorgol.entity.objects.asteroids.Asteroid;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class WordGenerator {
    private final ArrayList<String> words;

    public WordGenerator() {

        try {
            BufferedReader bf = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(getClass().getResource("/language/cool.txt")).openStream()
            ));

            this.words = new ArrayList<>();

            String line;
            while ((line = bf.readLine()) != null)
                words.add(line);

            bf.close();

        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public Asteroid[] setWords(Asteroid[] asteroids, Word[] aliveWords) {
        Word[] bunchWords = new Word[asteroids.length];

        for (int i = 0; i < bunchWords.length; i++) {
            /* creating new available word */
            Word word = create();
            while(isNotAvailable(word, bunchWords) || isNotAvailable(word, aliveWords))
                word = create();

            /* setting up */
            bunchWords[i] = word;
            asteroids[i].setWord(word);
        }

        return asteroids;
    }

    private Word create() {
        return new Word(getRandomStringWord());
    }

    private String getRandomStringWord() {
        return words.get((int) (Math.random() * words.size()));
    }

    private boolean isNotAvailable(Word word, Word[] bunchWords) {
        for (Word ex : bunchWords)
            if (ex != null) {
                String e = ex.string;
                String w = word.string;

                if (e.charAt(0) == w.charAt(0)) return true;                  // The same first character
                if (e.substring(0, 2).equals(w.substring(0, 2))) return true; // The same start of length 2
                if (e.equals(w)) return true;                                 // The same words
            }

        return false;
    }
}

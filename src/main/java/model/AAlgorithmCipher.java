package model;

import java.util.HashMap;
import java.util.Map;

public abstract class AAlgorithmCipher extends AAlgorithm {
    protected String alphabet = alphabetsMapping.get(Alphabet.ENGLISH);
    protected static Map<Alphabet, String> alphabetsMapping = new HashMap<>();

    static {
        alphabetsMapping.put(Alphabet.ENGLISH, Alphabet.ENGLISH.getDescription());
        alphabetsMapping.put(Alphabet.VIETNAM, Alphabet.VIETNAM.getDescription());
    }

    public void setAlphabet(Alphabet afu) {
        this.alphabet = alphabetsMapping.get(afu);
    }
}

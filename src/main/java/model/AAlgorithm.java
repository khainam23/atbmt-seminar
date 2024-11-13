package model;

import java.util.HashMap;
import java.util.Map;

public abstract class AAlgorithm {
    public abstract String generateKey();

    public abstract void loadKey(String key);
    public abstract String getKey();

    public abstract String encrypt(String msg);
    public abstract String decrypt(String msg);

    public String convertMsg(String msg) {
        return msg.toLowerCase();
    }

    protected String alphabet = alphabetsMapping.get(Alphabet.ENGLISH);
    protected static Map<Alphabet, String> alphabetsMapping = new HashMap<>();

    static {
        alphabetsMapping.put(Alphabet.ENGLISH, Alphabet.ENGLISH.getDescription());
        alphabetsMapping.put(Alphabet.VIETNAM, Alphabet.VIETNAM.getDescription());
    }

    public void setAlphabet(Alphabet afu) {
        this.alphabet = alphabetsMapping.get(afu);
    }

    public void setAlphabet(String nameAlphabet) {
        for (Map.Entry<Alphabet,String> entry : alphabetsMapping.entrySet()) {
            Alphabet alphabetName = entry.getKey();
            if(alphabetName.name().equalsIgnoreCase(nameAlphabet)) {
                alphabet = alphabetsMapping.get(alphabetName);
                return;
            }
        }
    }

    public String getNameAlphabet() {
        for (Map.Entry<Alphabet,String> entry : alphabetsMapping.entrySet()) {
            Alphabet alphabetName = entry.getKey();
            String des = entry.getValue();
            if(des.equalsIgnoreCase(alphabet)) {
                return alphabetName.name().toUpperCase();
            }
        }
        return null;
    }

    // Tìm nghịch đảo của a trong trường Zm
    public int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
}

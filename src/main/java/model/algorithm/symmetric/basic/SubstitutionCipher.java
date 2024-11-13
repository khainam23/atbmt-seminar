package model.algorithm.symmetric.basic;

import model.AAlgorithm;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Note: Nó tạo ra một bảng chữ cái ngẫu nhiên không trùng lặp.
 * Sau đó ánh xạ plaintext theo vị trí tương ứng trong bảng chữ cái.
 */
public class SubstitutionCipher extends AAlgorithm {
    private String hashAlphabet;

    public SubstitutionCipher() {
        generateKey();
    }

    @Override
    public String generateKey() {
        StringBuilder builder = new StringBuilder();
        Random rd = new Random();
        Set<Integer> uniqueInd = new HashSet<>();
        int alphabetSize = alphabet.length();
        while(uniqueInd.size() != alphabetSize) {
            int ind = rd.nextInt(alphabetSize);
            if(!uniqueInd.contains(ind)) {
                uniqueInd.add(ind);
                builder.append(alphabet.charAt(ind));
            }
        }
        this.hashAlphabet = builder.toString();
        return hashAlphabet;
    }

    @Override
    public void loadKey(String key) {
        if (key.length() != alphabet.length()) throw new RuntimeException("Key of user not same size with alphabet.");
        else this.hashAlphabet = key.toLowerCase();
    }

    @Override
    public String getKey() {
        return hashAlphabet;
    }

    @Override
    public String encrypt(String msg) {
        return getString(msg, alphabet, hashAlphabet);
    }

    @Override
    public String decrypt(String msg) {
        return getString(msg, hashAlphabet, alphabet);
    }

    private String getString(String msg, String hashAlphabet, String alphabet) {
        msg = convertMsg(msg);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            int ind = hashAlphabet.indexOf(c);
            if(ind != -1) {
                builder.append(alphabet.charAt(ind));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}

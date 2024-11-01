package model;

import java.util.Random;

public class ShiftCipher extends AAlgorithmCipher {
    private int shift = 3;

    @Override
    public String generateKey() {
        this.shift = new Random().nextInt() + 1;
        return shift + "";
    }

    @Override
    public void loadKey(String key) {
        try {
            int sfu = Integer.parseInt(key); // Shift for user
            if (sfu == 0) throw new RuntimeException("It's nothing.");
            else if (sfu < 0) throw new RuntimeException("Key for shift cipher not negative number.");
            else this.shift = sfu;
        } catch (NumberFormatException exception) {
            throw new RuntimeException("Key is not a number");
        }
    }

    @Override
    public String encrypt(String msg) {
        msg = convertMsg(msg);
        StringBuilder builder = new StringBuilder();
        int alphabetSize = alphabet.length();
        msg = msg.toLowerCase();
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            int ind = alphabet.indexOf(c);

            if(ind != -1) {
                int newInd = (ind + shift) % alphabetSize;
                builder.append(alphabet.charAt(newInd));
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    @Override
    public String decrypt(String msg) {
        msg = convertMsg(msg);
        StringBuilder builder = new StringBuilder();
        int alphabetSize = alphabet.length();
        msg = msg.toLowerCase();

        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            int index = alphabet.indexOf(c);

            if (index != -1) {
                int newIndex = (index - shift + alphabetSize) % alphabetSize;
                builder.append(alphabet.charAt(newIndex));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

}

package model.algorithm.symmetric.basic;

import model.AAlgorithm;

import java.util.Random;

/**
 * Note: Tạo ra một từ khóa ngẫu nhiên theo bảng chữ cái.
 * Lấy vị trí chữ cái của keyword theo thứ tự từ trái qua phải sau đó lấy vị trí trong alphabet,
 * tổng 2 vị trí và giới hạn trong trường chữ cái, sau đó lấy chữ cái tại vị mới đó.
 * Lặp lại cho tới khi hết chuỗi.
 */
public class Vigenere extends AAlgorithm {
    private String keyword;

    public Vigenere() {
        generateKey();
    }

    @Override
    public String generateKey() {
        Random rd = new Random();
        int m = rd.nextInt(alphabet.length()) + 1;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < m; ++i) {
            key.append(alphabet.charAt(rd.nextInt(alphabet.length())));
        }
        keyword = key.toString();
        return keyword;
    }

    @Override
    public void loadKey(String key) {
        if (key.length() > alphabet.length()) throw new RuntimeException("Key so long.");
        keyword = key;
    }

    @Override
    public String getKey() {
        return keyword;
    }

    @Override
    public String encrypt(String msg) {
        char[] cs = msg.toCharArray();
        StringBuilder encryptContent = new StringBuilder();
        for (int i = 0; i < cs.length ; i++) {
            char c = cs[i];
            int indC = alphabet.indexOf(c);
            if (indC != -1) {
                encryptContent.append(alphabet.charAt(
                        findIndNew(
                                indC,
                                // Lấy theo thứ tự từ trái qua phải, sau đó lấy vị trí trong alphabet
                                alphabet.indexOf(keyword.charAt(i % keyword.length())))
                ));
            } else {
                encryptContent.append(c);
            }
        }
        return encryptContent.toString();
    }

    public int findIndNew(int indC, int indK) {
        return (indC + indK) % alphabet.length();
    }

    @Override
    public String decrypt(String msg) {
        char[] cs = msg.toCharArray();
        StringBuilder decryptContent = new StringBuilder();
        for (int i = 0; i < cs.length; ++i) {
            char c = cs[i];
            int indC = alphabet.indexOf(c);
            if (indC != -1) {
                decryptContent.append(alphabet.charAt(
                        findIndOld(
                                indC,
                                alphabet.indexOf(keyword.charAt(i % keyword.length())))
                ));
            } else {
                decryptContent.append(c);
            }
        }
        return decryptContent.toString();
    }

    public int findIndOld(int indC, int indK) {
        return (indC - indK + alphabet.length()) % alphabet.length();
    }
}

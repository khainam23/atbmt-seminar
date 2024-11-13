package model.algorithm.symmetric.basic;

import model.AAlgorithm;

import java.util.Random;

/**
 * Note: Tìm vị trí của chữ cái trong bảng chữ cái sau đó đưa qua
 * một hàm toán học để tìm vị trí mới, sau đó lấy chữ cái tương ứng của vị trí
 * mới trong bảng chữ cái
 */
public class Affine extends AAlgorithm {
    private int a;
    private int b;

    public Affine() {
        generateKey();
    }

    @Override
    public String generateKey() {
        Random rd = new Random();
        int m = alphabet.length();
        do {
            a = rd.nextInt(m) + 1;
        } while (gcd(a, m) != 1);
        b = rd.nextInt(m);
        return "a=" + a + ",b=" + b;
    }

    // Số lớn nhất 2 số chia được (ước chung lớn nhất)
    private int gcd(int x, int y) {
        while (y != 0) {
            int temp = y;
            y = x % y;
            x = temp;
        }
        return x;
    }

    @Override
    public void loadKey(String key) {
        String[] split = key.split(",");
        a = Integer.parseInt(split[0].substring(split[0].indexOf("=")));
        b = Integer.parseInt(split[1].substring(split[1].indexOf("=")));
    }

    @Override
    public String getKey() {
        return "a=" + a + ",b=" + b;
    }

    @Override
    public String encrypt(String msg) {
        char[] cs = msg.toCharArray();
        StringBuilder encryptContent = new StringBuilder();
        for (char c : cs) {
            int ind = alphabet.indexOf(c);
            if (ind != -1) {
                encryptContent.append(alphabet.charAt(findIndNew(ind)));
            } else {
                encryptContent.append(c);
            }
        }
        return encryptContent.toString();
    }

    public int findIndNew(int ind) {
        // Hàm toán học
        return (a * ind + b) % alphabet.length();
    }

    public String decrypt(String msg) {
        char[] cs = msg.toCharArray();
        StringBuilder decryptContent = new StringBuilder();

        int m = alphabet.length();
        int aInv = modInverse(a, m);

        for (char c : cs) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                int originalIndex = (aInv * (index - b + m)) % m;
                decryptContent.append(alphabet.charAt(originalIndex));
            } else {
                decryptContent.append(c);
            }
        }

        return decryptContent.toString();
    }

}

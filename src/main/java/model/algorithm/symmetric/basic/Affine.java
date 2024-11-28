package model.algorithm.symmetric.basic;

import model.AAlgorithm;
import model.Alphabet;

import java.util.Random;

/**
 * Note: Tìm vị trí của chữ cái trong bảng chữ cái sau đó đưa qua
 * một hàm toán học để tìm vị trí mới, sau đó lấy chữ cái tương ứng của vị trí
 * mới trong bảng chữ cái
 */
public class Affine extends AAlgorithm {
    private int a;
    private int b;
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
        try {
            String[] split = key.split(",");
            a = Integer.parseInt(split[0].trim().substring(split[0].indexOf("=") + 1));
            b = Integer.parseInt(split[1].trim().substring(split[1].indexOf("=") + 1));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Incorrect syntax");
        }
    }
    @Override
    public String getKey() {
        return "a=" + a + ",b=" + b;
    }
    @Override
    public String encrypt(String msg) {
        msg = convertMsg(msg);
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
    @Override
    public String decrypt(String msg) {
        msg = convertMsg(msg);
        char[] cs = msg.toCharArray();
        StringBuilder decryptContent = new StringBuilder();

        int m = alphabet.length();
        int aInv = modInverse(a, m);

        for (char c : cs) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                System.out.println("Index: " + index);
                System.out.println("b: " + b);
                System.out.println("A-1: " + aInv);
                int originalIndex = (aInv * (index - b + m)) % m;
                System.out.println(15-21+27);
                decryptContent.append(alphabet.charAt(originalIndex));
            } else {
                decryptContent.append(c);
            }
        }

        return decryptContent.toString();
    }
}

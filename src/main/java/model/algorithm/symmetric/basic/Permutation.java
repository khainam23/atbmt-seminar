package model.algorithm.symmetric.basic;

import model.AAlgorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 */
public class Permutation extends AAlgorithm {
    private int n;
    private int[] matrix;

    public Permutation() {
        generateKey();
    }

    public static void main(String[] args) {
        Permutation permutation = new Permutation();
        String msg = "hello world";
        String en = permutation.encrypt(msg);
        String de = permutation.decrypt(en);
        System.out.println(en);
        System.out.println(de);
    }

    @Override
    public String generateKey() {
        Random rd = new Random();
        n = rd.nextInt(7) + 3;
        matrix = new int[n];
        generatePermutation();
        return Arrays.toString(matrix);
    }

    private void generatePermutation() {
        Set<Integer> indUnique = new HashSet<>();
        Random rd = new Random();
        int i = 0;
        while (indUnique.size() != n) {
            int ind = rd.nextInt(n);
            if (!indUnique.contains(ind)) {
                indUnique.add(ind);
                matrix[i] = ind;
                ++i;
            }
        }
    }

    @Override
    public void loadKey(String key) {
        String[] numberStrs = key.substring(1, key.length() - 1)
                .replace(" ", "")
                .split(",");
        for (int i = 0; i < numberStrs.length; i++) {
            matrix[i] = Integer.parseInt(numberStrs[i]);
        }
    }

    @Override
    public String getKey() {
        return Arrays.toString(matrix);
    }

    @Override
    public String encrypt(String msg) {
        String[] strs = msg.split("(?<=\\G.{" + n + "})");
        if (strs[strs.length - 1].length() < n) {
            for (int i = 0; i < (n - strs[strs.length - 1].length()); i++) {
                strs[strs.length - 1] += " ";
            }
        }
        StringBuilder builder = new StringBuilder();
        Arrays.stream(strs).forEach(str -> builder.append(enMess(str)));
        return builder.toString();
    }

    private String enMess(String mess) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = mess.length();
        for (int i = 0; i < len; i++) {
            int indHash = matrix[i];
            stringBuilder.append(mess.charAt(indHash));
        }
        return stringBuilder.toString();
    }

    @Override
    public String decrypt(String msg) {
        String[] strs = msg.split("(?<=\\G.{" + n + "})");
        if (strs[strs.length - 1].length() < n) {
            throw new RuntimeException("Can't decrypt of msg");
        }
        StringBuilder builder = new StringBuilder();
        Arrays.stream(strs).forEach(str -> builder.append(deMess(str)));
        return builder.toString();
    }

    private String deMess(String mess) {
        StringBuilder stringBuilder = new StringBuilder(mess);
        int len = mess.length();
        for (int i = 0; i < len; i++) {
            int indHash = matrix[i];
            stringBuilder.setCharAt(indHash, mess.charAt(i));
        }
        return stringBuilder.toString();
    }
}

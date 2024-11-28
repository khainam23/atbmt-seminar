package model.algorithm.hash;

import model.AAlgorithm;

import java.security.MessageDigest;
import java.util.Random;

public abstract class AAlgorithmHash extends AAlgorithm {
    protected int size;
    protected String salt;
    private Random rd;

    @Override
    public String generateKey() {
        if(rd == null)
            rd = new Random();
        return generateKey(size == 0 ? rd.nextInt(90) + 10 : size);
    }

    private String generateKey(int csSize) {
        size = csSize;
        salt = "";
        for (int i = 0; i < size; i++) {
            salt +=  (char) (rd.nextInt(26) + 'a');
        }
        salt = convertMsg(salt);
        return salt;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if(size > 9) {
            generateKey(size);
        } else {
            throw new RuntimeException("Key so short.");
        }
    }

    @Override
    public String getKey() {
        return salt;
    }

    @Override
    public String encrypt(String msg) {
        try {
            final MessageDigest md = MessageDigest.getInstance(getNameAlgorithm());
            msg = msg + salt;
            byte[] hash = md.digest(msg.getBytes());
            return base64Encode(hash);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected abstract String getNameAlgorithm();

    @Override
    public void loadKey(String key) {
        if (key.length() == size) {
            salt = convertMsg(key);
        } else {
            throw new RuntimeException("[" + this.getClass() + "] Key so long");
        }
    }

    @Override
    public String decrypt(String msg) {
        throw new RuntimeException("[" + this.getClass() + "] Not support for decrypt");
    }
}

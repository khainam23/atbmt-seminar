package model;

public abstract class AAlgorithm {
    public abstract String generateKey();

    public abstract void loadKey(String key);

    public abstract String encrypt(String msg);
    public abstract String decrypt(String msg);

    public String convertMsg(String msg) {
        return msg.toLowerCase();
    }
}

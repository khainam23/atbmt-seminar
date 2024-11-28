package model.algorithm.hash;

public enum SHAType {
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    private final String des;

    SHAType(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }
}

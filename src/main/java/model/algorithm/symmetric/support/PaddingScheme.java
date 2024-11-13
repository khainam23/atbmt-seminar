package model.algorithm.symmetric.support;

public enum PaddingScheme {
    PKCS5Padding("PKCS#5"),
    PKCS7Padding("PKCS#7"),
    NoPadding("No padding"),
    ISO10126Padding("Random padding - ISO10126"),
    ZeroPadding("0x00");

    private final String description;

    PaddingScheme(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}

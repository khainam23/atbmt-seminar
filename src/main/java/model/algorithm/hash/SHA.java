package model.algorithm.hash;

public class SHA extends AAlgorithmHash {
    private SHAType shaType;

    public SHA() {
        shaType = SHAType.SHA1;
    }

    public void setShaType(SHAType shaType) {
        this.shaType = shaType;
    }

    @Override
    protected String getNameAlgorithm() {
        return shaType.getDes();
    }
}

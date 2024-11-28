package model.algorithm.hash;

import java.security.MessageDigest;

public class MD5 extends AAlgorithmHash {

    @Override
    protected String getNameAlgorithm() {
        return "MD5";
    }
}

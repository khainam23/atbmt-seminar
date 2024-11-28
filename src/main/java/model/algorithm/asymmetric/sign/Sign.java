package model.algorithm.asymmetric.sign;

import model.algorithm.asymmetric.AAsymmetric;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Sign extends AAsymmetric {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Override
    public String generateKey() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
            kpg.initialize(1024, sr);
            KeyPair keyPair = kpg.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
            return base64Encode(publicKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadKey(String key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            byte[] keyBytes = base64Decode(key.getBytes());
            PublicKey pubKey = keyFactory.generatePublic(new java.security.spec.X509EncodedKeySpec(keyBytes));
            this.publicKey = pubKey;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key: " + e.getMessage(), e);
        }
    }

    @Override
    public String getKey() {
        return String.format("""
                        ---Start public key---
                        %s
                        ---End public key---
                                        
                        ---Start private key---
                        %s
                        ---End private key---
                        """, base64Encode(publicKey.getEncoded()),
                base64Encode(privateKey.getEncoded()));
    }

    @Override
    public String encrypt(String filePath) {
        try {
            Signature signature = Signature.getInstance("DSA");
            signature.initSign(privateKey);
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            signature.update(fileData);
            byte[] signedData = signature.sign();
            return base64Encode(signedData);
        } catch (Exception e) {
            throw new RuntimeException("Signing failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String msg) {
        throw new RuntimeException("Not found file");
    }

    public String decrypt(String signedData, String filePath) throws Exception {
        Signature signature = Signature.getInstance("DSA");
        signature.initVerify(publicKey);
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));
        signature.update(fileData);
        byte[] signatureBytes = base64Decode(signedData.getBytes());
        return signature.verify(signatureBytes) ? "Valid signature" : "Invalid signature";
    }

    @Override
    public void setPriKey(String priKey) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(priKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPubKey(String pubKey) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(pubKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

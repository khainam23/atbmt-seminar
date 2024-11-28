package model.algorithm.asymmetric;

import model.AAlgorithm;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class RSA extends AAsymmetric {
    private Map<String, PublicKey> publicKeys;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private ArrayList<Integer> bitSize;
    private int keySize;

    @Override
    public String generateKey() {
        if(bitSize == null)
            bitSize = new ArrayList<>(List.of(1024, 2048, 4096));
        if(publicKeys == null)
            publicKeys = new HashMap<>();
        try {
            PublicKey pk;

            if (privateKey == null) {
                keySize = bitSize.get((int) (Math.random() * bitSize.size()));
                SecureRandom sr = new SecureRandom(); // Thuật toán sinh khóa ngẫu nhiên
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(keySize, sr);
                KeyPair keyPair = kpg.genKeyPair();
                privateKey = keyPair.getPrivate();
                pk = keyPair.getPublic();
            } else {
                RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
                pk = KeyFactory.getInstance("RSA")
                        .generatePublic(new RSAPublicKeySpec(rsaPrivateKey.getModulus(), BigInteger.valueOf(65537)));
            }
            String enBase64 = base64Encode(pk.getEncoded());
            publicKeys.put(enBase64, pk);
            publicKey = pk;
            return enBase64;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getBatchSizes() {
        return bitSize;
    }

    public int getIndexItem(Integer batch) {
        return bitSize.indexOf(batch);
    }

    public void setBatchSize(String bathSize) {
        Integer value = Integer.parseInt(bathSize);
        if(bitSize.contains(value)) {
            keySize = value;
            generateKey();
        } else
            throw new RuntimeException("Batch Size Exists");
    }

    public int getKeySize() {
        return keySize;
    }

    @Override
    public void loadKey(String key) {
        try {
            if (key.startsWith("both")) {
                String[] keys = key.split(",");
                String spk = keys[0].trim();
                String spbk = keys[1].trim();

                privateKey = loadPrivateKey(spk);
                PublicKey pk = loadPublicKey(spbk);
                publicKeys.put(base64Encode(pk.getEncoded()), pk);
                publicKey = pk;
            } else if (key.startsWith("private")) {
                privateKey = loadPrivateKey(key);
                resetPublicKey();
            } else {
                PublicKey pk = loadPublicKey(key);
                publicKeys.put(base64Encode(pk.getEncoded()), pk);
                publicKey = pk;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void resetPublicKey() {
        publicKeys.clear();
        generateKey();
    }

    private RSAPrivateKey loadPrivateKey(String privateKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    private RSAPublicKey loadPublicKey(String publicKeyString) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    @Override
    public String getKey() {
        return base64Encode(publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return base64Encode(privateKey.getEncoded());
    }

    public Set<String> getPublicKeys() {
        return publicKeys.keySet();
    }

    @Override
    public String encrypt(String msg) {
        return processEncrypt(msg, publicKey);
    }

    public String encrypt(String msg, String pk) {
        try {
            if (pk == null) {
                pk = getKey();
            }
            return processEncrypt(msg, loadPublicKey(pk));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isKey(String key) {
        return base64Decode(key.getBytes()).length == keySize;
    }

    private String processEncrypt(String msg, PublicKey pkc) {
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pkc);
            byte[] hashText = cipher.doFinal(msg.getBytes());
            return base64Encode(hashText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String msg) {
        try {
            byte[] convertMsg = base64Decode(msg.getBytes());
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            String oldMsg = new String(cipher.doFinal(convertMsg));
            return oldMsg;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPriKey(String priKey) {
        try {
            loadPrivateKey(priKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setPubKey(String pubKey) {
        try {
            loadPublicKey(pubKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
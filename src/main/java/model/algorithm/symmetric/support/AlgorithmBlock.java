package model.algorithm.symmetric.support;

import model.AAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Random;

/**
 * Date: 13/11/2024
 * Công việc: Class cấu trúc cho các thuật toán liên quan tới block
 * Các thuật toán đã triển khai: AES, DES
 */
public class AlgorithmBlock extends AAlgorithm {
    private String nameAlgorithm;
    private String nameMOO;
    private String namePadding;
    private SecretKey secretKey;

    public AlgorithmBlock(String nameAlgorithm) {
        this.nameAlgorithm = nameAlgorithm;
        generateKey();
    }

    @Override
    public String generateKey() {
        try {
            Random rd = new Random();
            nameMOO = ModesOfOperation.values()[rd.nextInt(ModesOfOperation.values().length)].name();
            namePadding = TypeInput.values()[rd.nextInt(TypeInput.values().length)].name();
            KeyGenerator keyGen = KeyGenerator.getInstance(nameAlgorithm);
            secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Nạp khóa từ chuỗi
    @Override
    public void loadKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, nameAlgorithm);
    }

    // Lấy khóa dưới dạng chuỗi Base64
    @Override
    public String getKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Mã hóa tin nhắn
    @Override
    public String encrypt(String msg) {
        try {
            Cipher cipher = Cipher.getInstance(nameAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(msg.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Giải mã tin nhắn
    @Override
    public String decrypt(String msg) {
        try {
            Cipher cipher = Cipher.getInstance(nameAlgorithm + "/" + nameMOO + " / " + namePadding);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(msg);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMOO(String nameMOO) {
        this.nameMOO = nameMOO;
    }

    public void setPadding(String namePadding) {
        this.namePadding = namePadding;
    }

    public String getNameMOO() {
        return nameMOO;
    }

    public String getNamePadding() {
        return namePadding;
    }
}

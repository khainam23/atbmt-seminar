package model;

import java.io.*;

public class Base64 extends AAlgorithm {
    @Override
    public String generateKey() {
        return null;
    }

    @Override
    public void loadKey(String key) {

    }

    @Override
    public String encrypt(String msg) {
        msg = convertMsg(msg);
        return java.util.Base64
                .getEncoder()
                .encodeToString(msg.getBytes());
    }

    @Override
    public String decrypt(String msg) {
        msg = convertMsg(msg);
        return new String(
                java.util.Base64
                        .getDecoder()
                        .decode(msg)
        );
    }

    public String encryptWithFile(File source, File dest) throws IOException, InterruptedException {
        // File checked
        String hash = encrypt(readContent(source));
        writeContent(dest, hash);
        return hash;
    }

    public String decryptWithFile(File source, File dest) throws IOException, InterruptedException {
        // File checked
        String msg = decrypt(readContent(source));
        writeContent(dest, msg);
        return msg;
    }

    public String readContent(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)
                )
        );
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();
        return builder.toString();
    }

    public void writeContent(File file, String msg) throws IOException, InterruptedException {
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(file)
        );
        bos.write(msg.getBytes());
        bos.flush();
        Thread.sleep(500);
        bos.close();
    }

    public static void main(String[] args) {

    }
}

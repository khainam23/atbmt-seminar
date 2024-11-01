import model.Alphabet;
import model.SubstitutionCipher;

public class SubstitutionCipherTest {
    public static void main(String[] args) {
        SubstitutionCipher substitutionCipher = new SubstitutionCipher();
        substitutionCipher.setAlphabet(Alphabet.VIETNAM);
        String key = substitutionCipher.generateKey();
        System.out.printf("Key generate: %s \n", key);
        substitutionCipher.loadKey(key);
        String text = "âbc";
        String hashText = substitutionCipher.encrypt(text);
        System.out.printf("After: %s - Before: %s \n", text, hashText);
        System.out.printf("Agter: %s - Before: %s", hashText, substitutionCipher.decrypt(hashText));
    }
}

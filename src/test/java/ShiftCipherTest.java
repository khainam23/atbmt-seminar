import model.Alphabet;
import model.algorithm.symmetric.basic.Caesar;


public class ShiftCipherTest {
    public static void main(String[] args) {
        Caesar shiftCipher = new Caesar();
        shiftCipher.setAlphabet(Alphabet.ENGLISH);
        String text = "âbc";
        System.out.printf("After: %s - Before: %s \n", text, shiftCipher.encrypt(text));
        String hashText = shiftCipher.encrypt(text);
        System.out.printf("After: %s - Before: %s", hashText, shiftCipher.decrypt(hashText));
    }
}

package model.algorithm.symmetric.basic;

import model.AAlgorithm;
import model.Alphabet;

import java.util.Arrays;
import java.util.Random;

/**
 * Note: Tạo ra một ma trận với các phần tử là số ngẫu nhiên sao cho có ma trận nghịch đảo,
 * chuyển chuỗi thành các mảng bằng độ dài matrix và biểu diễn bằng index trong bảng chữ cái,
 * tích 2 ma trận (lúc này là vị trí), lấy ra chuỗi theo các vị trí đó.
 */
public class Hill extends AAlgorithm {
    private int[][] keyMatrix;
    private int matrixSize;
    private char charPadding = 'q'; // Chỉ xuất hiện ở đầu trong mọi bảng chữ cái mà tui biết :>>

    public Hill() {
        generateKey();
    }

    public static void main(String[] args) {
        Hill hill = new Hill();
        hill.setAlphabet(Alphabet.VIETNAM);

        System.out.println(hill.getKey());
        hill.loadKey(hill.getKey());

        String msg = "Anh ban a";
        String encrypted = hill.encrypt(msg);
        System.out.println("Encrypted Message: " + encrypted);

        String decrypted = hill.decrypt(encrypted);
        System.out.println("Decrypted Message: " + decrypted);
    }

    @Override
    public String generateKey() {
        Random rd = new Random();
        matrixSize = (rd.nextInt(alphabet.length() - 2) + 2) % 6;
        keyMatrix = new int[matrixSize][matrixSize];
        int modInverse;
        do {
            for (int i = 0; i < matrixSize; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    keyMatrix[i][j] = rd.nextInt(alphabet.length());
                }
            }
            modInverse = modInverse(calculateDeterminant(keyMatrix), alphabet.length());
        } while (modInverse == -1);

        return matrixToString(keyMatrix);
    }

    @Override
    public void loadKey(String key) {
        // Tải khóa từ chuỗi và chuyển đổi thành ma trận
        String[] spaces = key.split("\n");
        String[] keyValues = key.split(",");
        System.out.println(Arrays.toString(keyValues));
        matrixSize = (int) Math.sqrt(keyValues.length);
        keyMatrix = new int[matrixSize][matrixSize];

        int index = 0;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                keyMatrix[i][j] = Integer.parseInt(keyValues[index++]);
            }
        }
    }

    @Override
    public String getKey() {
        return matrixToString(keyMatrix);
    }

    @Override
    public String encrypt(String msg) {
        return processMessage(msg, keyMatrix);
    }

    @Override
    public String decrypt(String msg) {
        // Xử lý thông điệp đầu vào
        String de = processMessage(msg, getInverseMatrix(keyMatrix));
        // Phòng trường hợp q xuất hiện đầu
        return de.charAt(0) + de.substring(1).replace(charPadding, ' ').trim();
    }


    private String processMessage(String msg, int[][] matrix) {
        msg = convertMsg(msg).replace(' ', charPadding);

        StringBuilder encryptedMessage = new StringBuilder();

        // Chia thông điệp thành các khối tương ứng với kích thước ma trận
        for (int i = 0; i < msg.length(); i += matrixSize) {
            int[] msgBlock = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                ;
                if (i + j < msg.length()) {
                    msgBlock[j] = alphabet.indexOf(msg.charAt(i + j));
                } else {
                    msgBlock[j] = alphabet.indexOf(charPadding);
                }
            }

            // Mã hóa hoặc giải mã bằng phép nhân ma trận
            int[] resultBlock = multiplyMatrix(matrix, msgBlock);
            for (int k = 0; k < matrixSize; k++) {
                encryptedMessage.append(alphabet.charAt(resultBlock[k] % alphabet.length()));
            }
        }

        return encryptedMessage.toString();
    }


    private int[] multiplyMatrix(int[][] matrix, int[] block) {
        int[] result = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            result[i] = 0;
            for (int j = 0; j < matrixSize; j++) {
                result[i] = (result[i] + matrix[i][j] * block[j]) % alphabet.length();
            }
        }
        return result;
    }

    private int[][] getInverseMatrix(int[][] matrix) {
        int[][] inverse = new int[matrixSize][matrixSize];
        int det = calculateDeterminant(keyMatrix);
        int modInverse = modInverse(det, alphabet.length());

        if (modInverse == -1) {
            throw new IllegalArgumentException("Matrix is not invertible.");
        }

        // Tính ma trận nghịch đảo
        int[][] adjugate = getAdjugateMatrix(matrix);
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                inverse[i][j] = (adjugate[i][j] * modInverse) % alphabet.length();
                if (inverse[i][j] < 0) {
                    inverse[i][j] += alphabet.length();  // Đảm bảo giá trị trong phạm vi 0-25
                }
            }
        }

        return inverse;
    }


    private int calculateDeterminant(int[][] matrix) {
        int matrixSize = matrix.length;
        if (matrixSize == 1) {
            return matrix[0][0] % alphabet.length();
        } else if (matrixSize == 2) {
            return (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % alphabet.length();
        } else if (matrixSize == 3) {
            // Định thức ma trận 3x3 (công thức hiện tại)
            return (matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
                    matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
                    matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0])) % alphabet.length();
        } else {
            // Định thức ma trận nxn bằng phương pháp đệ quy (Laplace expansion)
            int determinant = 0;
            for (int col = 0; col < matrixSize; col++) {
                int sign = (col % 2 == 0) ? 1 : -1;  // Thay đổi dấu theo cột
                int[][] minorMatrix = getMinor(matrix, 0, col);  // Lấy ma trận con
                determinant += sign * matrix[0][col] * calculateDeterminant(minorMatrix);
            }
            return determinant % alphabet.length();
        }
    }

    // Hàm lấy ma trận con (minor matrix) từ ma trận gốc
    private int[][] getMinor(int[][] matrix, int row, int col) {
        int matrixSize = matrix.length;
        int[][] minor = new int[matrixSize - 1][matrixSize - 1];
        int r = 0;
        for (int i = 0; i < matrixSize; i++) {
            if (i == row) continue;  // Bỏ qua hàng cần bỏ
            int c = 0;
            for (int j = 0; j < matrixSize; j++) {
                if (j == col) continue;  // Bỏ qua cột cần bỏ
                minor[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minor;
    }


    private int[][] getAdjugateMatrix(int[][] matrix) {
        int matrixSize = matrix.length;
        int[][] adjugateMatrix = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                int[][] minor = getMinor(matrix, i, j);
                adjugateMatrix[j][i] = sign * calculateDeterminant(minor);
            }
        }
        return adjugateMatrix;
    }

    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                sb.append(matrix[i][j]);
                if (i != matrixSize - 1 || j != matrixSize - 1) {
                    sb.append(",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

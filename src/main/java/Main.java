import view.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            try {
                frame.start();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error:" + exception.getMessage());
            }
        });
    }
}

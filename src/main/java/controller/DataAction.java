package controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class DataAction {
    public static String readFile(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) content.append(line);
            return content.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void choiceFile(IRunAction action) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setDialogTitle("Open File");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        // Tạo hộp thư mục lựa chọn
        int x = fileChooser.showOpenDialog(null);
        // Kiểm tra xem có cho phép khởi tạo
        if (x == JFileChooser.APPROVE_OPTION) {
            // File đã lựa chọn
            File f = fileChooser.getSelectedFile();
            action.run(f);
        }
    }
    public static void saveContentToFile(final String data) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Kiểm tra xem người dùng đã chọn phần mở rộng chưa
            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(data);
                JOptionPane.showMessageDialog(null, "File saved successfully!");
                fileWriter.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        }
    }
}

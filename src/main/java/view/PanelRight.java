package view;

import config.DimensionConfig;
import config.IconConfig;
import config.LayoutConfig;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PanelRight extends JPanel {
    private static PanelRight panelRight;
    private final CardLayout cardLayout = new CardLayout();
    private JTextArea description;
    private JButton btnSave;

    private PanelRight() {
        init();
    }

    public static PanelRight getInstance() {
        return panelRight == null ? panelRight = new PanelRight() : panelRight;
    }

    private void init() {
        setLayout(cardLayout);
        add(defaultLayout(), LayoutConfig.DEFAULT_LAYOUT);
        setPreferredSize(DimensionConfig.SIZE_RIGHT);
    }

    private JPanel defaultLayout() {
        ScrollPaneWin11 scrollPane = new ScrollPaneWin11();
        description = new JTextArea();
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        scrollPane.setViewportView(description);

        btnSave = new JButton(IconConfig.ICON_SAVE);
        btnSave.addActionListener(e -> saveContentToFile());
        btnSave.setVisible(false);

        JPanel paneDefault = new JPanel(new BorderLayout());

        paneDefault.add(scrollPane, BorderLayout.CENTER);
        paneDefault.add(btnSave, BorderLayout.SOUTH);
        return paneDefault;
    }

    private void saveContentToFile() {
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
                fileWriter.write(description.getText());
                JOptionPane.showMessageDialog(null, "File saved successfully!");
                fileWriter.flush();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        }
    }

    public void setDescription(String msg, boolean isShowSave) {
        description.setText(msg);
        btnSave.setVisible(isShowSave);
    }
}

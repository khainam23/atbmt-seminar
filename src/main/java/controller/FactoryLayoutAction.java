package controller;

import config.LayoutConfig;
import model.algorithm.symmetric.basic.Caesar;
import view.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Note: Hỗ trợ trong việc duy chuyển và khởi tạo các màn hình
 */
public class FactoryLayoutAction extends MouseAdapter {
    // Sử dụng để gọi hiển thị màn hình
    private static final PanelCenter panelCenter = PanelCenter.getInstance();
    // Truyền thông tin bổ sung
    private static final PanelRight panelRight = PanelRight.getInstance();
    // Sử dụng mẫu Singleton
    private static FactoryLayoutAction factoryLayoutAction;
    // Quản lý màn hình hiện tại
    private String uidLayout;
    // Các thuật toán cơ bản có cách duy chuyển màn hình giống nhau
    private final List<String> algorithmBasic;
    // Các thuật toán đối xứng có cách duy chuyển màn hình giống nhau
    private final List<String> algorithmSymmetric;
    // Quản lý các layout thực tế tương ứng với name
    private final Map<String, JPanel> mapLayout;

    private FactoryLayoutAction() {
        algorithmBasic = Arrays.asList(
                LayoutConfig.CAESAR_LAYOUT,
                LayoutConfig.HILL_LAYOUT,
                LayoutConfig.SUBSTITUTION_CIPHER_LAYOUT,
                LayoutConfig.VIGENERE_LAYOUT
        );
        algorithmSymmetric = Arrays.asList(
                LayoutConfig.DES_LAYOUT,
                LayoutConfig.AES_LAYOUT
        );
        mapLayout = new HashMap<>();
        mapLayout.put(LayoutConfig.CAESAR_LAYOUT, PanelCaesarLayout.getInstance());
        mapLayout.put(LayoutConfig.HILL_LAYOUT, PanelHill.getInstance());
        mapLayout.put(LayoutConfig.SUBSTITUTION_CIPHER_LAYOUT, PanelSubstitutionLayout.getInstance());
        mapLayout.put(LayoutConfig.VIGENERE_LAYOUT, PanelVigenere.getInstance());
        mapLayout.put(LayoutConfig.AES_LAYOUT, PanelAes.getInstance());
        mapLayout.put(LayoutConfig.DES_LAYOUT, PanelDes.getInstance());
    }

    public static FactoryLayoutAction getInstance() {
        return factoryLayoutAction == null ? factoryLayoutAction = new FactoryLayoutAction() : factoryLayoutAction;
    }

    // Sự kiện khi chuột được nhấn
    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel panel = (JPanel) e.getSource();
        JLabel label = (JLabel) panel.getComponent(1);
        String chooseName = label.getText().toLowerCase();
        moveLayout(chooseName);
    }

    // Quy trình di chuyển màn hình
    private void moveLayout(String nameLayout, String... content) {
        uidLayout = nameLayout;
        if (content.length == 0) {
            panelRight.setDescription(
                    "Please choose file just has extension is .txt",
                    false
            );
            if(!algorithmBasic.contains(uidLayout)) {
                panelCenter.moveLayout(LayoutConfig.FILE_INPUT_LAYOUT);
            } else {
                moveLayout(uidLayout, "");
            }
            return;
        }
        setContentForLayout(content[0]);
        panelCenter.moveLayout(uidLayout);
    }

    // Truyền và thiết lập nội dung nhận được trên giao diện của từng phần
    private void setContentForLayout(String content) {
        IContentPanel panel = (IContentPanel) getPanel(uidLayout);
        panel.setContent(content);
    }
    // Đọc và gửi nội dung của file đi tới phần tử được chọn
    public void readAndSendContent(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Thực hiện thao tác gửi nội dung ở đây (ví dụ: in ra màn hình)
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        moveLayout(uidLayout, content.toString());
    }
    // Lấy phần tử theo giá trị uid
    public JPanel getPanel(String layout) {
        return mapLayout.get(layout);
    }
    // Ghi nội dung cho phần tử bên phải
    public void showMsgInRight(String content) {
        panelRight.setDescription(content, true);
    }
    public Map<String, JPanel> getMapLayout() {
        return mapLayout;
    }
}

package controller;

import config.LayoutConfig;
import view.PanelCenter;
import view.PanelRight;
import view.algorithm.APanel;
import view.algorithm.asymmetric.PanelRsa;
import view.algorithm.hash.PanelMD5;
import view.algorithm.hash.PanelSHA;
import view.algorithm.asymmetric.sign.PanelSign;
import view.algorithm.symmetric.basic.*;
import view.algorithm.symmetric.support.PanelAes;
import view.algorithm.symmetric.support.PanelDes;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Note: Hỗ trợ trong việc di chuyển và khởi tạo các màn hình
 */
public class FactoryLayoutAction extends MouseAdapter {
    // Sử dụng để gọi hiển thị màn hình
    private static final PanelCenter panelCenter = PanelCenter.getInstance();
    // Sử dụng để truyền thông tin bổ sung
    private static final PanelRight panelRight = PanelRight.getInstance();
    // Sử dụng mẫu Singleton
    private static volatile FactoryLayoutAction factoryLayoutAction;
    // Các thuật toán cơ bản
    private final List<String> algorithmBasic;
    private final List<String> algorithmAsymmetric;
    // Các thuật toán đối xứng
    private final List<String> algorithmSymmetric;
    // Các thuật toán băm
    private final List<String> algorithmHash;
    // Các thuật toán chữ ký điện tử
    private final List<String> algorithmSign;
    // Quản lý các layout thực tế tương ứng với name
    private final Map<String, JPanel> mapLayout;
    // Quản lý màn hình hiện tại
    private String uidLayout;
    // Định hình loại nhập liệu đầu vào {File or Text}
    private ControlTypeInput controlTypeInput = ControlTypeInput.getInstance();

    private FactoryLayoutAction() {
        algorithmBasic = List.of(
                LayoutConfig.CAESAR_LAYOUT,
                LayoutConfig.HILL_LAYOUT,
                LayoutConfig.SUBSTITUTION_CIPHER_LAYOUT,
                LayoutConfig.VIGENERE_LAYOUT,
                LayoutConfig.AFFINE_LAYOUT,
                LayoutConfig.PERMUTATION_LAYOUT
        );
        algorithmSymmetric = List.of(
                LayoutConfig.DES_LAYOUT,
                LayoutConfig.AES_LAYOUT
        );
        algorithmAsymmetric = List.of(
                LayoutConfig.RSA_LAYOUT
        );
        algorithmHash = List.of(
                LayoutConfig.MD5_LAYOUT,
                LayoutConfig.SHA_LAYOUT
        );
        algorithmSign = List.of(
                LayoutConfig.SIGN_LAYOUT
        );
        mapLayout = new HashMap<>();
        // symmetric
        {
            // basic
            mapLayout.put(LayoutConfig.CAESAR_LAYOUT, PanelCaesar.getInstance());
            mapLayout.put(LayoutConfig.HILL_LAYOUT, PanelHill.getInstance());
            mapLayout.put(LayoutConfig.SUBSTITUTION_CIPHER_LAYOUT, PanelSubstitution.getInstance());
            mapLayout.put(LayoutConfig.VIGENERE_LAYOUT, PanelVigenere.getInstance());
            mapLayout.put(LayoutConfig.AFFINE_LAYOUT, PanelAffine.getInstance());
            mapLayout.put(LayoutConfig.PERMUTATION_LAYOUT, PanelPermutation.getInstance());
            // support
            mapLayout.put(LayoutConfig.AES_LAYOUT, PanelAes.getInstance());
            mapLayout.put(LayoutConfig.DES_LAYOUT, PanelDes.getInstance());
        }
        // asymmetric
        {
            mapLayout.put(LayoutConfig.RSA_LAYOUT, PanelRsa.getInstance());
        }
        // hash
        {
            mapLayout.put(LayoutConfig.MD5_LAYOUT, PanelMD5.getInstance());
            mapLayout.put(LayoutConfig.SHA_LAYOUT, PanelSHA.getInstance());
        }
        // sign
        {
            mapLayout.put(LayoutConfig.SIGN_LAYOUT, PanelSign.getInstance());
        }
    }

    synchronized public static FactoryLayoutAction getInstance() {
        return factoryLayoutAction == null ? factoryLayoutAction = new FactoryLayoutAction() : factoryLayoutAction;
    }

    // Sự kiện khi chuột được nhấn
    @Override
    public void mouseClicked(MouseEvent e) {
        JPanel panel = (JPanel) e.getSource();
        JLabel label = (JLabel) panel.getComponent(1);
        String chooseName = label.getText().toLowerCase();
        moveLayout(chooseName, null);
    }

    /**
     * Quy trình di chuyển màn hình.
     *
     * @param nameLayout
     * @param content
     */
    private void moveLayout(String nameLayout, String content) {
        uidLayout = nameLayout;
        if (content == null) {
            showMsgInRight("");
            if (algorithmBasic.contains(uidLayout) ||
                    algorithmAsymmetric.contains(uidLayout) ||
                    algorithmSign.contains(uidLayout)
            ) {
                // Nhập nội dung
                moveLayout(uidLayout, "");
            } else if (algorithmSymmetric.contains(uidLayout) || algorithmHash.contains(uidLayout)) {
                // Chọn nhập hay lấy file
                String nameLayoutInput = controlTypeInput.getNameLayoutInput();
                if (nameLayoutInput == null) {
                    // Nhập tay
                    moveLayout(uidLayout, "");
                } else if (nameLayoutInput.equals(LayoutConfig.FILE_INPUT_LAYOUT)) {
                    // Thông qua file
                    showMsgInRight("Please choose file type is *.txt");
                    panelCenter.moveLayout(nameLayoutInput);
                }
            }
            return;
        }
        setContentForLayout(content);
        panelCenter.moveLayout(nameLayout);
    }

    // Truyền và thiết lập nội dung nhận được trên giao diện của từng phần
    private void setContentForLayout(String content) {
        APanel panel = (APanel) mapLayout.get(uidLayout);
        panel.setContent(content);
    }

    // Đọc và gửi nội dung của file đi tới phần tử được chọn
    public void readAndSendContent(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        moveLayout(uidLayout, DataAction.readFile(file));
    }

    // Ghi nội dung cho phần tử bên phải
    public void showMsgInRight(String content) {
        panelRight.setDescription(content, true);
    }

    public Map<String, JPanel> getMapLayout() {
        return mapLayout;
    }
}

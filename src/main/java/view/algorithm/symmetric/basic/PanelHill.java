package view.algorithm.symmetric.basic;

import config.IconConfig;
import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.Hill;
import view.algorithm.APanel;
import view.custom.ButtonCustom;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelHill extends APanel {
    private static PanelHill panelHill;
    private Hill hillModel;

    public static PanelHill getInstance() {
        return panelHill == null ? panelHill = new PanelHill() : panelHill;
    }

    @Override
    protected void init() {
        hillModel = new Hill();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        JComboBox<String> comboBoxLanguages = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        JPanel panelOption = new JPanel(new GridLayout(1, 3));
        ButtonCustom btnReload = new ButtonCustom(IconConfig.ICON_RELOAD);
        ButtonCustom btnLoadKey = new ButtonCustom(IconConfig.ICON_INPUT);
        JLabel viewArr = new JLabel(
                hillModel.getKey()
        );
        btnReload.addActionListener(e -> {
            hillModel.generateKey();
            viewArr.setText(hillModel.getKey());
        });
        btnLoadKey.addActionListener(e -> {
            String inputKey = JOptionPane.showInputDialog(null, "Input your key. Ex: [12, 10], [7, 5]", "Custom Key", JOptionPane.PLAIN_MESSAGE);
            if(inputKey == null || inputKey.isBlank()) {
                JOptionPane.showMessageDialog(null, "Key isn't empty");
            } else {
                try {
                    hillModel.loadKey(inputKey);
                    viewArr.setText(hillModel.getKey());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        panelOption.add(viewArr);
        panelOption.add(btnReload);
        panelOption.add(btnLoadKey);
        panelTop.add(comboBoxLanguages);
        panelTop.add(panelOption);

        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(e -> {
            String alphabetName = (String) comboBoxLanguages.getSelectedItem();
            String content = textArea.getText();

            if (!hillModel.getNameAlphabet().equals(alphabetName)) {
                hillModel.setAlphabet(alphabetName);
            }
            String encryptContent = hillModel.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String alphabetName = (String) comboBoxLanguages.getSelectedItem();
            String content = textArea.getText();

            if (!hillModel.getNameAlphabet().equals(alphabetName)) {
                hillModel.setAlphabet(alphabetName);
            }
            String decryptContent = hillModel.decrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(decryptContent);
        });
        panelBottom.add(btnEncrypt);
        panelBottom.add(btnDecrypt);

        add(panelTop, BorderLayout.NORTH);
        add(createPanelContent(), BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
}

package view.algorithm.symmetric.basic;

import config.IconConfig;
import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.Affine;
import view.algorithm.APanel;
import view.custom.ButtonCustom;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelAffine extends APanel {
    private static PanelAffine panelAffine;
    private Affine affineModel;
    private JLabel labelHashLanguage;

    public static PanelAffine getInstance() {
        return panelAffine == null ? panelAffine = new PanelAffine() : panelAffine;
    }

    @Override
    protected void init() {
        affineModel = new Affine();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelOptionFunction = new JPanel(new GridLayout(1, 1, 5,5 ));
        JComboBox<String> comboBoxTypesInput = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        comboBoxTypesInput.addItemListener(item -> {
            String nameType = (String) item.getItem();
            affineModel.setAlphabet(nameType);
            labelHashLanguage.setText(affineModel.generateKey());
        });
        panelOptionFunction.add(comboBoxTypesInput);

        JPanel panelShowGenerateLanguage = getPanelShowGenerateLanguage();

        panelTop.add(panelOptionFunction);
        panelTop.add(panelShowGenerateLanguage);

        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(e -> {
            String content = textArea.getText();
            String encryptContent = affineModel.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String content = textArea.getText();
            String decryptContent = affineModel.decrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(decryptContent);
        });
        panelBottom.add(btnEncrypt);
        panelBottom.add(btnDecrypt);

        add(panelTop, BorderLayout.NORTH);
        add(createPanelContent(), BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private JPanel getPanelShowGenerateLanguage() {
        JPanel panelShowGenerateLanguage = new JPanel(new GridLayout(1, 3, 5, 5));
        ButtonCustom btnReloadAlphabet = new ButtonCustom(IconConfig.ICON_RELOAD);
        btnReloadAlphabet.addActionListener(e -> {
            labelHashLanguage.setText(affineModel.generateKey());
            repaint();
        });
        ButtonCustom btnLoadKey = new ButtonCustom(IconConfig.ICON_INPUT);
        btnLoadKey.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(null, "Input key. Ex: a=4,b=5", "Custom key of user", JOptionPane.PLAIN_MESSAGE);
            if (key == null || key.isBlank()) {
                JOptionPane.showMessageDialog(null, "Key isn't empty!!!");
            } else {
                try {
                    affineModel.loadKey(key);
                    labelHashLanguage.setText(affineModel.getKey());
                    JOptionPane.showMessageDialog(null, "Success", "Done", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        labelHashLanguage = new JLabel(affineModel.getKey(), SwingConstants.CENTER);
        labelHashLanguage.setFont(new Font("Arial", Font.PLAIN, 15));
        panelShowGenerateLanguage.add(btnLoadKey);
        panelShowGenerateLanguage.add(btnReloadAlphabet);
        panelShowGenerateLanguage.add(labelHashLanguage);
        return panelShowGenerateLanguage;
    }
}

package view.algorithm.symmetric.support;

import config.IconConfig;
import controller.FactoryLayoutAction;
import model.algorithm.symmetric.support.AlgorithmBlock;
import model.algorithm.symmetric.support.ModesOfOperation;
import model.algorithm.symmetric.support.PaddingScheme;
import model.algorithm.symmetric.support.TypeInput;
import view.algorithm.APanel;
import view.custom.ButtonCustom;
import view.custom.ScrollPaneWin11;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelAes extends APanel {
    private static PanelAes panelAes;
    private AlgorithmBlock aesModel;
    private JLabel labelHashLanguage;

    public static PanelAes getInstance() {
        return panelAes == null ? panelAes = new PanelAes() : panelAes;
    }
    @Override
    protected void init() {
        aesModel = new AlgorithmBlock("AES");

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelOptionFunction = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JComboBox<String> comboBoxTypesInput = new JComboBox<>(Arrays.stream(TypeInput.values()).map(Enum::name).toArray(String[]::new));
        comboBoxTypesInput.setSelectedItem(aesModel.getNameMOO());
        comboBoxTypesInput.addItemListener(item -> {
            String nameType = (String) item.getItem();
            if (nameType.equals(TypeInput.TEXT.name())) {
                textArea.setEditable(true);
            } else {
                textArea.setEditable(false);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        panelOptionFunction.add(comboBoxTypesInput, gbc);

        JComboBox<String> comboBoxMOOs = new JComboBox<>(Arrays.stream(ModesOfOperation.values()).map(Enum::name).toArray(String[]::new));
        comboBoxTypesInput.setSelectedItem(aesModel.getNamePadding());
        comboBoxMOOs.addItemListener(item -> {
            String nameMOO = (String) item.getItem();
            aesModel.setMOO(nameMOO);
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        panelOptionFunction.add(comboBoxMOOs, gbc);

        JComboBox<String> comboBoxPaddings = new JComboBox<>(Arrays.stream(PaddingScheme.values()).map(PaddingScheme::getDescription).toArray(String[]::new));
        comboBoxPaddings.addItemListener(item -> {
            String namePadding = (String) item.getItem();
            String convertToIdPadding = Arrays.stream(PaddingScheme.values())
                    .filter(uidPadding -> uidPadding.name().equals(namePadding))
                    .map(Enum::name)
                    .findFirst()
                    .orElse(null);
            aesModel.setPadding(convertToIdPadding);
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        panelOptionFunction.add(comboBoxPaddings, gbc);

        JPanel panelShowGenerateLanguage = getPanelShowGenerateLanguage();

        panelTop.add(panelOptionFunction);
        panelTop.add(panelShowGenerateLanguage);

        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(e -> {
            String content = textArea.getText();
            String encryptContent = aesModel.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String content = textArea.getText();
            String decryptContent = aesModel.decrypt(content);
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
            labelHashLanguage.setText(aesModel.generateKey());
            repaint();
        });
        ButtonCustom btnLoadKey = new ButtonCustom(IconConfig.ICON_INPUT);
        btnLoadKey.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(null, "Input key", "Custom key of user", JOptionPane.PLAIN_MESSAGE);
            if (key == null || key.isBlank()) {
                JOptionPane.showMessageDialog(null, "Key isn't empty!!!");
            } else {
                try {
                    aesModel.loadKey(key);
                    labelHashLanguage.setText(aesModel.getKey());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        labelHashLanguage = new JLabel(aesModel.getKey(), SwingConstants.CENTER);
        labelHashLanguage.setFont(new Font("Arial", Font.PLAIN, 15));
        panelShowGenerateLanguage.add(btnLoadKey);
        panelShowGenerateLanguage.add(btnReloadAlphabet);
        panelShowGenerateLanguage.add(labelHashLanguage);
        return panelShowGenerateLanguage;
    }
}

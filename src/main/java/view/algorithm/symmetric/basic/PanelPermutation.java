package view.algorithm.symmetric.basic;

import config.IconConfig;
import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.Permutation;
import view.algorithm.APanel;
import view.custom.ButtonCustom;
import view.custom.ScrollPaneWin11;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelPermutation extends APanel {
    private static PanelPermutation panelPermutation;
    private Permutation permutationModel;

    public static PanelPermutation getInstance() {
        return panelPermutation == null ? panelPermutation = new PanelPermutation() : panelPermutation;
    }

    @Override
    protected void init() {
        permutationModel = new Permutation();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        JComboBox<String> comboBoxLanguages = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        JPanel panelOption = new JPanel(new GridLayout(1, 3));
        ButtonCustom btnReload = new ButtonCustom(IconConfig.ICON_RELOAD);
        ButtonCustom btnLoadKey = new ButtonCustom(IconConfig.ICON_INPUT);
        JLabel viewArr = new JLabel(
                permutationModel.getKey()
        );
        btnReload.addActionListener(e -> {
            permutationModel.generateKey();
            viewArr.setText(permutationModel.getKey());
        });
        btnLoadKey.addActionListener(e -> {
            String inputKey = JOptionPane.showInputDialog(null, "Input your key. Ex: [12, 10]", "Custom Key", JOptionPane.PLAIN_MESSAGE);
            if(inputKey == null || inputKey.isBlank()) {
                JOptionPane.showMessageDialog(null, "Key isn't empty");
            } else {
                try {
                    permutationModel.loadKey(inputKey);
                    viewArr.setText(permutationModel.getKey());
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

            if (!permutationModel.getNameAlphabet().equals(alphabetName)) {
                permutationModel.setAlphabet(alphabetName);
            }
            String encryptContent = permutationModel.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String alphabetName = (String) comboBoxLanguages.getSelectedItem();
            String content = textArea.getText();

            if (!permutationModel.getNameAlphabet().equals(alphabetName)) {
                permutationModel.setAlphabet(alphabetName);
            }
            String decryptContent = permutationModel.decrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(decryptContent);
        });
        panelBottom.add(btnEncrypt);
        panelBottom.add(btnDecrypt);

        add(panelTop, BorderLayout.NORTH);
        add(createPanelContent(), BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }
}

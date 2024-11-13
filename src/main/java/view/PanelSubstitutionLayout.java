package view;

import config.IconConfig;
import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.SubstitutionCipher;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelSubstitutionLayout extends JPanel implements IContentPanel {
    private static PanelSubstitutionLayout panelCaesarLayout;
    private SubstitutionCipher substitutionCipher;
    private JTextArea textArea;
    private JLabel labelHashLanguage;

    private PanelSubstitutionLayout() {
        init();
    }

    public static PanelSubstitutionLayout getInstance() {
        return panelCaesarLayout == null ? panelCaesarLayout = new PanelSubstitutionLayout() : panelCaesarLayout;
    }

    private void init() {
        substitutionCipher = new SubstitutionCipher();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel panelOptionFunction = new JPanel(new GridLayout(1, 2, 5, 5));
        JComboBox<String> comboBoxLanguages = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        comboBoxLanguages.addItemListener(item -> {
            String nameAlphabet = (String) item.getItem();
            substitutionCipher.setAlphabet(nameAlphabet);
            labelHashLanguage.setText(substitutionCipher.generateKey());
        });
        JPanel panelShowGenerateLanguage = getPanelShowGenerateLanguage();
        panelOptionFunction.add(comboBoxLanguages);
        panelTop.add(panelOptionFunction);
        panelTop.add(panelShowGenerateLanguage);

        JPanel panelCenter = new JPanel(new BorderLayout());
        ScrollPaneWin11 scrollPane = new ScrollPaneWin11();
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setViewportView(textArea);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnEncrypt = new JButton("Encrypt");
        btnEncrypt.addActionListener(e -> {
            String content = textArea.getText();
            String encryptContent = substitutionCipher.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String content = textArea.getText();
            String decryptContent = substitutionCipher.decrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(decryptContent);
        });
        panelBottom.add(btnEncrypt);
        panelBottom.add(btnDecrypt);

        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private JPanel getPanelShowGenerateLanguage() {
        JPanel panelShowGenerateLanguage = new JPanel(new GridLayout(1, 3, 5, 5));
        ButtonCustom btnReloadAlphabet = new ButtonCustom(IconConfig.ICON_RELOAD);
        btnReloadAlphabet.addActionListener(e -> {
            labelHashLanguage.setText(substitutionCipher.generateKey());
            repaint();
        });
        ButtonCustom btnLoadKey = new ButtonCustom(IconConfig.ICON_INPUT);
        btnLoadKey.addActionListener(e -> {
            String key = JOptionPane.showInputDialog(null, "Input key", "Custom key of user", JOptionPane.PLAIN_MESSAGE);
            if(key == null || key.isBlank()) {
                JOptionPane.showMessageDialog(null, "Key isn't empty!!!");
            } else {
                try {
                    substitutionCipher.loadKey(key);
                    labelHashLanguage.setText(substitutionCipher.getKey());
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            }
        });
        labelHashLanguage = new JLabel(substitutionCipher.getKey(), SwingConstants.CENTER);
        labelHashLanguage.setFont(new Font("Arial", Font.PLAIN, 15));
        panelShowGenerateLanguage.add(btnLoadKey);
        panelShowGenerateLanguage.add(btnReloadAlphabet);
        panelShowGenerateLanguage.add(labelHashLanguage);
        return panelShowGenerateLanguage;
    }

    @Override
    public void setContent(String content) {
        textArea.setText(content);
    }
}

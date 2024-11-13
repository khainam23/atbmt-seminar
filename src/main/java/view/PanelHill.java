package view;

import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.Hill;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelHill extends JPanel implements IContentPanel {
    private static PanelHill panelHill;
    private JTextArea textArea;
    private Hill hillModel;

    private PanelHill() {
        init();
    }

    public static PanelHill getInstance() {
        return panelHill == null ? panelHill = new PanelHill() : panelHill;
    }

    private void init() {
        hillModel = new Hill();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(1, 2));
        JComboBox<String> comboBoxLanguages = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        panelTop.add(comboBoxLanguages);

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
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    @Override
    public void setContent(String content) {
        textArea.setText(content);
    }
}

package view;

import controller.FactoryLayoutAction;
import model.Alphabet;
import model.algorithm.symmetric.basic.Caesar;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Arrays;

public class PanelCaesarLayout extends JPanel implements IContentPanel {
    private static PanelCaesarLayout panelCaesarLayout;
    private JTextArea textArea;
    private Caesar caesarModel;

    private PanelCaesarLayout() {
        init();
    }

    public static PanelCaesarLayout getInstance() {
        return panelCaesarLayout == null ? panelCaesarLayout = new PanelCaesarLayout() : panelCaesarLayout;
    }

    private void init() {
        caesarModel = new Caesar();

        setLayout(new BorderLayout(2, 2));

        JPanel panelTop = new JPanel(new GridLayout(1, 2));
        JComboBox<String> comboBoxLanguages = new JComboBox<>(Arrays.stream(Alphabet.values()).map(Enum::name).toArray(String[]::new));
        SpinnerNumberModel modelSpinner = new SpinnerNumberModel(Integer.parseInt(caesarModel.getKey()), 1, Alphabet.ENGLISH.getDescription().length(), 1);
        JSpinner spinner = new JSpinner(modelSpinner);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        editor.getTextField().setFormatterFactory(new DefaultFormatterFactory(formatter));
        panelTop.add(comboBoxLanguages);
        panelTop.add(spinner);

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
            int spinnerNumber = (int) spinner.getValue();
            String content = textArea.getText();

            if (spinnerNumber != Integer.parseInt(caesarModel.generateKey())) {
                caesarModel.loadKey(spinnerNumber + "");
            }
            if(!caesarModel.getNameAlphabet().equals(alphabetName)) {
                caesarModel.setAlphabet(alphabetName);
            }
            String encryptContent = caesarModel.encrypt(content);
            FactoryLayoutAction.getInstance().showMsgInRight(encryptContent);
        });
        JButton btnDecrypt = new JButton("Decrypt");
        btnDecrypt.addActionListener(e -> {
            String alphabetName = (String) comboBoxLanguages.getSelectedItem();
            int spinnerNumber = (int) spinner.getValue();
            String content = textArea.getText();

            if (spinnerNumber != Integer.parseInt(caesarModel.generateKey())) {
                caesarModel.loadKey(spinnerNumber + "");
            }

            if(!caesarModel.getNameAlphabet().equals(alphabetName)) {
                caesarModel.setAlphabet(alphabetName);
            }
            String decryptContent = caesarModel.decrypt(content);
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

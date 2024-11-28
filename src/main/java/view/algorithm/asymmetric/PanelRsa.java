package view.algorithm.asymmetric;

import config.IconConfig;
import config.LayoutConfig;
import controller.ControlTypeInput;
import controller.DataAction;
import model.algorithm.asymmetric.RSA;
import view.algorithm.APanel;
import view.custom.ButtonCustom;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelRsa extends APanel {
    private static PanelRsa panelRsa;
    private final String textHint = "Key have size is: ";
    private RSA rsaModel;

    public static PanelRsa getInstance() {
        return panelRsa == null ? panelRsa = new PanelRsa() : panelRsa;
    }

    @Override
    protected void init() {
        rsaModel = new RSA();

        JPanel panelTop = new JPanel(new BorderLayout());
        JPanel panelOptions = new JPanel(new FlowLayout());
        ButtonCustom btnDowAllKey = getBtn("Download all key", e -> {
            final String startSpace = "---Start key---\n";
            final String endSpace = "---End key---\n\n";
            final String content = rsaModel.getPublicKeys()
                    .stream()
                    .map(key -> startSpace + key + "\n" + endSpace)
                    .reduce("", String::concat);
            DataAction.saveContentToFile(content);
        });
        panelOptions.add(btnDowAllKey);
        JPanel panelKeys = new JPanel(new GridLayout(4, 1, 3, 3));
        JPanel panelPriKey = getPanelKey(rsaModel.getPrivateKey());
        JPanel panelPubKey = getPanelKey(rsaModel.getKey());
        JLabel hint = new JLabel(textHint + rsaModel.getKeySize());
        JComboBox<String> comboBoxBatchSize = new JComboBox<>(rsaModel.getBatchSizes().stream().map(String::valueOf).toArray(String[]::new));
        comboBoxBatchSize.setSelectedItem(rsaModel.getIndexItem(rsaModel.getKeySize()));
        comboBoxBatchSize.addItemListener(item -> {
            String bathSize = (String) item.getItem();
            rsaModel.setBatchSize(bathSize);
            hint.setText(textHint + rsaModel.getKeySize());
        });
        panelKeys.add(panelPriKey);
        panelKeys.add(panelPubKey);
        panelKeys.add(comboBoxBatchSize);
        panelKeys.add(hint);
        panelTop.add(panelOptions, BorderLayout.CENTER);
        panelTop.add(panelKeys, BorderLayout.SOUTH);

        setLayout(new BorderLayout(5, 5));
        add(panelTop, BorderLayout.NORTH);
        add(createPanelContent(), BorderLayout.CENTER);
    }

    private JPanel getPanelKey(String data) {
        JPanel panelKey = new JPanel(new FlowLayout());
        int maxLength = 80;
        String truncatedData = data.length() > maxLength
                ? data.substring(0, maxLength) + "..."
                : data;
        JTextField text = new JTextField();
        text.setEditable(false);
        text.setText(truncatedData);
        text.setToolTipText(data);
        ButtonCustom btnLoad = new ButtonCustom(IconConfig.ICON_UPLOAD);
        btnLoad.addActionListener(e -> {
            try {
                String nameLayout = ControlTypeInput.getInstance().getNameLayoutInput();
                if (nameLayout == null) {
                    String temp = JOptionPane.showInputDialog("Please enter public your key");
                    if (rsaModel.isKey(temp))
                        text.setText(temp);
                } else if (nameLayout.equals(LayoutConfig.FILE_INPUT_LAYOUT)) {
                    DataAction.choiceFile(f -> {
                        String temp = DataAction.readFile((File) f);
                        if (rsaModel.isKey(temp))
                            text.setText(temp);
                    });
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Error input key");
            }
        });
        ButtonCustom btnDow = new ButtonCustom(IconConfig.ICON_SAVE);
        btnDow.addActionListener(e -> DataAction.saveContentToFile(data));
        panelKey.add(text);
        panelKey.add(btnLoad);
        panelKey.add(btnDow);
        return panelKey;
    }
}

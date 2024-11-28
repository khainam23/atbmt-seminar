package view.algorithm.hash;

import config.IconConfig;
import model.algorithm.hash.AAlgorithmHash;
import view.algorithm.APanel;
import view.custom.ButtonCustom;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class ALayoutHash {
    public static JPanel init(AAlgorithmHash hash, JPanel panelCenter) {
        JPanel panel = new JPanel();
        JPanel panelTop = new JPanel();

        SpinnerNumberModel modelSpinner = new SpinnerNumberModel(hash.getSize(), 1, 100, 1);
        JSpinner spinner = new JSpinner(modelSpinner);
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        NumberFormatter formatter = new NumberFormatter(NumberFormat.getIntegerInstance());
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);
        editor.getTextField().setFormatterFactory(new DefaultFormatterFactory(formatter));
        spinner.addChangeListener(e -> {
            JSpinner source = (JSpinner) e.getSource(); // Lấy đối tượng JSpinner
            Integer value = (Integer) source.getValue(); // Lấy giá trị từ spinner
            hash.setSize(value); // Cập nhật giá trị mới vào đối tượng hash
        });

        JPanel panelHash = new JPanel(new FlowLayout());
        JLabel label = new JLabel(hash.getKey());
        ButtonCustom btnReload = new ButtonCustom(IconConfig.ICON_RELOAD);
        btnReload.addActionListener(e -> {
            String temp = hash.generateKey();
            label.setText(temp);
        });
        panelHash.add(label);
        panelHash.add(btnReload);

        panelTop.add(spinner);
        panelTop.add(panelHash);

        panel.setLayout(new BorderLayout(5, 5));
        panel.add(panelTop, BorderLayout.NORTH);
        panel.add(panelCenter, BorderLayout.CENTER);
        return panel;
    }
}

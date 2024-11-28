package view;

import config.DimensionConfig;
import config.IconConfig;
import config.LayoutConfig;
import controller.DataAction;
import view.custom.ScrollPaneWin11;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PanelRight extends JPanel {
    private static PanelRight panelRight;
    private final CardLayout cardLayout = new CardLayout();
    private JTextArea description;
    private JButton btnSave;

    private PanelRight() {
        init();
    }

    public static PanelRight getInstance() {
        return panelRight == null ? panelRight = new PanelRight() : panelRight;
    }

    private void init() {
        setLayout(cardLayout);
        add(defaultLayout(), LayoutConfig.DEFAULT_LAYOUT);
        setPreferredSize(DimensionConfig.SIZE_RIGHT);
    }

    private JPanel defaultLayout() {
        ScrollPaneWin11 scrollPane = new ScrollPaneWin11();
        description = new JTextArea();
        description.setFont(new Font("Arial", Font.PLAIN, 16));
        description.setEditable(false);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        scrollPane.setViewportView(description);

        btnSave = new JButton(IconConfig.ICON_SAVE);
        btnSave.addActionListener(e -> DataAction.saveContentToFile(description.getText()));
        btnSave.setVisible(false);

        JPanel paneDefault = new JPanel(new BorderLayout());

        paneDefault.add(scrollPane, BorderLayout.CENTER);
        paneDefault.add(btnSave, BorderLayout.SOUTH);
        return paneDefault;
    }
    public void setDescription(String msg, boolean isShowSave) {
        description.setText(msg);
        btnSave.setVisible(isShowSave);
    }
}

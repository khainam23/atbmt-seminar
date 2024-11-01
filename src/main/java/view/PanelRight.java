package view;

import config.DimensionConfig;

import javax.swing.*;
import java.awt.*;

public class PanelRight extends JPanel {
    private static PanelRight panelRight;
    private PanelRight() {
        init();
    }
    public static PanelRight getInstance() {
        return panelRight == null ? panelRight = new PanelRight() : panelRight;
    }

    private void init() {
        JLabel text = new JLabel();
        setLayout(new FlowLayout(FlowLayout.LEFT));

        setPreferredSize(DimensionConfig.SIZE_RIGHT);
        add(text);
    }
}

package view;

import config.DimensionConfig;

import javax.swing.*;
import java.awt.*;

public class PanelFooter extends JPanel {
    private static PanelFooter panelFooter;
    private PanelFooter() {
        init();
    }
    public static PanelFooter getInstance() {
        return panelFooter == null ? panelFooter = new PanelFooter() : panelFooter;
    }

    private void init() {
        JLabel label = new JLabel("Empty");
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(label);

        setPreferredSize(DimensionConfig.SIZE_FOOTER);
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
    }
}

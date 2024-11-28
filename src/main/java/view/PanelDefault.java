package view;

import javax.swing.*;
import java.awt.*;

public class PanelDefault extends JPanel {
    private static PanelDefault panelDefault;
    private PanelDefault() {init();}
    synchronized public static PanelDefault getInstance(){return panelDefault == null ? panelDefault = new PanelDefault() : panelDefault;}

    private void init() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Choose Algorithm", SwingConstants.CENTER);
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(50f);
        label.setFont(newFont);
        add(label, BorderLayout.CENTER);
    }
}

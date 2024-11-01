package view;

import javax.swing.*;
import java.awt.*;

public class PanelCenter extends JPanel {
    private static PanelCenter panelCenter;
    private final CardLayout cardLayout = new CardLayout();
    private final String DEFAULT_LAYOUT = "default layout";
    private PanelCenter() {
        init();
    }

    public static PanelCenter getInstance() {
        return panelCenter == null ? panelCenter = new PanelCenter() : panelCenter;
    }

    private void init() {
        // Default Layout
        setLayout(cardLayout);
        add(defaultLayout(), DEFAULT_LAYOUT);
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.GRAY));
    }

    private JPanel defaultLayout() {
        JPanel panelDefault = new JPanel();
        panelDefault.setLayout(new BorderLayout());
        JLabel label = new JLabel("Choose Algorithm", SwingConstants.CENTER);
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(50f);
        label.setFont(newFont);
        panelDefault.add(label, BorderLayout.CENTER);
        return panelDefault;
    }
}

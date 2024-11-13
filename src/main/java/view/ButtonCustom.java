package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonCustom extends JButton {
    public ButtonCustom(Icon icon) {
        super(icon);
        init();
    }

    private void init() {
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            private Color oldColor;
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
                JButton btn = (JButton) e.getSource();
                oldColor = btn.getBackground();
                btn.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setContentAreaFilled(false);
                JButton btn = (JButton) e.getSource();
                btn.setBackground(oldColor);
            }
        });
    }
}

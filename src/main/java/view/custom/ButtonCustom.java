package view.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonCustom extends JButton {
    private final Color color;
    private final boolean isTranparent;
    public ButtonCustom(Icon icon) {
        super(icon);
        color = Color.LIGHT_GRAY;
        isTranparent = true;
        init();
    }
    public ButtonCustom(String msg) {
        super(msg);
        color = Color.CYAN;
        isTranparent = false;
        init();
    }
    private void init() {
        setContentAreaFilled(false);
        setOpaque(!isTranparent);
        setBorderPainted(!isTranparent);
        setForeground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            private Color oldColor;
            @Override
            public void mouseEntered(MouseEvent e) {
                setContentAreaFilled(true);
                JButton btn = (JButton) e.getSource();
                oldColor = btn.getBackground();
                btn.setBackground(color);
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

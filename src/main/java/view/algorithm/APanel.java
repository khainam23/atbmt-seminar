package view.algorithm;

import view.custom.ButtonCustom;
import view.custom.ScrollPaneWin11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class APanel extends JPanel {
    protected JTextArea textArea;

    protected APanel() {
        init();
    }

    protected abstract void init();

    public void setContent(String content) {
        if (textArea != null) {
            if(content == null || content.isBlank()) {
                textArea.setEditable(true);
            } else {
                textArea.setText(content);
            }
        }
    }

    protected final JPanel createPanelContent() {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setFont(new Font("Arial", Font.PLAIN, 18));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
        }
        textArea.setText("");
        final JPanel panelContent = new JPanel(new BorderLayout());
        ScrollPaneWin11 scrollPane = new ScrollPaneWin11();
        scrollPane.setViewportView(textArea);
        panelContent.add(scrollPane, BorderLayout.CENTER);
        return panelContent;
    }

    protected ButtonCustom getBtn(String text, ActionListener ac) {
        ButtonCustom btn = new ButtonCustom(text);
        btn.setForeground(Color.BLACK);
        btn.addActionListener(ac);
        return btn;
    }
}

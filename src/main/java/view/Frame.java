package view;

import javax.swing.*;
import java.awt.*;

import static config.DimensionConfig.*;

public class Frame extends JFrame {

    public Frame() {
        init();
        startUI();
    }

    private void startUI() {
        add(mainLayout());
    }

    private void init() {
        setTitle("ATBMHTTT");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(SIZE_LIMIT_FRAME);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JPanel mainLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Head
        PanelHead panelHead = PanelHead.getInstance();

        // Left
        JPanel panelLeft = PanelLeft.getInstance();

        // Center
        JPanel panelCenter = PanelCenter.getInstance();

        // Right
        JPanel panelRight = PanelRight.getInstance();

        // Footer
        JPanel panelFooter = PanelFooter.getInstance();

        mainPanel.add(panelHead, BorderLayout.NORTH);
        mainPanel.add(panelLeft, BorderLayout.WEST);
        mainPanel.add(panelCenter, BorderLayout.CENTER);
        mainPanel.add(panelRight, BorderLayout.EAST);
        mainPanel.add(panelFooter, BorderLayout.SOUTH);

        return mainPanel;
    }

    public void start() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

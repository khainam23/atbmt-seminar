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
//        mainPanel.add(PanelHead.getInstance(), BorderLayout.NORTH);
        mainPanel.add(PanelLeft.getInstance(), BorderLayout.WEST);
        mainPanel.add(PanelCenter.getInstance(), BorderLayout.CENTER);
        mainPanel.add(PanelRight.getInstance(), BorderLayout.EAST);
//        mainPanel.add(PanelFooter.getInstance(), BorderLayout.SOUTH);
        return mainPanel;
    }

    public void start() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

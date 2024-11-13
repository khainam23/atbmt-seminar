package view;

import javax.swing.*;
import java.awt.*;

import static config.DimensionConfig.SIZE_HEAD;
import static config.IconConfig.*;

public class PanelHead extends JPanel {
    private static PanelHead panelHead;
    private PanelHead() {
        init();
    }

    public static PanelHead getInstance() {
        return panelHead == null ? panelHead = new PanelHead() : panelHead;
    }

    private void init() {
        setPreferredSize(SIZE_HEAD);

        // Setup top
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelLabel = new JPanel();
        JLabel labelTitle = new JLabel("Home");
        labelTitle.setHorizontalAlignment(SwingConstants.LEFT);
        labelTitle.setOpaque(true);
        panelLabel.setBackground(Color.GRAY);
        panelLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 80));
        labelTitle.setBackground(panelLabel.getBackground());
        panelLabel.add(labelTitle);
        panelTop.add(panelLabel);
        panelTop.setBackground(Color.DARK_GRAY);

        // Setup bottom
        JPanel panelBottom = new JPanel(new BorderLayout(0, 0));
        panelBottom.setBackground(Color.DARK_GRAY);
        JPanel panelOption = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        ButtonCustom btnBack = new ButtonCustom(ICON_BACK);
        ButtonCustom btnNext = new ButtonCustom(ICON_NEXT);
        ButtonCustom btnReload = new ButtonCustom(ICON_RELOAD);
        panelOption.add(btnBack);
        panelOption.add(btnNext);
        panelOption.add(btnReload);
        JPanel panelAddress = new JPanel();
        JTextField fieldAddress = new JTextField(100);
        fieldAddress.setEditable(false);
        panelAddress.add(fieldAddress);
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 5));
        JTextField fieldSearch = new JTextField(20);
        ButtonCustom btnSearch = new ButtonCustom(ICON_SEARCH);
        panelSearch.add(fieldSearch);
        panelSearch.add(btnSearch);
        panelBottom.add(panelOption, BorderLayout.WEST);
        panelBottom.add(panelAddress, BorderLayout.CENTER);
        panelBottom.add(panelSearch, BorderLayout.EAST);
        panelOption.setBackground(Color.DARK_GRAY);
        panelAddress.setBackground(Color.DARK_GRAY);
        panelSearch.setBackground(Color.DARK_GRAY);

        setLayout(new GridLayout(2, 1));
        add(panelTop);
        add(panelBottom);
    }
}

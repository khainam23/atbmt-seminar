package view;

import config.IconConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import static config.DimensionConfig.SIZE_LEFT;

public class PanelLeft extends JPanel {
    private static PanelLeft panelLeft;
    private JPanel panelList;

    private PanelLeft() {
        init();
    }

    public static PanelLeft getInstance() {
        return panelLeft == null ? panelLeft = new PanelLeft() : panelLeft;
    }

    private void init() {
        setPreferredSize(SIZE_LEFT);

        ScrollPaneWin11 scrollPane = new ScrollPaneWin11();
        scrollPane.setBorder(null);
        panelList = new JPanel();
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        scrollPane.setViewportView(panelList);
        insertItemToList();
        scrollPane.setPreferredSize(new Dimension((int) (SIZE_LEFT.getWidth()), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 120));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

    }

    public void addItem(ImageIcon icon, String content) {
        final FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
        if (icon == null) {
            addItem(IconConfig.ICON_JAVA, content);
            return;
        }

        final class MouseAdapterCustom extends MouseAdapter {
            private final JPanel panel;
            private final Color oldColor;

            public MouseAdapterCustom(JPanel panel) {
                this.panel = panel;
                oldColor = panel.getBackground();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(oldColor);
            }
        }

        JPanel panelItem = new JPanel();
        panelItem.setLayout(flowLayout);
        JLabel labelIcon = new JLabel(icon);
        JLabel labelName = new JLabel(content, SwingConstants.LEFT);
        final MouseAdapterCustom mouseAdapterCustom = new MouseAdapterCustom(panelItem);
        panelItem.addMouseListener(mouseAdapterCustom);

        panelItem.add(labelIcon);
        panelItem.add(labelName);
        panelList.add(panelItem);
    }

    public void insertItemToList() {
        class Item {
            final ImageIcon img;
            final String content;

            public Item(ImageIcon img, String content) {
                this.img = img;
                this.content = content;
            }
        }

        List<Item> items = Arrays.asList(
                new Item(null, "ShiftCipher"),
                new Item(null, "SubstitutionCipher")
        );

        items.forEach(item -> addItem(item.img, item.content));
    }
}

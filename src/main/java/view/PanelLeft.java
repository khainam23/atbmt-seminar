package view;

import config.IconConfig;
import config.LayoutConfig;
import controller.FactoryLayoutAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.util.List;
import java.util.*;

import static config.DimensionConfig.SIZE_LEFT;

/**
 * Note: Hiển thị layout cho phía bên trái của giao diện chính.
 * Tự động map tên của các phần tử trong LayoutConfig.
 */
public class PanelLeft extends JPanel {
    private static PanelLeft panelLeft;
    private JPanel panelList;
    private Set<String> layoutIgnore;

    private PanelLeft() {
        init();
    }

    public static PanelLeft getInstance() {
        return panelLeft == null ? panelLeft = new PanelLeft() : panelLeft;
    }

    private void init() {
        layoutIgnore = new HashSet<>(
                Arrays.asList(
                        LayoutConfig.FILE_INPUT_LAYOUT,
                        LayoutConfig.DEFAULT_LAYOUT
                )
        );

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

        panelItem.addMouseListener(FactoryLayoutAction.getInstance());
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
                this.content = content.toUpperCase();
            }
        }

        List<Item> items = new ArrayList<>();
        Class<?> myLayoutConfig = LayoutConfig.class;
        Field[] fields = myLayoutConfig.getFields();
        for (Field field : fields) {
            try {
                String value = (String) field.get(null);
                if (!layoutIgnore.contains(value))
                    items.add(new Item(null, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        items.forEach(item -> addItem(item.img, item.content));
    }
}

package view;

import config.IconConfig;
import config.LayoutConfig;
import controller.FactoryLayoutAction;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * Note: Hiển thị các màn hình làm việc
 */
public class PanelCenter extends JPanel {
    private static PanelCenter panelCenter;
    private final CardLayout cardLayout = new CardLayout();
    private final FactoryLayoutAction factoryLayoutAction = FactoryLayoutAction.getInstance();
    private PanelCenter() {
        init();
    }
    public static PanelCenter getInstance() {
        return panelCenter == null ? panelCenter = new PanelCenter() : panelCenter;
    }
    private void init() {
        setLayout(cardLayout);
        setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.GRAY));
        add(PanelDefault.getInstance(), LayoutConfig.DEFAULT_LAYOUT);
        add(PanelInputFile.getInstance(), LayoutConfig.FILE_INPUT_LAYOUT);
        factoryLayoutAction.getMapLayout().forEach((k, v) -> add(v, k));
    }
    // Di chuyển giữa các màn hình
    public void moveLayout(String name) {
        cardLayout.show(this, name);
    }
}
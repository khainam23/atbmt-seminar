package view;

import config.IconConfig;
import controller.DataAction;
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

public class PanelInputFile extends JPanel {
    private static PanelInputFile pif;
    private final FactoryLayoutAction factoryLayoutAction = FactoryLayoutAction.getInstance();
    private PanelInputFile() {init();}
    public static PanelInputFile getInstance() {
        return pif == null ? pif = new PanelInputFile() : pif;
    }
    private void init() {
        setBorder(BorderFactory.createDashedBorder(Color.BLACK, 5f, 5f, 5f, true));
        setLayout(new BorderLayout());
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DataAction.choiceFile(f -> factoryLayoutAction.readAndSendContent((File) f));
            }
        });

        new DropTarget(null, new DropTargetAdapter() {
            @Override
            public void dragOver(DropTargetDragEvent dtde) {
                // Xử lý khi kéo vào JPanel
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
            }
            @Override
            public void dropActionChanged(DropTargetDragEvent dtde) {
                // Thay đổi hành động thả
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
            }
            @Override
            public void drop(DropTargetDropEvent event) {
                // Có cho phép kéo thả
                event.acceptDrop(DnDConstants.ACTION_COPY);
                // Get the transfer which can provide the dropped item data
                Transferable transferable = event.getTransferable();
                // Get the data formats of the dropped item
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    try {
                        if (flavor.isFlavorJavaFileListType()) {
                            java.util.List<File> files = (List) transferable.getTransferData(flavor);
                            for (File file : files) {
                                String path = file.getPath();
                                String extension = path.substring(path.lastIndexOf('.') + 1);
                                if (extension.equals("txt")) {
                                    factoryLayoutAction.readAndSendContent(file);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.dropComplete(true);
            }
        });

        JLabel dropFile = new JLabel();
        dropFile.setHorizontalAlignment(SwingConstants.CENTER);
        dropFile.setIcon(IconConfig.setResizeIcon("edit.png", 100, 100));
        add(dropFile, BorderLayout.CENTER);
    }
}

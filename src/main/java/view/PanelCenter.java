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
        add(defaultLayout(), LayoutConfig.DEFAULT_LAYOUT);
        add(fileLayout(), LayoutConfig.FILE_INPUT_LAYOUT);
        factoryLayoutAction.getMapLayout().forEach(
                (k, v) -> {
                    add(v, k);
                }
        );
    }
    // Một màn hình mặc định khi mới vào ứng dụng
    private JPanel defaultLayout() {
        JPanel panelDefault = new JPanel();
        panelDefault.setLayout(new BorderLayout());
        JLabel label = new JLabel("Choose Algorithm", SwingConstants.CENTER);
        Font currentFont = label.getFont();
        Font newFont = currentFont.deriveFont(50f);
        label.setFont(newFont);
        panelDefault.add(label, BorderLayout.CENTER);
        return panelDefault;
    }
    // Màn hình lựa chọn file
    private JPanel fileLayout() {
        JPanel panelInputFile = new JPanel();
        panelInputFile.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 5f, 5f, 5f, true));
        panelInputFile.setLayout(new BorderLayout());
        panelInputFile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelInputFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Truyền vào thư mục đang sử dụng để truy cập nhanh hơn
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

                fileChooser.setDialogTitle("Open File");
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

                // Tạo hộp thư mục lựa chọn
                int x = fileChooser.showOpenDialog(panelInputFile);

                // Kiểm tra xem có cho phép khởi tạo
                if (x == JFileChooser.APPROVE_OPTION) {
                    // File đã lựa chọn
                    File f = fileChooser.getSelectedFile();

                    factoryLayoutAction.readAndSendContent(f);
                }
            }
        });

        new DropTarget(panelInputFile, new DropTargetAdapter() {
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

                // Loop through the flavors
                for (DataFlavor flavor : flavors) {

                    try {
                        // If the drop items are files
                        if (flavor.isFlavorJavaFileListType()) {

                            // Get all of the dropped files
                            java.util.List<File> files = (List) transferable.getTransferData(flavor);
                            // Loop them through
                            for (File file : files) {
                                String path = file.getPath();
                                String extension = path.substring(path.lastIndexOf('.') + 1);
                                if (extension.equals("txt")) {
                                    factoryLayoutAction.readAndSendContent(file);
                                }
                            }
                        }

                    } catch (Exception e) {

                        // Print out the error stack
                        e.printStackTrace();

                    }
                }

                // Inform that the drop is complete
                event.dropComplete(true);
            }
        });

        JLabel dropFile = new JLabel();
        dropFile.setHorizontalAlignment(SwingConstants.CENTER);
        dropFile.setIcon(IconConfig.setResizeIcon("edit.png", 100, 100));
        panelInputFile.add(dropFile, BorderLayout.CENTER);

        return panelInputFile;
    }
    // Di chuyển giữa các màn hình
    public void moveLayout(String name) {
        cardLayout.show(this, name);
    }
}

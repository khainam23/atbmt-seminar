package view.algorithm.asymmetric.sign;

import controller.DataAction;
import model.algorithm.asymmetric.sign.Sign;
import view.algorithm.APanel;
import view.custom.ButtonCustom;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class PanelSign extends APanel {
    private static PanelSign panelSign;
    private Sign signModel;

    public static JPanel getInstance() {
        return panelSign == null ? panelSign = new PanelSign() : panelSign;
    }

    @Override
    protected void init() {
        signModel = new Sign();

        // Tạo panel chứa các nút với FlowLayout (đã căn giữa nội bộ)
        JPanel panelOptions = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonCustom btnDowAllKey = getBtn("Download key", e -> DataAction.saveContentToFile(signModel.getKey()));
        ButtonCustom btnCheckSign = getBtn("Check Sign", e -> createDialog());
        ButtonCustom btnGenSign = getBtn("Sign", e ->
                DataAction.choiceFile(file ->
                        DataAction.saveContentToFile(signModel.encrypt(((File) file).getPath()))
                )
        );
        panelOptions.add(btnGenSign);
        panelOptions.add(btnDowAllKey);
        panelOptions.add(btnCheckSign);

        // Tạo container cha căn giữa panelOptions
        JPanel parentPanel = new JPanel(new GridBagLayout());
        parentPanel.add(panelOptions); // Căn giữa panelOptions trong GridBagLayout

        // Đặt parentPanel vào JFrame
        setLayout(new BorderLayout());
        add(parentPanel, BorderLayout.CENTER);
    }

    public void createDialog() {
       DataAction.choiceFile(file ->
           DataAction.choiceFile(sign -> {
               try {
                   JOptionPane.showMessageDialog(null, signModel.decrypt(DataAction.readFile((File) sign), ((File) file).getPath()));
               } catch (Exception e) {
                   JOptionPane.showMessageDialog(null, "Error valid sign.");
               }
           })
       );
    }
}

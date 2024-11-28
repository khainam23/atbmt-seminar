package view.algorithm.hash;

import model.algorithm.hash.MD5;
import view.algorithm.APanel;

import java.awt.*;

public class PanelMD5 extends APanel {
    private static PanelMD5 panelMD5;

    public static PanelMD5 getInstance() {
        return panelMD5 == null ? panelMD5 = new PanelMD5() : panelMD5;
    }

    @Override
    protected void init() {
        MD5 md5Model = new MD5();
        setLayout(new GridLayout(1, 1));
        add(ALayoutHash.init(md5Model, createPanelContent()));
    }
}

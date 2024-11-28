package view.algorithm.hash;

import model.algorithm.hash.SHA;
import view.algorithm.APanel;
import view.algorithm.symmetric.basic.PanelSubstitution;

import java.awt.*;

public class PanelSHA extends APanel {
    private static PanelSHA panelSHA;
    public static PanelSHA getInstance() {
        return panelSHA == null ? panelSHA = new PanelSHA() : panelSHA;
    }
    @Override
    protected void init() {
        SHA shaModel = new SHA();
        setLayout(new GridLayout(1, 1));
        add(ALayoutHash.init(shaModel, createPanelContent()));
    }
}

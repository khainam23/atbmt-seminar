package config;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public interface IconConfig {
    int HEIGHT = 20;
    int WIDTH = 20;
    ImageIcon ICON_BACK = setResizeIcon("back.png");
    ImageIcon ICON_NEXT = setResizeIcon("next.png");
    ImageIcon ICON_SEARCH = setResizeIcon("search.png");
    ImageIcon ICON_RELOAD = setResizeIcon("reload.png");
    ImageIcon ICON_JAVA = setResizeIcon("java.png");

    static ImageIcon setResizeIcon(String path) {
        path = "/" + path.trim();
        return new ImageIcon(
                new ImageIcon(
                        Objects.requireNonNull(
                                IconConfig.class.getResource(path)
                        )
                ).getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH)
        );
    }
}

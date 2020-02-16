package net.shaidullin.code_maker.ui;

import net.shaidullin.code_maker.core.node.Node;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class IconManager {
    private final static IconManager instance = new IconManager();
    private static final Map<String, ImageIcon> cachedIcons = new HashMap<>();

    public static IconManager getInstance() {
        return instance;
    }

    @Nullable
    public ImageIcon getIcon(Node node) {
        String pathToIcon = node.getIconPath();
        if (StringUtils.isBlank(pathToIcon)) {
            return null;
        }

        return getIcon(pathToIcon);
    }


    private ImageIcon getIcon(String path) {
        if (cachedIcons.containsKey(path)) {
            return cachedIcons.get(path);
        }

        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            cachedIcons.put(path, icon);

            return icon;
        }

        return null;
    }
}

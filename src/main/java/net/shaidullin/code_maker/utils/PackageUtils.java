package net.shaidullin.code_maker.utils;

import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageUtils {

    public static String assembleFqnClassName(Node node) {
        List<String> parts = new ArrayList<>();
        if (!(node instanceof ModuleNode)) {
            parts.add(node.getMetadata().getSystemName());
        }

        while (node.getParent() != null) {
            node = node.getParent();
            if (!(node instanceof ModuleNode)) {
                parts.add(node.getMetadata().getSystemName());
            }
        }
        if (node instanceof ModuleNode) {
            parts.add(((ModuleMetadata) node.getMetadata()).getFqnPackage());
        }

        Collections.reverse(parts);
        return String.join(".", parts);
    }
}

package net.shaidullin.code_maker.utils;

import net.shaidullin.code_maker.core.node.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageUtils {
    public static String assembleFqnClassName(Node node) {
        return assembleFqnClassName(node, false);
    }

    public static String assembleFqnClassName(Node node, boolean withoutElement) {
        List<String> parts = new ArrayList<>();

        if (node instanceof LeafNode) {
            parts.add(node.getSystemName());
            node = node.getParent();
        }

        if (node instanceof PackageNode) {
            ElementNode elementNode = ((PackageNode) node).getParent();
            if (!withoutElement) {
                parts.add(elementNode.getSystemName());
            }

            parts.add(node.getSystemName());
            node = elementNode.getParent();
        }

        if (node instanceof ElementNode) {
            throw new UnsupportedOperationException("Use assembleFqnClassName(PackageNode) instead");
        }

        if (node instanceof ModuleNode) {
            parts.add(((ModuleNode) node).getMetadata().getFqnPackage());
        }

        Collections.reverse(parts);
        return String.join(".", parts);
    }

    public static String getLastPart(String name) {
        String[] fqn = name.split("\\.");

        if (fqn.length == 0) {
            return null;
        }

        return fqn[fqn.length - 1];
    }
}

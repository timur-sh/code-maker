package net.shaidullin.code_maker.core.node.utils;

import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.IntegrationObject;

public class LeafNodeUtils {
    public final static DtoNode UNDEFINED_CLASS = buildDtoNode(
        new PackageNode(
            "UNDEFINED",
            new ElementNode(),
            null
        ),
        "UNDEFINED",
        null
    );

    public static DtoNode buildDtoNode(PackageNode parent, String systemName,
                                       IntegrationObject integrationObject) {
        DtoNode node = new DtoNode();
        node.setParent(parent);
        node.setSystemName(systemName);
        node.setIntegrationObject(integrationObject);

        return node;
    }
}

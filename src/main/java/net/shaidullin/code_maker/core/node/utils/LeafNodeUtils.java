package net.shaidullin.code_maker.core.node.utils;

import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.IntegrationElement;

public class LeafNodeUtils {
    public final static DtoNode UNDEFINED_CLASS = buildDtoNode(
        new PackageNode(
            "UNDEFINED",
            new ElementNode(),
            null
        ),
        "UNDEFINED",
        null,
        null
    );

    public static DtoNode buildDtoNode(PackageNode parent, String systemName,
                                       IntegrationElement integrationObject, DtoMetadata metadata) {
        DtoNode node = new DtoNode();
        node.setParent(parent);
        node.setSystemName(systemName);
        node.setIntegrationElement(integrationObject);
        node.setMetadata(metadata);

        return node;
    }
}

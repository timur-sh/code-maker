package net.shaidullin.code_maker.core;

import com.intellij.util.containers.ArrayListSet;
import net.shaidullin.code_maker.core.metadata.PackageMetadata;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class NodeTestUtils {
    public static final String AUTH_PACKAGE = "auth";

    public static final String AUTHORIZATION_LEAF_NAME = "Authorization";
    public static final String AUTHENTICATION_LEAF_NAME = "Authentication";

    /**
     * Assemble package node and its metadata, than persist it
     *
     * @param elementNode
     * @return
     */
    public static PackageNode addPackage(ElementNode elementNode) {
        PackageMetadata metadata = new PackageMetadata();
        metadata.setDescription("Auth package");
        metadata.setSystemName(AUTH_PACKAGE);
        metadata.setUuid(UUID.randomUUID());

        PackageNode packageNode = new PackageNode();
        packageNode.setSystemName(metadata.getSystemName());
        packageNode.setIntegrationElement(Objects.requireNonNull(elementNode.getIntegrationElement()));
        packageNode.setParent(elementNode);
        packageNode.setMetadata(metadata);

        File file = new File(
            FileUtils.buildPathToMetadata(elementNode),
            packageNode.getSystemName());
        file.mkdirs();
        NodeUtils.writeMetadata(packageNode, metadata, true);

        return packageNode;
    }

    public static LeafNode addLeaf(String name, PackageNode packageNode) {
        DtoMetadata metadata = new DtoMetadata();
        metadata.setCachable(false);
        metadata.setCacheKeyTypeUID(null);
        metadata.setFields(new ArrayListSet<>());
        metadata.setGeneric(false);
        metadata.setGenericAlias(null);
        metadata.setParentUID(null);
        metadata.setDescription(name);
        metadata.setSystemName(name);
        metadata.setUuid(UUID.randomUUID());

        DtoNode dtoNode = new DtoNode();
        dtoNode.setIntegrationElement(packageNode.getIntegrationElement());
        dtoNode.setMetadata(metadata);
        dtoNode.setParent(packageNode);
        dtoNode.setSystemName(name);

        NodeUtils.writeLeafMetadata(dtoNode, metadata);
        return dtoNode;
    }

}

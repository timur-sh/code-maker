package net.shaidullin.code_maker.core;

import com.intellij.util.containers.ArrayListSet;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.utils.NodeUtils;

import java.util.UUID;

public class NodeTestUtils {
    public static final String AUTH_PACKAGE = "auth";

    public static final String AUTHORIZATION_LEAF_NAME = "Authorization";
    public static final String AUTHENTICATION_LEAF_NAME = "Authentication";

    public static LeafNode addLeaf(String name, PackageNode packageNode) {
        return addLeaf(name, UUID.randomUUID(), packageNode);
    }

    public static LeafNode addLeaf(String name, UUID uuid, PackageNode packageNode) {
        return addLeaf(name, uuid, packageNode, false, null, null);
    }

    public static LeafNode addLeaf(String name, UUID uuid, PackageNode packageNode,
                                   boolean generic, String typeParameter, UUID typeArgument) {
        return addLeaf(name, uuid, packageNode, generic, typeParameter, typeArgument, null);
    }

    public static LeafNode addLeaf(String name, UUID uuid, PackageNode packageNode,
                                   boolean generic, String typeParameter, UUID typeArgument,
                                   UUID parentUID) {
        DtoMetadata metadata = new DtoMetadata();
        metadata.setCachable(false);
        metadata.setCacheKeyTypeUID(null);
        metadata.setFields(new ArrayListSet<>());
        metadata.setGeneric(generic);
        metadata.setTypeParameter(typeParameter);
        metadata.setTypeArgumentUID(typeArgument);
        metadata.setParentUID(parentUID);
        metadata.setDescription(name);
        metadata.setSystemName(name);
        metadata.setUuid(uuid);

        DtoNode dtoNode = new DtoNode();
        dtoNode.setIntegrationElement(packageNode.getIntegrationElement());
        dtoNode.setMetadata(metadata);
        dtoNode.setParent(packageNode);
        dtoNode.setSystemName(name);

        NodeUtils.writeLeafMetadata(dtoNode, metadata);
        return dtoNode;
    }

    public static FieldMetadata buildFieldMetadata(String name, UUID typeUID, boolean generic,
                                                   String typeParameter, UUID typeArgument,
                                                   boolean list) {
        FieldMetadata metadata = new FieldMetadata();
        metadata.setTypeArgumentUID(typeArgument);
        metadata.setTypeParameter(typeParameter);
        metadata.setGeneric(generic);

        metadata.setList(list);
        metadata.setNullable(false);
        metadata.setTypeUID(typeUID);
        metadata.setDescription(name);
        metadata.setSystemName(name);
        metadata.setUuid(UUID.randomUUID());

        return metadata;
    }
}

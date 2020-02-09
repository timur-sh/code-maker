package net.shaidullin.code_maker.integration.impl.dto;

import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.core.type.DefaultMetadataFieldTypeImpl;
import net.shaidullin.code_maker.core.type.FieldTypeUtils;
import net.shaidullin.code_maker.core.type.MetadataFieldType;
import net.shaidullin.code_maker.integration.AbstractIntegrationObject;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.utils.JsonUtils;
import net.shaidullin.code_maker.utils.PackageUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DtoIntegrationObject extends AbstractIntegrationObject<PackageNode, DtoMetadata> {
    private static final String NAME = "Dto";
    private static final String FOLDER = "domain";
    private static final String UID = "2427bcb4-3822-48ff-94a7-6d9b5615a969";

    @Override
    public String getUID() {
        return UID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getFolder() {
        return FOLDER;
    }

    @Override
    public void generate(PackageNode packageNode) {
        //todo implement
    }

    @Override
    public DtoNode buildLeaf(String systemName, FileInputStream inputStream, PackageNode packageNode) {
        DtoMetadata dtoMetadata = JsonUtils.readValue(inputStream, DtoMetadata.class);
        return LeafNodeUtils.buildDtoNode(packageNode, systemName, this, dtoMetadata);
    }

    @Override
    public List<Class<IntegrationObject>> declareDependencies() {
        return new ArrayList<>();
    }

}

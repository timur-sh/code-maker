package net.shaidullin.code_maker.dto;

import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.AbstractIntegrationObject;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.utils.JsonUtils;

import java.io.FileInputStream;
import java.util.List;

public class DtoIntegrationObject extends AbstractIntegrationObject<PackageNode, DtoMetadata, DtoFieldTypeImpl> {
    private static final String NAME = "Dto";
    private static final String FOLDER = "domain";

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

    }

    @Override
    public DtoNode buildLeaf(String systemName, FileInputStream inputStream, PackageNode packageNode) {
        DtoMetadata dtoMetadata = JsonUtils.readValue(inputStream, DtoMetadata.class);
        return LeafNodeUtils.buildDtoNode(packageNode, systemName, this, dtoMetadata);
    }

    @Override
    public DtoFieldTypeImpl buildFieldType(DtoMetadata metadata) {
        return null;
    }

    @Override
    public List<Class<IntegrationObject>> declareDependencies() {
        return List.of();
    }

}

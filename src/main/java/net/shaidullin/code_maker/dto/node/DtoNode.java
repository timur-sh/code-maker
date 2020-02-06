package net.shaidullin.code_maker.dto.node;

import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

/**
 * DTO node is contained in package
 */
public class DtoNode implements LeafNode<PackageNode, DtoMetadata> {
    private String systemName;
    private PackageNode parent;
    private IntegrationObject integrationObject;
    private DtoMetadata metadata;

    public DtoNode() {
    }

    public DtoNode(String systemName, PackageNode parent, IntegrationObject integrationObject) {
        this.systemName = systemName;
        this.parent = parent;
        this.integrationObject = integrationObject;
    }

    @Override
    public String getSystemName() {
        return systemName;
    }

    @Override
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public PackageNode getParent() {
        return parent;
    }

    @Override
    public void setParent(PackageNode parent) {
        this.parent = parent;
    }

    @Override
    public DtoMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(DtoMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public IntegrationObject getIntegrationObject() {
        return integrationObject;
    }

    @Override
    public void setIntegrationObject(IntegrationObject integrationObject) {
        this.integrationObject = integrationObject;
    }

}

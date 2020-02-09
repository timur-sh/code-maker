package net.shaidullin.code_maker.integration.impl.dto.node;

import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.IntegrationElement;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;

/**
 * DTO node is contained in package
 */
public class DtoNode implements LeafNode<PackageNode, DtoMetadata> {
    private String systemName;
    private PackageNode parent;
    private IntegrationElement integrationElement;
    private DtoMetadata metadata;

    public DtoNode() {
    }

    public DtoNode(String systemName, PackageNode parent, IntegrationElement integrationElement) {
        this.systemName = systemName;
        this.parent = parent;
        this.integrationElement = integrationElement;
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
    public IntegrationElement getIntegrationElement() {
        return integrationElement;
    }

    @Override
    public void setIntegrationElement(IntegrationElement integrationElement) {
        this.integrationElement = integrationElement;
    }

}

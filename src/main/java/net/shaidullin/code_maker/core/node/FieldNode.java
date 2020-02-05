package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

public class FieldNode implements Node<LeafNode, FieldMetadata> {
    private String systemName;
    private LeafNode parent;
    private FieldMetadata metadata;

    public FieldNode() {
    }

    public FieldNode(String systemName, LeafNode parent) {
        this.systemName = systemName;
        this.parent = parent;
    }

    @Override
    public String getSystemName() {
        return this.systemName;
    }

    @Override
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public LeafNode getParent() {
        return this.parent;
    }

    @Override
    public void setParent(LeafNode parent) {
        this.parent = parent;
    }

    @Override
    public FieldMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(FieldMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public IntegrationObject getIntegrationObject() {
        throw new UnsupportedOperationException("LeafNode.getIntegrationObject() is not supported");
    }

    @Override
    public void setIntegrationObject(IntegrationObject integrationObject) {
        throw new UnsupportedOperationException("LeafNode.getIntegrationObject() is not supported");
    }
}

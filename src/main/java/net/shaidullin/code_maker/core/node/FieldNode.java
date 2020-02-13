package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;

import java.util.Objects;

public class FieldNode implements Node<LeafNode, FieldMetadata> {
    private LeafNode parent;
    private FieldMetadata metadata;

    public FieldNode(FieldMetadata fieldMetadata, LeafNode parent) {
        this.metadata = fieldMetadata;
        this.parent = parent;
    }

    @Override
    public String getSystemName() {
        return metadata.getSystemName();
    }

    @Override
    public void setSystemName(String systemName) {
        metadata.setSystemName(systemName);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldNode)) return false;
        FieldNode fieldNode = (FieldNode) o;
        return Objects.equals(parent, fieldNode.parent) &&
            Objects.equals(metadata, fieldNode.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, metadata);
    }
}

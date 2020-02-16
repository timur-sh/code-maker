package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FieldNode<N extends LeafNode, M extends FieldMetadata> implements Node<N, M> {
    private N parent;
    private M metadata;

    public FieldNode(M fieldMetadata, N parent) {
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
    public N getParent() {
        return this.parent;
    }

    @Override
    public void setParent(N parent) {
        this.parent = parent;
    }

    @Override
    public M getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(M metadata) {
        this.metadata = metadata;
    }

    @Nullable
    @Override
    public String getIconPath() {
        return "/icons/field.png";
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

    @Override
    public String toString() {
        return NameResolverManager.getInstance()
            .resolve(NameResolverManager.JAVA, this.getMetadata(), true);
    }
}

package net.shaidullin.code_maker.integration.impl.dto.node;

import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.integration.IntegrationElement;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
    public void removeNestedNode(Node nestedNode) {
        if (nestedNode instanceof FieldNode) {
            FieldNode fieldNode = (FieldNode) nestedNode;

            DtoMetadata metadata = this.metadata;
            metadata.getFields()
                .removeIf(f -> f.getUuid().equals(fieldNode.getMetadata().getUuid()));
        }
    }

    @Nullable
    @Override
    public String getIconPath() {
        return "/icons/class.png";
    }

    @Override
    public IntegrationElement getIntegrationElement() {
        return integrationElement;
    }

    @Override
    public void setIntegrationElement(IntegrationElement integrationElement) {
        this.integrationElement = integrationElement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DtoNode dtoNode = (DtoNode) o;
        return Objects.equals(systemName, dtoNode.systemName) &&
            Objects.equals(parent, dtoNode.parent) &&
            Objects.equals(integrationElement, dtoNode.integrationElement) &&
            Objects.equals(metadata, dtoNode.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, parent, integrationElement, metadata);
    }

    @Override
    public String toString() {
        return NameResolverManager.getInstance()
            .resolve(NameResolverManager.JAVA, this.getMetadata(), true);
    }
}

package net.shaidullin.code_maker.core.node;

import com.intellij.util.xmlb.annotations.Transient;
import net.shaidullin.code_maker.core.metadata.PackageMetadata;
import net.shaidullin.code_maker.integration.IntegrationElement;
import org.jetbrains.annotations.NotNull;

/**
 * Package node
 */
public class PackageNode implements IeNode<ElementNode, PackageMetadata>, Comparable<PackageNode> {
    private String systemName;
    private ElementNode parent;

    @Transient
    private IntegrationElement integrationObject;

    @Transient
    private PackageMetadata metadata;

    public PackageNode() {
    }

    public PackageNode(String systemName, ElementNode parent, IntegrationElement integrationObject) {
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
    public ElementNode getParent() {
        return parent;
    }

    @Override
    public void setParent(ElementNode parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(@NotNull PackageNode o) {
        return this.systemName.compareTo(o.systemName);
    }

    @Override
    public PackageMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(PackageMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public IntegrationElement getIntegrationElement() {
        return this.integrationObject;
    }

    @Override
    public void setIntegrationElement(IntegrationElement integrationElement) {
        this.integrationObject = integrationElement;
    }
}

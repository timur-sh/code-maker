package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.ElementMetadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

import java.util.Objects;

/**
 * Мета данные элементов
 */
public class ElementNode implements IoNode<ModuleNode, ElementMetadata> {
    private String systemName;
    private ModuleNode parent;
    private ElementMetadata metadata;
    private IntegrationObject integrationObject;

    public ElementNode() {
    }

    public ElementNode(String systemName, ModuleNode parent,
                       IntegrationObject integrationObject) {
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
    public ModuleNode getParent() {
        return parent;
    }

    @Override
    public void setParent(ModuleNode parent) {
        this.parent = parent;
    }

    @Override
    public ElementMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(ElementMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public IntegrationObject getIntegrationObject() {
        return this.integrationObject;
    }

    @Override
    public void setIntegrationObject(IntegrationObject integrationObject) {
        this.integrationObject = integrationObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ElementNode)) return false;
        ElementNode that = (ElementNode) o;
        return Objects.equals(systemName, that.systemName) &&
            Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, parent);
    }

    @Override
    public String toString() {
        return systemName;
    }
}

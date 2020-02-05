package net.shaidullin.code_maker.core.node;

import com.intellij.util.xmlb.annotations.Transient;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

import java.util.Objects;

/**
 * Module node
 */
public class ModuleNode implements Node<ModuleNode, ModuleMetadata> {
    private String systemName;
    private String path;

    @Transient
    private IntegrationObject integrationObject;

    @Transient
    private ModuleMetadata metadata;

    public ModuleNode() {
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
    public ModuleNode getParent() {
        return null;
    }

    @Override
    public void setParent(ModuleNode parent) {
        throw new UnsupportedOperationException("ModuleNode.getParent() not supported for ModuleNode");
    }

    @Override
    public ModuleMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public IntegrationObject getIntegrationObject() {
        return integrationObject;
    }

    @Override
    public void setIntegrationObject(IntegrationObject integrationObject) {
        this.integrationObject = integrationObject;
    }

    @Override
    public void setMetadata(ModuleMetadata metadata) {
        this.metadata = metadata;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleNode that = (ModuleNode) o;
        return Objects.equals(systemName, that.systemName) &&
            Objects.equals(path, that.path) &&
            Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, path, metadata);
    }
}

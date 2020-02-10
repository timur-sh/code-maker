package net.shaidullin.code_maker.core.node;

import com.intellij.util.xmlb.annotations.Transient;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;

import java.util.Objects;

/**
 * Module node
 */
public class ModuleNode implements Node<ModuleNode, ModuleMetadata> {
    private String systemName;
    private String rootMetadataPath;
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

    @Transient
    @Override
    public ModuleMetadata getMetadata() {
        return this.metadata;
    }

    @Transient
    @Override
    public void setMetadata(ModuleMetadata metadata) {
        this.metadata = metadata;
    }

    public String getRootMetadataPath() {
        return rootMetadataPath;
    }

    public void setRootMetadataPath(String rootMetadataPath) {
        this.rootMetadataPath = rootMetadataPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleNode that = (ModuleNode) o;
        return Objects.equals(systemName, that.systemName) &&
            Objects.equals(rootMetadataPath, that.rootMetadataPath) &&
            Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, rootMetadataPath, metadata);
    }

    @Override
    public String toString() {
        return this.systemName;
    }
}

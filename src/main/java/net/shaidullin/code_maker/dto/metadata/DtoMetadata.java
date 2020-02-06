package net.shaidullin.code_maker.dto.metadata;

import net.shaidullin.code_maker.core.metadata.AbstractMetadata;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;

import java.util.*;

public class DtoMetadata extends AbstractMetadata implements LeafMetadata {
    private UUID parentUID;
    private boolean cacheable;
    private UUID cacheKeyTypeUID;
    private boolean generic;
    private String genericAlias;
    private Set<FieldMetadata> fields = new TreeSet<>(Comparator.comparing(FieldMetadata::getSystemName));

    public DtoMetadata() {
    }

    public UUID getParentUID() {
        return parentUID;
    }

    public void setParentUID(UUID parentUID) {
        this.parentUID = parentUID;
    }

    public Set<FieldMetadata> getFields() {
        return fields;
    }

    public void setFields(Set<FieldMetadata> fields) {
        this.fields.clear();
        this.fields.addAll(fields);
    }

    public boolean isCachable() {
        return cacheable;
    }

    public void setCachable(boolean cachable) {
        this.cacheable = cachable;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public String getGenericAlias() {
        return genericAlias;
    }

    public void setGenericAlias(String genericAlias) {
        this.genericAlias = genericAlias;
    }

    public UUID getCacheKeyTypeUID() {
        return cacheKeyTypeUID;
    }

    public void setCacheKeyTypeUID(UUID cacheKeyTypeUID) {
        this.cacheKeyTypeUID = cacheKeyTypeUID;
    }

    @Override
    public List<String> getFqnPackageParts() {
        return null;
    }

    @Override
    public void setFqnPackageParts(List<String> fqnPackageParts) {

    }
}

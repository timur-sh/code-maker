package net.shaidullin.code_maker.integration.impl.dto.metadata;

import net.shaidullin.code_maker.core.metadata.AbstractGenericMetadata;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;

import java.util.*;

public class DtoMetadata extends AbstractGenericMetadata implements LeafMetadata {
    private UUID parentUID;
    private boolean cacheable;
    private UUID cacheKeyTypeUID;
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

    public UUID getCacheKeyTypeUID() {
        return cacheKeyTypeUID;
    }

    public void setCacheKeyTypeUID(UUID cacheKeyTypeUID) {
        this.cacheKeyTypeUID = cacheKeyTypeUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DtoMetadata)) return false;
        if (!super.equals(o)) return false;
        DtoMetadata metadata = (DtoMetadata) o;
        return cacheable == metadata.cacheable &&
            Objects.equals(parentUID, metadata.parentUID) &&
            Objects.equals(cacheKeyTypeUID, metadata.cacheKeyTypeUID) &&
            Objects.equals(fields, metadata.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parentUID, cacheable, cacheKeyTypeUID, fields);
    }
}

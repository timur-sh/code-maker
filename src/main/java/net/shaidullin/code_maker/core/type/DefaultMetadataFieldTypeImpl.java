package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import org.jetbrains.annotations.Nullable;

public class DefaultMetadataFieldTypeImpl<M extends LeafMetadata> extends AbstractFieldType implements MetadataFieldType<M> {
    private M metadata;

    public DefaultMetadataFieldTypeImpl() {
    }

    @Nullable
    @Override
    public M getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(M metadata) {
        this.metadata = metadata;
    }
}

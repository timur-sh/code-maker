package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface MetadataFieldType<M extends LeafMetadata> extends FieldType {
    @Nullable
    M getMetadata();

    void setMetadata(M metadata);
}

package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import org.jetbrains.annotations.Nullable;


public interface MetadataType<M extends LeafMetadata> extends Type {
    @Nullable
    M getMetadata();

    void setMetadata(M metadata);
}

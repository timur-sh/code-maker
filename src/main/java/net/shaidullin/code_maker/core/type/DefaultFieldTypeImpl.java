package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import org.jetbrains.annotations.Nullable;

public class DefaultFieldTypeImpl extends AbstractFieldType<LeafMetadata> {
    public DefaultFieldTypeImpl() {
    }

    @Nullable
    @Override
    public LeafMetadata getMetadata() {
        return null;
    }

    @Override
    public void setMetadata(LeafMetadata metadata) {

    }
}

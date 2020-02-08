package net.shaidullin.code_maker.integration.impl.dto;

import net.shaidullin.code_maker.core.type.AbstractFieldType;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import org.jetbrains.annotations.Nullable;

public class DtoFieldTypeImpl extends AbstractFieldType<DtoMetadata> {
    private DtoMetadata metadata;

    public DtoFieldTypeImpl() {
    }

    @Nullable
    @Override
    public DtoMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(DtoMetadata metadata) {
        this.metadata = metadata;
    }
}

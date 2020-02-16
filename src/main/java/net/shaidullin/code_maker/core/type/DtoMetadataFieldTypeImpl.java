package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import org.jetbrains.annotations.Nullable;

public class DtoMetadataFieldTypeImpl extends AbstractType implements MetadataType<DtoMetadata> {
    private DtoMetadata metadata;

    public DtoMetadataFieldTypeImpl() {
    }

    @Nullable
    @Override
    public DtoMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void setMetadata(DtoMetadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return NameResolverManager.getInstance()
            .resolve(NameResolverManager.JAVA, this, true);
    }
}

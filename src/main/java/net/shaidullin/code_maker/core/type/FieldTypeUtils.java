package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;

import java.util.UUID;

public class FieldTypeUtils {
    public static FieldType buildFieldType(UUID uuid, String name, boolean primitive, boolean requiredImport, String fqnName) {
        DefaultFieldTypeImpl type = new DefaultFieldTypeImpl();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setFqnName(fqnName);
        type.setRequiredImport(requiredImport);

        return type;
    }

    public static MetadataFieldType<LeafMetadata> buildMetadataFieldType(UUID uuid, String name, boolean primitive,
                                                                         boolean requiredImport, String fqnName, LeafMetadata metadata) {
        DefaultMetadataFieldTypeImpl<LeafMetadata> type = new DefaultMetadataFieldTypeImpl<>();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setFqnName(fqnName);
        type.setRequiredImport(requiredImport);
        type.setMetadata(metadata);

        return type;
    }
}

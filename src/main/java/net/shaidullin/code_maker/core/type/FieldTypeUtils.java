package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;

import java.util.UUID;

public class FieldTypeUtils {
    public static FieldType buildFieldType(UUID uuid, String name, boolean primitive, boolean requiredImport, String clz) {
        return buildFieldType(uuid, name, primitive, requiredImport, clz, null);
    }

    public static FieldType buildFieldType(UUID uuid, String name, boolean primitive, boolean requiredImport, String clz, DtoMetadata metadata) {
        DefaultFieldTypeImpl type = new DefaultFieldTypeImpl();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setClassName(clz);
        type.setRequiredImport(requiredImport);
        type.setMetadata(metadata);

        return type;
    }
}

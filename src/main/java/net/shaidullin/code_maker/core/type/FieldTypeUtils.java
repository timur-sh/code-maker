package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.dto.metadata.DtoMetadata;

import java.util.UUID;

public class FieldTypeUtils {
    public static FieldType buildFieldType(UUID uuid, String name, boolean primitive, boolean importPackage, String clz) {
        return buildFieldType(uuid, name, primitive, importPackage, clz, null);
    }

    public static FieldType buildFieldType(UUID uuid, String name, boolean primitive, boolean importPackage, String clz, DtoMetadata domainMetadata) {
        DefaultFieldTypeImpl type = new DefaultFieldTypeImpl();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setTypeClassName(clz);
        type.setImportPackage(importPackage);
        type.setDomainMetadata(domainMetadata);

        return type;
    }
}

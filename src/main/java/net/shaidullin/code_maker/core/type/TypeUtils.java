package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.metadata.GenericMetadata;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TypeUtils {
    public static boolean isGeneric(FieldMetadata fieldMetadata, ApplicationState state) {
        return isGeneric(fieldMetadata.getUuid(), state);
    }

    public static boolean isGeneric(UUID typeUid, ApplicationState state) {
        if (typeUid == null) {
            return false;
        }

        GenericMetadata genericMetadata = getGenericMetadata(typeUid, state);
        if (genericMetadata != null) {
            if (genericMetadata.isGeneric()) {
                return true;
            }

            return genericMetadata.getTypeArgumentUID() != null && isGeneric(genericMetadata.getTypeArgumentUID(), state);
        }

        return false;
    }

    /**
     * If fieldMetadata is represented
     *
     * @param fieldMetadata
     * @param state
     * @return
     */
    @Nullable
    public static GenericMetadata getGenericMetadata(FieldMetadata fieldMetadata, ApplicationState state) {
        return getGenericMetadata(fieldMetadata.getUuid(), state);
    }

    /**
     * If fieldMetadata is represented
     *
     * @param typeUid
     * @param state
     * @return
     */
    @Nullable
    public static GenericMetadata getGenericMetadata(UUID typeUid, ApplicationState state) {
        Type type = getType(typeUid, state);


        if (!(type instanceof MetadataType)) {
            return null;
        }

        MetadataType metadataType = ((MetadataType) type);
        if (!(metadataType.getMetadata() instanceof GenericMetadata)) {
            return null;
        }

        return ((GenericMetadata) metadataType.getMetadata());
    }

    public static Type getType(FieldMetadata fieldMetadata, ApplicationState state) {
        return getType(fieldMetadata.getUuid(), state);
    }

    public static Type getType(UUID typeUid, ApplicationState state) {
        return state.getTypeManager()
            .getTypeByUID(typeUid);
    }

    public static Type buildFieldType(UUID uuid, String name, boolean primitive, boolean requiredImport, String fqnName) {
        DefaultTypeImpl type = new DefaultTypeImpl();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setFqnName(fqnName);
        type.setRequiredImport(requiredImport);

        return type;
    }

    public static DtoMetadataFieldTypeImpl buildDtoMetadataFieldType(UUID uuid, String name, boolean primitive,
                                                                     boolean requiredImport, String fqnName, DtoMetadata metadata) {
        DtoMetadataFieldTypeImpl type = new DtoMetadataFieldTypeImpl();
        type.setName(name);
        type.setUuid(uuid);
        type.setPrimitive(primitive);
        type.setFqnName(fqnName);
        type.setRequiredImport(requiredImport);
        type.setMetadata(metadata);

        return type;
    }
}

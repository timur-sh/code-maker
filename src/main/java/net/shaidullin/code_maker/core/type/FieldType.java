package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.dto.metadata.DtoMetadata;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface FieldType {

    /**
     * @return UUID of type
     */
    UUID getUuid();

    /**
     * @param uuid UUID of type
     */
    void setUuid(UUID uuid);

    /**
     * @return type name
     */
    String getName();

    /**
     * @param name of type
     */
    void setName(String name);

    /**
     * @return t
     */
    String getTypeClassName();

    void setTypeClassName(String typeClassName);

    boolean isPrimitive();

    void setPrimitive(boolean primitive);

    boolean isImportPackage();

    void setImportPackage(boolean importPackage);

    @Nullable
    DtoMetadata getDomainMetadata();

    void setDomainMetadata(DtoMetadata domainMetadata);
}

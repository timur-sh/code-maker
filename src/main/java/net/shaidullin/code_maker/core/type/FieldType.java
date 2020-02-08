package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface FieldType<T extends LeafMetadata> {

    /**
     * @return UUID of type
     */
    UUID getUuid();

    /**
     * @param uuid UUID of type
     */
    void setUuid(UUID uuid);

    /**
     * Name is displayed in menu tree
     *
     * @return type name
     */
    String getName();

    /**
     * @param name of type
     */
    void setName(String name);

    /**
     * Class name is used in generation
     * @return
     */
    String getClassName();

    void setClassName(String typeClassName);

    boolean isPrimitive();

    void setPrimitive(boolean primitive);

    boolean isRequiredImport();

    void setRequiredImport(boolean requiredImport);

    @Nullable
    T getMetadata();

    void setMetadata(T metadata);
}

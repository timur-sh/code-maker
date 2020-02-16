package net.shaidullin.code_maker.core.type;

import java.util.UUID;

public interface Type {

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
     * Fully qualified name of type including a package
     *
     * @return
     */
    String getFqnName();

    void setFqnName(String fqnName);

    boolean isPrimitive();

    void setPrimitive(boolean primitive);

    boolean isRequiredImport();

    void setRequiredImport(boolean requiredImport);
}

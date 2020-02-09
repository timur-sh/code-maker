package net.shaidullin.code_maker.core.metadata;

import java.io.Serializable;
import java.util.UUID;

public interface Metadata extends Serializable {
    /**
     * @return system name of metadata item
     */
    String getSystemName();

    void setSystemName(String name);

    /**
     * @return description of metadata item
     */
    String getDescription();

    void setDescription(String description);

    /**
     * @return UUID of metadata item
     */
    UUID getUuid();

    void setUuid(UUID uuid);
}

package net.shaidullin.code_maker.core.metadata;

import java.io.Serializable;
import java.util.List;
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

    /**
     * @return FQN parts of package name to the metadata item
     */
    List<String> getFqnPackageParts();

    void setFqnPackageParts(List<String> fqnPackageParts);
}

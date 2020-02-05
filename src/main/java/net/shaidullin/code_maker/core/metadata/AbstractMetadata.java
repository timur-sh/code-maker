package net.shaidullin.code_maker.core.metadata;

import java.util.UUID;

/**
 * Default abstract implementation of Metadata
 */
public abstract class AbstractMetadata implements Metadata {
    protected UUID uuid;
    protected String systemName;
    protected String description;

    public AbstractMetadata() {
    }

    @Override
    public String getSystemName() {
        return this.systemName;
    }

    @Override
    public void setSystemName(String name) {
        this.systemName = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

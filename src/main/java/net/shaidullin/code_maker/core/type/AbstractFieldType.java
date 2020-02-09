package net.shaidullin.code_maker.core.type;

import java.util.Objects;
import java.util.UUID;

public abstract class AbstractFieldType implements FieldType {
    private UUID uuid;
    private String name;
    private String fqnName;
    private boolean primitive;
    private boolean requiredImport;

    public AbstractFieldType() {
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFqnName() {
        return fqnName;
    }

    @Override
    public void setFqnName(String fqnName) {
        this.fqnName = fqnName;
    }

    @Override
    public boolean isPrimitive() {
        return primitive;
    }

    @Override
    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    @Override
    public boolean isRequiredImport() {
        return requiredImport;
    }

    @Override
    public void setRequiredImport(boolean requiredImport) {
        this.requiredImport = requiredImport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFieldType)) return false;
        AbstractFieldType fieldType = (AbstractFieldType) o;
        return primitive == fieldType.primitive &&
            requiredImport == fieldType.requiredImport &&
            Objects.equals(uuid, fieldType.uuid) &&
            Objects.equals(name, fieldType.name) &&
            Objects.equals(fqnName, fieldType.fqnName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, fqnName, primitive, requiredImport);
    }
}

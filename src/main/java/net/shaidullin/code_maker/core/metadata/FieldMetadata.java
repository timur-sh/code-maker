package net.shaidullin.code_maker.core.metadata;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Мета данные полей
 */
public class FieldMetadata extends AbstractMetadata {
    protected UUID uuid;
    protected String systemName;
    protected String description;
    protected UUID typeUID;
    protected boolean list;
    protected boolean nullable;
    private boolean generic;
    private String genericAlias;
    protected UUID genericParameterUID;

    public FieldMetadata() {
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
    public List<String> getFqnPackageParts() {
        return null;
    }

    @Override
    public void setFqnPackageParts(List<String> fqnPackageParts) {

    }

    @Override
    public String getSystemName() {
        return systemName;
    }

    @Override
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isNullable() {
        return nullable;
    }

    public UUID getTypeUID() {
        return typeUID;
    }

    public void setTypeUID(UUID typeUID) {
        this.typeUID = typeUID;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public String getGenericAlias() {
        return genericAlias;
    }

    public void setGenericAlias(String genericAlias) {
        this.genericAlias = genericAlias;
    }

    public UUID getGenericParameterUID() {
        return genericParameterUID;
    }

    public void setGenericParameterUID(UUID genericParameterUID) {
        this.genericParameterUID = genericParameterUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldMetadata that = (FieldMetadata) o;
        return list == that.list &&
            nullable == that.nullable &&
            generic == that.generic &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(systemName, that.systemName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(typeUID, that.typeUID) &&
            Objects.equals(genericAlias, that.genericAlias) &&
            Objects.equals(genericParameterUID, that.genericParameterUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, systemName, description, typeUID, list, nullable, generic, genericAlias, genericParameterUID);
    }
}

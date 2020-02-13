package net.shaidullin.code_maker.core.metadata;

import java.util.Objects;
import java.util.UUID;

/**
 * Мета данные полей классов
 */
public class FieldMetadata extends AbstractMetadata {
    protected UUID typeUID;
    protected boolean list;
    protected boolean nullable;
    private boolean generic;
    private String genericAlias;
    protected UUID genericParameterUID;

    public FieldMetadata() {
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
        if (!(o instanceof FieldMetadata)) return false;
        if (!super.equals(o)) return false;
        FieldMetadata that = (FieldMetadata) o;
        return list == that.list &&
            nullable == that.nullable &&
            generic == that.generic &&
            Objects.equals(typeUID, that.typeUID) &&
            Objects.equals(genericAlias, that.genericAlias) &&
            Objects.equals(genericParameterUID, that.genericParameterUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeUID, list, nullable, generic, genericAlias, genericParameterUID);
    }
}

package net.shaidullin.code_maker.core.metadata;

import java.util.Objects;
import java.util.UUID;

/**
 * Мета данные полей классов
 */
public class FieldMetadata extends AbstractGenericMetadata {
    protected UUID typeUID;
    protected boolean list;
    protected boolean nullable;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldMetadata)) return false;
        if (!super.equals(o)) return false;
        FieldMetadata that = (FieldMetadata) o;
        return list == that.list &&
            nullable == that.nullable &&
            Objects.equals(typeUID, that.typeUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeUID, list, nullable);
    }
}

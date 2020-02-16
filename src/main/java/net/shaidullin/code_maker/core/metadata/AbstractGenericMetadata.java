package net.shaidullin.code_maker.core.metadata;

import java.util.Objects;
import java.util.UUID;

/**
 * Default abstract implementation of Metadata
 */
public abstract class AbstractGenericMetadata extends AbstractMetadata implements GenericMetadata {
    protected boolean generic;
    protected String typeParameter;
    protected UUID typeArgumentUID;


    @Override
    public boolean isGeneric() {
        return generic;
    }

    @Override
    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    @Override
    public String getTypeParameter() {
        return this.typeParameter;
    }

    @Override
    public void setTypeParameter(String parameter) {
        this.typeParameter = parameter;
    }

    @Override
    public UUID getTypeArgumentUID() {
        return this.typeArgumentUID;
    }

    @Override
    public void setTypeArgumentUID(UUID uuid) {
        this.typeArgumentUID = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractGenericMetadata)) return false;
        if (!super.equals(o)) return false;
        AbstractGenericMetadata that = (AbstractGenericMetadata) o;
        return generic == that.generic &&
            Objects.equals(typeParameter, that.typeParameter) &&
            Objects.equals(typeArgumentUID, that.typeArgumentUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), generic, typeParameter, typeArgumentUID);
    }
}
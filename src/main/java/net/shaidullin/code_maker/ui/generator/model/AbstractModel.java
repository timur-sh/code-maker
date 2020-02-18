package net.shaidullin.code_maker.ui.generator.model;

import java.util.Objects;

public abstract class AbstractModel {
    protected String systemName;

    public AbstractModel() {
    }

    public AbstractModel(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractModel)) return false;
        AbstractModel that = (AbstractModel) o;
        return Objects.equals(systemName, that.systemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName);
    }
}

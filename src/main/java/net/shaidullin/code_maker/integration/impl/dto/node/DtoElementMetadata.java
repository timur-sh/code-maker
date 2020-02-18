package net.shaidullin.code_maker.integration.impl.dto.node;

import net.shaidullin.code_maker.core.metadata.ElementMetadata;

import java.util.Objects;

public class DtoElementMetadata extends ElementMetadata {
    private String rootDtoJavaInterface;
    private String cacheInterface;

    public DtoElementMetadata() {
    }

    public String getRootDtoJavaInterface() {
        return rootDtoJavaInterface;
    }

    public void setRootDtoJavaInterface(String rootDtoJavaInterface) {
        this.rootDtoJavaInterface = rootDtoJavaInterface;
    }

    public String getCacheInterface() {
        return cacheInterface;
    }

    public void setCacheInterface(String cacheInterface) {
        this.cacheInterface = cacheInterface;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DtoElementMetadata)) return false;
        if (!super.equals(o)) return false;
        DtoElementMetadata that = (DtoElementMetadata) o;
        return Objects.equals(rootDtoJavaInterface, that.rootDtoJavaInterface) &&
            Objects.equals(cacheInterface, that.cacheInterface);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rootDtoJavaInterface, cacheInterface);
    }
}

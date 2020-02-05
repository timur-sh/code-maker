package net.shaidullin.code_maker.core.type;

import com.bavlex.generator.TypeScriptHelper;
import com.bavlex.generator.config.metadata.DomainMetadata;
import com.bavlex.generator.utils.BavlexStringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import net.shaidullin.code_maker.dto.metadata.DtoMetadata;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class DefaultFieldTypeImpl implements FieldType {
    private UUID uuid;
    private String name;
    private String typeClassName;
    private boolean primitive;
    private boolean importPackage;
    private DtoMetadata domainMetadata;

    public DefaultFieldTypeImpl() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeClassName() {
        return typeClassName;
    }

    public void setTypeClassName(String typeClassName) {
        this.typeClassName = typeClassName;
    }

    public boolean isPrimitive() {
        return primitive;
    }

    public void setPrimitive(boolean primitive) {
        this.primitive = primitive;
    }

    public boolean isImportPackage() {
        return importPackage;
    }

    public void setImportPackage(boolean importPackage) {
        this.importPackage = importPackage;
    }

    public DtoMetadata getDomainMetadata() {
        return domainMetadata;
    }

    public void setDomainMetadata(DtoMetadata domainMetadata) {
        this.domainMetadata = domainMetadata;
    }

    @JsonIgnore
    public String getSystemName(boolean needPrimitive) {
        StringBuilder sb = new StringBuilder();
        String systemName = BavlexStringUtils.getLastPartOfClassName(typeClassName);

        if (needPrimitive && isPrimitive()) {
            if (typeClassName.equals(Integer.class.getName())) {
                systemName = "int";
            } else {
                systemName = name.toLowerCase();
            }
        }

        if (domainMetadata != null) {
            systemName = domainMetadata.getSignatureClassName();
        }

        sb.append(systemName);
        if (typeClassName.equals(Map.class.getName())) {
            sb.append("<String, Object>");
        }

        return sb.toString();
    }

    @JsonIgnore
    public String getSystemName() {
        return getSystemName(false);
    }

    @JsonIgnore
    public String getNameTS() {
        return TypeScriptHelper.getTSTypeFrom(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultFieldTypeImpl)) return false;
        DefaultFieldTypeImpl fieldType = (DefaultFieldTypeImpl) o;
        return primitive == fieldType.primitive &&
            importPackage == fieldType.importPackage &&
            Objects.equals(uuid, fieldType.uuid) &&
            Objects.equals(name, fieldType.name) &&
            Objects.equals(typeClassName, fieldType.typeClassName) &&
            Objects.equals(domainMetadata, fieldType.domainMetadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, typeClassName, primitive, importPackage, domainMetadata);
    }

    @Override
    public String toString() {
        if (domainMetadata != null) {
            return domainMetadata.getSignatureClassName();
        }
        return name;
    }
}

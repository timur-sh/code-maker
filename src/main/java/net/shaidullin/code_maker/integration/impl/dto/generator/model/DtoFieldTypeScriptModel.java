package net.shaidullin.code_maker.integration.impl.dto.generator.model;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.ui.generator.model.AbstractModel;
import org.apache.commons.lang3.StringUtils;

public class DtoFieldTypeScriptModel extends AbstractModel {
    private String signature;
    private FieldMetadata fieldMetadata;
    private Type type;

    public DtoFieldTypeScriptModel() {
    }

    public DtoFieldTypeScriptModel(String signature, FieldMetadata fieldMetadata, Type type) {
        super(fieldMetadata.getSystemName());
        this.signature = signature;
        this.fieldMetadata = fieldMetadata;
        this.type = type;
    }

    public boolean isNullable() {
        return fieldMetadata.isNullable();
    }

    public String getSignature() {
        return signature;
    }

    public Type getType() {
        return type;
    }

    public String getDefinition() {

        if (fieldMetadata.isList()) {
            return assembleInitInstance(signature);

        } else if (type.getName().equals("NaturalCodeKey")) {
            return assembleInitInstance(signature);

        } else if (type.getName().equals("AttributeNaturalCodeKey")) {
            return assembleInitInstance(signature);


        } else if (type.getName().equals("AttributeValue")) {
            return assembleInitInstance(signature);
        }

        return "";
    }

    private String assembleInitInstance(String code) {
        return String.format(" = new %s()", code);
    }
}

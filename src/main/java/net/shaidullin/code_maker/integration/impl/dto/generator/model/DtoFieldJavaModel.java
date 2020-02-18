package net.shaidullin.code_maker.integration.impl.dto.generator.model;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.ui.generator.model.AbstractModel;

public class DtoFieldJavaModel extends AbstractModel {
    private String signature;
    private FieldMetadata fieldMetadata;
    private Type type;

    public DtoFieldJavaModel() {
    }

    public DtoFieldJavaModel(String signature, FieldMetadata fieldMetadata, Type type) {
        super(fieldMetadata.getSystemName());
        this.signature = signature;
        this.fieldMetadata = fieldMetadata;
        this.type = type;
    }

    public String getSignature() {
        return signature;
    }

    public Type getType() {
        return type;
    }

    public String getDefinition() {

        if (fieldMetadata.isList()) {
            return "= new ArrayList<>()";
        } else if (type.getName().equals("NaturalCodeKey")) {
            return "= new NaturalCodeKey()";
        } else if (type.getName().equals("AttributeNaturalCodeKey")) {
            return "= new AttributeNaturalCodeKey()";
        } else if (type.getName().equals("AttributeValue")) {
            return "= new AttributeValue<>()";
        }

        return "";
    }
}

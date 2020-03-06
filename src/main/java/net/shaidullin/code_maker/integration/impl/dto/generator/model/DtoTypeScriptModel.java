package net.shaidullin.code_maker.integration.impl.dto.generator.model;

import net.shaidullin.code_maker.ui.generator.model.AbstractModel;

import java.util.Set;
import java.util.TreeSet;

public class DtoTypeScriptModel extends AbstractModel {
    private String signatureClassName;
    private String parent;

    private Set<DtoFieldTypeScriptModel> fields = new TreeSet<>();

    public DtoTypeScriptModel() {
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Set<DtoFieldTypeScriptModel> getFields() {
        return fields;
    }

    public void setFields(Set<DtoFieldTypeScriptModel> fields) {
        this.fields = fields;
    }

    public String getSignatureClassName() {
        return signatureClassName;
    }

    public void setSignatureClassName(String signatureClassName) {
        this.signatureClassName = signatureClassName;
    }
}


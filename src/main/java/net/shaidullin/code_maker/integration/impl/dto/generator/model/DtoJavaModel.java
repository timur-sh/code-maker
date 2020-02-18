package net.shaidullin.code_maker.integration.impl.dto.generator.model;

import net.shaidullin.code_maker.ui.generator.model.AbstractModel;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DtoJavaModel extends AbstractModel {
    private String signatureClassName;
    private String parent;
    private List<String> interfaces = new ArrayList<>();

    private Set<DtoFieldJavaModel> fields = new TreeSet<>();

    public DtoJavaModel() {
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Set<DtoFieldJavaModel> getFields() {
        return fields;
    }

    public void setFields(Set<DtoFieldJavaModel> fields) {
        this.fields = fields;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public String getInterfaceSignature() {
        if (interfaces.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" implements ");
        sb.append(StringUtils.join(interfaces, ", "));

        return sb.toString();
    }

    public String getSignatureClassName() {
        return signatureClassName;
    }

    public void setSignatureClassName(String signatureClassName) {
        this.signatureClassName = signatureClassName;
    }
}


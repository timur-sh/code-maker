package net.shaidullin.code_maker.integration.impl.dto.generator.model;

import com.google.common.base.CaseFormat;
import net.shaidullin.code_maker.ui.generator.model.AbstractModel;

public class DtoJavaCacheModel extends AbstractModel {
    private String key;
    private String value;

    public DtoJavaCacheModel() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCacheName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, value);
    }
}

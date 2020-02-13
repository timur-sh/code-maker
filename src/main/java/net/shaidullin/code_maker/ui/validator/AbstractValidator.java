package net.shaidullin.code_maker.ui.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractValidator {
    protected boolean valid = true;
    protected List<String> result = new ArrayList<>();

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}

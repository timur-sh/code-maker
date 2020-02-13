package net.shaidullin.code_maker.ui.validator;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

public class NameValidator extends AbstractValidator {
    public void validate(String name) {
        if (StringUtils.isEmpty(name)) {
            valid = false;
            result.add("Name is empty");
            return;
        }

        if (name.contains(".") || name.contains(" ") || name.contains("-")) {
            valid = false;
            result.add("Name ends with space, or contain '.' or contains '-'");
            return;
        }

        Pattern p = Pattern.compile("^[0-9].*$");
        if (p.matcher(name).find()) {
            valid = false;
            result.add("Not valid file name");
            return;

        }
    }
}

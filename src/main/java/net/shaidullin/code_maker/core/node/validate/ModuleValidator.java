package net.shaidullin.code_maker.core.node.validate;

import net.shaidullin.code_maker.core.node.ModuleNode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class ModuleValidator extends AbstractValidator {
    public ModuleValidator() {
    }

    public void validate(ModuleNode moduleNode) {
        if (StringUtils.isEmpty(moduleNode.getSystemName())) {
            valid = false;
            result.add("System name is empty");
        }

        if (StringUtils.isBlank(moduleNode.getMetadata().getFqnPackage())) {
            valid = false;
            result.add("Package name is empty");
        }
    }
}

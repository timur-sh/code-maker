package net.shaidullin.code_maker.ui.validator;

import net.shaidullin.code_maker.core.node.PackageNode;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class PackageValidator extends AbstractValidator {
    private final List<PackageNode> packageNodes;

    public PackageValidator(List<PackageNode> packageNodes) {
        this.packageNodes = packageNodes;
    }

    public void validate(String packageName) {
        if (StringUtils.isEmpty(packageName)) {
            valid = false;
            result.add("Name is empty");
            return;
        }

        if (packageName.contains(".") || packageName.contains(" ") || packageName.contains("-")) {
            valid = false;
            result.add("Name ends with space, or contain '.' or contains '-'");
            return;
        }

        boolean packageExists = packageNodes.stream()
            .anyMatch(packageNode -> packageNode.getSystemName().equals(packageName));
        if (packageExists) {
            valid = false;
            result.add(String.format("Package with the name '%s' exists already", packageName));
            return;
        }

    }
}

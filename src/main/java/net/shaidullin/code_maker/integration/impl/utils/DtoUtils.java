package net.shaidullin.code_maker.integration.impl.utils;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;

import java.util.ArrayList;
import java.util.List;

public class DtoUtils {
    public static List<String> buildImportedPackages(DtoNode dtoNode, ApplicationState state) {
        List<String> result = new ArrayList<>();

        for (FieldMetadata field : dtoNode.getMetadata().getFields()) {
            if (field.isList()) {
                result.add("java.util.List");
            }

            Type type = TypeUtils.getType(field.getTypeUID(), state);

            if (type.isRequiredImport()) {
                result.add(type.getFqnName());
            }
        }

        return result;
    }
}

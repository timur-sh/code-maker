package net.shaidullin.code_maker.integration.utils;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static void sortNodesByParent(List<DtoNode> dtoNodes, ApplicationState state) {
        dtoNodes.sort((o1, o2) ->
            compareToDtoNode(o1, o2, state)
        );
    }

    private static int compareToDtoNode(DtoNode o1, DtoNode o2, ApplicationState state) {
        // Классы без родителей
        // Классы с родителями

        if (o1.getMetadata().getParentUID() == null && o2.getMetadata().getParentUID() == null) {
            return 0;
        }

        if (o1.getMetadata().getParentUID() == null) {
            return -1;
        }

        if (o2.getMetadata().getParentUID() == null) {
            return 1;
        }

        LeafNode leafNode1 = state.getLeafByUID(o1.getMetadata().getParentUID());
        LeafNode leafNode2 = state.getLeafByUID(o2.getMetadata().getParentUID());

        if (leafNode1 instanceof DtoNode && leafNode2 instanceof DtoNode) {
            return compareToDtoNode(((DtoNode) leafNode1), (DtoNode) leafNode2, state);
        }

        return 0;
    }
}

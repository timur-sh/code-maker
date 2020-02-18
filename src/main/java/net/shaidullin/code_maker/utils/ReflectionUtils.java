package net.shaidullin.code_maker.utils;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils {
    public static Node getNodeSupportedByWBP(PackageNode packageNode, WorkspacePanel workspacePanel, ApplicationState state) {
        Method method = null;

        try {
            Field body = workspacePanel.getClass().getDeclaredField("body");
            body.setAccessible(true);
            for (Method declaredMethod : body.getType().getDeclaredMethods()) {
                if (declaredMethod.getName().equalsIgnoreCase("isInProcess")) {
                    method = declaredMethod;
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (method == null || method.getParameterTypes().length == 0) {
            return null;
        }

        Class<?> parameter = method.getParameterTypes()[0];


        for (LeafNode leafNode : state.getLeaves().get(packageNode)) {
            if (parameter.isAssignableFrom(leafNode.getClass())) {
                return leafNode;
            }
        }

        return null;
    }
}

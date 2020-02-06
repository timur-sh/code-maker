package net.shaidullin.code_maker.config.utils;

import net.shaidullin.code_maker.core.node.ModuleNode;
import org.apache.commons.collections.CollectionUtils;

public class ConfigUtils {

    /**
     * @param other module
     * @return true if {@code other} module is injected into current
     */
    public static boolean isModuleAllowed(ModuleNode current, ModuleNode other) {
        if (current == null || CollectionUtils.isNotEmpty(current.getMetadata().getUsedModules()) || other == null) {
            return false;
        }

        return current.getMetadata().getUsedModules().contains(other.getSystemName());
    }
}

package net.shaidullin.code_maker.integration;

import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.ModuleNode;

public class IoUtils {
    public static ElementNode assembleElementNode(IntegrationObject integrationObject, ModuleNode module) {
        return new ElementNode(integrationObject.getFolder(), module, integrationObject);
    }
}

package net.shaidullin.code_maker.core;

import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.ModuleNode;

import java.util.UUID;

public class NodeTestUtils {
    public final static ModuleNode SECURITY_MODULE = createModuleNode("security", "/security",
        "security", null);

    public final static ModuleNode NEWS_MODULE = createModuleNode("news", "/news",
        "news", SECURITY_MODULE);

    public static ModuleNode createModuleNode(String name, String rootPath, String secondPackageName,
                                              ModuleNode usedModule) {
        ModuleMetadata metadata = new ModuleMetadata();

        if (usedModule != null) {
            metadata.getUsedModules().add(usedModule.getSystemName());
        }

        metadata.setDescription("Module for testing purpose");
        metadata.getFqnPackageParts().add("com");
        metadata.getFqnPackageParts().add(secondPackageName);
        metadata.setSystemName(name);
        metadata.setUuid(UUID.randomUUID());

        ModuleNode moduleNode = new ModuleNode();
        moduleNode.setRootMetadataPath(rootPath);
        moduleNode.setSystemName(name);
        moduleNode.setMetadata(metadata);

        return moduleNode;
    }
}

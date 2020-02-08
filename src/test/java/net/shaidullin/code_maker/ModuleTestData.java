package net.shaidullin.code_maker;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.config.CMState;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public interface ModuleTestData {
    Path FIXTURE_PATH = Paths.get("src", "test", "resources", "fixture");

    Path GENERATED_FILES_PATH = Paths.get(FIXTURE_PATH.toString(), "generated-files");

    Path AUTO_CLEAN_PATH = Paths.get(FIXTURE_PATH.toString(), "auto-clean");

    String SECURITY_MODULE_NAME = "security";
    Path SECURITY_MODULE_PATH = Paths.get(AUTO_CLEAN_PATH.toString(), SECURITY_MODULE_NAME + ".json");

    ApplicationState autoCleanState = new ApplicationState();

    static ApplicationState initializeAutoCleanState() {
        CMState cmState = new CMState();
        cmState.setModuleNodes(new ArrayList<>());

        File securityFile = new File(SECURITY_MODULE_PATH.toAbsolutePath().toUri());
        ModuleNode securityModuleNode = new ModuleNode();
        securityModuleNode.setSystemName(FileUtils.getFileName(securityFile.getName()));
        securityModuleNode.setRootMetadataPath(securityFile.getParent());
        NodeUtils.reloadMetadata(securityModuleNode);
        cmState.getModuleNodes().add(securityModuleNode);

        cmState.setConfiguration(new HashMap<>());
        cmState.getConfiguration().put(ApplicationState.GENERATE_PATH, GENERATED_FILES_PATH.toString());

        autoCleanState.loadState(cmState);
        autoCleanState.refreshState();

        return autoCleanState;
    }

    static ModuleNode getSecurityNode() {
        return autoCleanState.getModules().stream()
            .filter(m -> ModuleTestData.SECURITY_MODULE_NAME.equals(m.getSystemName()))
            .findFirst()
            .orElse(null);
    }

    static void deleteAutoCleanState() {
        for (ModuleNode module : autoCleanState.getModules()) {
            for (IntegrationObject integrationObject : autoCleanState.getIntegrationObjects()) {
                String pathToMetadata = FileUtils.buildPathToMetadata(module);
                File file = new File(pathToMetadata, integrationObject.getFolder());
                file.deleteOnExit();

            }
        }
    }

}

package net.shaidullin.code_maker.config;

import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.utils.FileHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ConfigurationManager {
    private static final ConfigurationManager INSTANCE = new ConfigurationManager();
    private final ReentrantLock lock = new ReentrantLock();

    private final List<ModuleNode> MODULES = new ArrayList<>();
    private final Map<ModuleNode, List<ElementNode>> ELEMENTS = new HashMap<>();
    private final Map<ElementNode, List<PackageNode>> PACKAGES = new HashMap<>();
    private final Map<PackageNode, List<LeafNode>> LEAVES = new HashMap<>();

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        return INSTANCE;
    }

    public void performConfiguration(ApplicationState state) {
        lock.lock();
        try {
            if (state.getUsedLeaf() != null) {
                reloadClass(state.getUsedLeaf());

            } else if (state.getUsedPackage() != null) {
                reloadPackage(state.getUsedPackage());

            } else if (state.getUsedElement() != null) {
                reloadElement(state.getUsedElement());

            } else {
                MODULES.clear();

                for (ModuleNode module : state.getModules()) {
                    MODULES.add(module);

                    for (IntegrationObject integrationObject : state.getIntegrationObjects()) {
                        integrationObject.initialize(module);
                        loadElementsByModule(module, integrationObject);
                    }
                }
            }
            assembleConfiguration(state);

        } finally {
            lock.unlock();
        }

    }

    private void assembleConfiguration(ApplicationState state) {
        for (ModuleNode module : MODULES) {
            state.getElements().put(module, new ArrayList<>(ELEMENTS.get(module)));

            for (ElementNode elementNode : ELEMENTS.get(module)) {
                state.getPackages().put(elementNode, new ArrayList<>(PACKAGES.get(elementNode)));

                for (PackageNode packageNode : PACKAGES.get(elementNode)) {
                    state.getLeaves().put(packageNode, new ArrayList<>(LEAVES.get(packageNode)));
                }
            }
        }
    }

    private void reloadClass(LeafNode leafNode) {
        reloadPackage(leafNode.getParent());
    }

    private void reloadPackage(PackageNode packageNode) {
        loadPackagesByElement(packageNode.getParent(), packageNode.getIntegrationObject());
    }

    private void reloadElement(ElementNode elementNode) {
        loadElementsByModule(elementNode.getParent(), elementNode.getIntegrationObject());
    }

    private void loadElementsByModule(ModuleNode module, IntegrationObject integrationObject) {
        ELEMENTS.remove(module);
        ELEMENTS.put(module, new ArrayList<>());
        List<ElementNode> elementNodes = ELEMENTS.get(module);

        List<ElementNode> elements = FileHelper.getFolders(module.getPath()).stream()
            .map(folderName -> new ElementNode(folderName, module, integrationObject))
            .collect(Collectors.toList());

        elementNodes.addAll(elements);

        for (ElementNode elementNode : elements) {
            loadPackagesByElement(elementNode, integrationObject);
        }
    }

    private void loadPackagesByElement(ElementNode elementNode, IntegrationObject integrationObject) {
        PACKAGES.remove(elementNode);
        PACKAGES.put(elementNode, new ArrayList<>());
        List<PackageNode> packageNodes = PACKAGES.get(elementNode);

        String pathToFolder = FileHelper.buildPathToMetadata(elementNode);
        for (String directoryName : FileHelper.getFolders(pathToFolder)) {
            PackageNode packageNode = new PackageNode(
                directoryName,
                elementNode,
                integrationObject
            );
            packageNodes.add(packageNode);
        }

        for (PackageNode packageNode : packageNodes) {
            loadClassesByPackage(packageNode, integrationObject);
        }
    }

    private void loadClassesByPackage(PackageNode packageNode, IntegrationObject integrationObject) {
        LEAVES.remove(packageNode);
        LEAVES.put(packageNode, new ArrayList<>());
        List<LeafNode> leafNodes = LEAVES.get(packageNode);

        leafNodes.addAll(integrationObject.getLeaves(packageNode));
    }

}

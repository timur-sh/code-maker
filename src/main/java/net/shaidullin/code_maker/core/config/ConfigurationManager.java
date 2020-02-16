package net.shaidullin.code_maker.core.config;

import net.shaidullin.code_maker.core.metadata.ElementMetadata;
import net.shaidullin.code_maker.core.metadata.PackageMetadata;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.IntegrationElement;
import net.shaidullin.code_maker.integration.IoUtils;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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
            NameResolverManager resolverManager = NameResolverManager.getInstance();

            if (state.getUsedLeaf() != null) {
                reloadClass(state.getUsedLeaf());

            } else if (state.getUsedPackage() != null) {
                reloadPackage(state.getUsedPackage());

            } else if (state.getUsedElement() != null) {
                reloadElement(state.getUsedElement());

            } else {
                MODULES.clear();

                for (ModuleNode module : state.getModules()) {
                    NodeUtils.reloadMetadata(module);
                    MODULES.add(module);

                    for (IntegrationElement integrationObject : state.getIntegrationElements()) {
                        integrationObject.initialize(module, resolverManager);
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
        loadPackagesByElement(packageNode.getParent(), packageNode.getIntegrationElement());
    }

    private void reloadElement(ElementNode elementNode) {
        loadElementsByModule(elementNode.getParent(), elementNode.getIntegrationElement());
    }

    private void loadElementsByModule(ModuleNode module, IntegrationElement integrationObject) {
        if (ELEMENTS.containsKey(module)) {
            ELEMENTS.get(module).removeIf(elementNode ->
                elementNode.getSystemName().equals(integrationObject.getFolder()));
        } else {
            ELEMENTS.put(module, new ArrayList<>());
        }

        String pathToMetadata = FileUtils.buildPathToMetadata(module);
        if (!FileUtils.exists(pathToMetadata, integrationObject.getFolder())) {
            return;
        }

        ElementNode elementNode = IoUtils.assembleElementNode(integrationObject, module);
        elementNode.setMetadata(NodeUtils.readMetadata(elementNode, ElementMetadata.class));
        loadPackagesByElement(elementNode, integrationObject);

        ELEMENTS.get(module).add(elementNode);
    }

    private void loadPackagesByElement(ElementNode elementNode, IntegrationElement integrationObject) {
        PACKAGES.remove(elementNode);
        PACKAGES.put(elementNode, new ArrayList<>());
        List<PackageNode> packageNodes = PACKAGES.get(elementNode);

        String pathToFolder = FileUtils.buildPathToMetadata(elementNode);
        for (String directoryName : FileUtils.getFolders(pathToFolder)) {
            PackageNode packageNode = new PackageNode(
                directoryName,
                elementNode,
                integrationObject
            );
            packageNode.setMetadata(NodeUtils.readMetadata(packageNode, PackageMetadata.class));
            packageNodes.add(packageNode);
        }

        for (PackageNode packageNode : packageNodes) {
            loadClassesByPackage(packageNode, integrationObject);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadClassesByPackage(PackageNode packageNode, IntegrationElement integrationObject) {
        LEAVES.remove(packageNode);
        LEAVES.put(packageNode, new ArrayList<>());

        LEAVES.get(packageNode)
            .addAll(integrationObject.getLeaves(packageNode));
    }
}

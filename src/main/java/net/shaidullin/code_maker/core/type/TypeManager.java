package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manager provides access methods to type storage
 */
public class TypeManager {
    private TypeStorage storage;
    private static TypeManager INSTANCE = new TypeManager();

    private static volatile boolean initialized = false;

    private TypeManager() {
    }

    public static TypeManager getInstance() {
        return INSTANCE;
    }

    /**
     * @return true if storage is already initialized
     */
    public boolean isInitialized() {
        return initialized;
    }

    public void initialize(ApplicationState state) {
        storage = new TypeStorage();
        initialized = true;
        reinitializeStorage(state);
    }

    @SuppressWarnings("unchecked")
    public void reinitializeStorage(ApplicationState state) {
        storage.initializePrimitiveTypes();

        Map<ElementNode, Map<UUID, FieldType>> customTypes = new HashMap<>();

        Map<PackageNode, List<LeafNode>> leaves = state.getLeaves();
        for (PackageNode packageNode : leaves.keySet()) {
            ElementNode elementNode = packageNode.getParent();

            if (!customTypes.containsKey(elementNode)) {
                customTypes.put(elementNode, new HashMap<>());
            }

            Map<UUID, FieldType> fieldTypeMap = customTypes.get(elementNode);

            for (LeafNode leafNode : leaves.get(packageNode)) {
                LeafMetadata metadata = leafNode.getMetadata();

                fieldTypeMap.put(
                    metadata.getUuid(),
                    leafNode.getIntegrationObject().buildFieldType(leafNode)
                );
            }
        }
        storage.registerCustomTypes(customTypes);

    }

    public FieldType getTypeByUID(UUID uuid) {
        return storage.getFieldByUID(uuid);
    }

    public Map<UUID, FieldType> getPrimitives() {
        return storage.getPrimitives();
    }

    public Map<UUID, FieldType> getTypesByElementNode(ElementNode elementNode) {
        return storage.getTypesByElementNode(elementNode);
    }

    public List<FieldType> getTypes() {
        List<FieldType> result = new ArrayList<>(storage.getPrimitives().values());
        result.addAll(storage.getClassesByModule().values());
        return result.stream()
            .sorted(Comparator.comparing(FieldType::getName))
            .collect(Collectors.toList());
    }
}

package net.shaidullin.code_maker.config;

import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.type.FieldType;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.integration.IntegrationObjectRegistry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manager provides accessing methods to type storage
 */
public class TypeManager {
    private final IntegrationObjectRegistry registry;
    private TypeStorage storage;
    private static TypeManager INSTANCE;
    private static volatile boolean initialized = false;

    private TypeManager(IntegrationObjectRegistry registry) {
        this.registry = registry;
    }

    public static TypeManager getInstance(IntegrationObjectRegistry registry) {
        if (INSTANCE == null) {
            INSTANCE = new TypeManager(registry);
        }
        return INSTANCE;
    }

    /**
     * Type manager
     *
     * @return
     */
    public boolean isInitialized() {
        return initialized;
    }

    public void initialize() {
        storage = new TypeStorage();
        initialized = true;
        reinitializeStorage();
    }

    public void reinitializeStorage() {
        storage.initialize();

        for (IntegrationObject integrationObject : registry) {
            integrationObject.registerTypes(storage);
        }
    }

    public FieldType getTypeByUID(UUID uuid) {
        return storage.getFieldByUID(uuid);
    }

    public Map<UUID, FieldType> getPrimitives() {
        return storage.getPrimitives();
    }

    public Map<UUID, FieldType> getCustomTypesByModule(ModuleNode cmModule) {
        return storage.getClassesByModule(cmModule);
    }

    public List<FieldType> getTypes() {
        List<FieldType> result = new ArrayList<>(storage.getPrimitives().values());
        result.addAll(storage.getClassesByModule().values());
        return result.stream()
            .sorted(Comparator.comparing(FieldType::getName))
            .collect(Collectors.toList());
    }
}

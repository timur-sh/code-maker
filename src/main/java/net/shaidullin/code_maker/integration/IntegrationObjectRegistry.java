package net.shaidullin.code_maker.integration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class IntegrationObjectRegistry implements Iterable<IntegrationObject> {
    private Map<String, IntegrationObject> REGISTRY = new HashMap<>();

    public IntegrationObjectRegistry() {
    }

    /**
     * Register a new integration object
     *
     * @param integrationObject
     * @throws IllegalArgumentException if the integration object is registered
     */
    public void register(IntegrationObject integrationObject) {
        if (REGISTRY.containsKey(integrationObject.getUID())) {
            throw new IllegalArgumentException(String.format("Integration object with the UUID='%s' is already registered.", integrationObject.getUID()));
        }

        REGISTRY.put(integrationObject.getUID(), integrationObject);
    }

    @Nullable
    public IntegrationObject getByUID(String uid) {
        return REGISTRY.get(uid);
    }

    public void deleteByUID(String uid) {
        REGISTRY.remove(uid);
    }

    public Collection<IntegrationObject> getAll() {
        return REGISTRY.values();
    }

    @NotNull
    @Override
    public Iterator<IntegrationObject> iterator() {
        return REGISTRY.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super IntegrationObject> action) {
        for (IntegrationObject value : REGISTRY.values()) {
            action.accept(value);
        }
    }

    @Override
    public Spliterator<IntegrationObject> spliterator() {
        return REGISTRY.values().spliterator();
    }
}

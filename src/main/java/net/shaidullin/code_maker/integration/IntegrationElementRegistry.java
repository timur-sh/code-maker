package net.shaidullin.code_maker.integration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class IntegrationElementRegistry implements Iterable<IntegrationElement<?>> {
    private Map<String, IntegrationElement<?>> REGISTRY = new HashMap<>();

    public IntegrationElementRegistry() {
    }

    /**
     * Register a new integration object
     *
     * @param integrationObject
     * @throws IllegalArgumentException if the integration object is registered
     */
    public void register(IntegrationElement<?> integrationObject) {
        if (REGISTRY.containsKey(integrationObject.getUID())) {
            throw new IllegalArgumentException(String.format("Integration object with the UUID='%s' is already registered.", integrationObject.getUID()));
        }

        REGISTRY.put(integrationObject.getUID(), integrationObject);
    }

    @Nullable
    public IntegrationElement<?> getByUID(String uid) {
        return REGISTRY.get(uid);
    }

    public void deleteByUID(String uid) {
        REGISTRY.remove(uid);
    }

    public Collection<IntegrationElement<?>> getAll() {
        return REGISTRY.values();
    }

    @NotNull
    @Override
    public Iterator<IntegrationElement<?>> iterator() {
        return REGISTRY.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super IntegrationElement<?>> action) {
        for (IntegrationElement<?> value : REGISTRY.values()) {
            action.accept(value);
        }
    }

    @Override
    public Spliterator<IntegrationElement<?>> spliterator() {
        return REGISTRY.values().spliterator();
    }
}

package net.shaidullin.code_maker.ui.resolver;

import net.shaidullin.code_maker.core.config.ApplicationState;

import java.util.HashMap;
import java.util.Map;

/**
 * Name resolvers are initialized in {@link ApplicationState#initialize()}
 */
public class NameResolverManager {
    public static final String JAVA = "java";

    private static final NameResolverManager instance = new NameResolverManager();
    private final Map<String, NameResolver> resolvers = new HashMap<>();

    private NameResolverManager() {
    }

    public static NameResolverManager getInstance() {
        return instance;
    }

    public NameResolverManager register(NameResolver nameResolver) {
        String key = nameResolver.getClass().getName();
        if (resolvers.containsKey(key)) {
            return this;
        }

        resolvers.put(key, nameResolver);

        return this;
    }

    public String resolve(String language, Object element, boolean forPrimitive) {
        NameResolver resolver = resolvers.values().stream()
            .filter(r -> r.isSupport(element))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Name resolver not found for class - " + element.getClass().getName()));

        return resolver.resolve(element, forPrimitive);
    }
}

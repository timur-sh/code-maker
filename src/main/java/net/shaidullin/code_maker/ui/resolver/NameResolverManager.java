package net.shaidullin.code_maker.ui.resolver;

import net.shaidullin.code_maker.core.config.ApplicationState;

import java.util.HashMap;
import java.util.Map;

/**
 * Name resolvers are initialized in {@link ApplicationState#initialize()}
 */
public class NameResolverManager {
    public static final String TYPE_SCRIPT = "ts";
    public static final String JAVA = "java";
    public static final String PLUGIN_UI = "plugin-ui";

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

    public String resolveTypeScript(Object element, boolean forPrimitive) {
        return this.resolve(TYPE_SCRIPT, element, forPrimitive);
    }

    public String resolveJava(Object element, boolean forPrimitive) {
        return this.resolve(JAVA, element, forPrimitive);
    }


    public String resolve(String language, Object element, boolean forPrimitive, String typeArgument) {
        NameResolver resolver = findResolver(language, element);

        return resolver.resolve(element, forPrimitive, typeArgument);
    }

    public String resolve(String language, Object element, boolean forPrimitive) {
        NameResolver resolver = findResolver(language, element);

        return resolver.resolve(element, forPrimitive);
    }

    private NameResolver findResolver(String language, Object element) {
        return resolvers.values().stream()
            .filter(r -> language.equals(r.getSupportLanguage()))
            .filter(r -> r.isSupport(element))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Name resolver not found for class - " + element.getClass().getName()));
    }
}

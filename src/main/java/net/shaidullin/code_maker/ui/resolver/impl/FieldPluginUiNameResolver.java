package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class FieldPluginUiNameResolver implements NameResolver {
    private final FieldJavaNameResolver fieldJavaNameResolver = new FieldJavaNameResolver();

    @Override
    public String resolve(Object element) {
        return resolve(element, true);
    }

    /**
     * @param element
     * @param forPrimitive - not used
     * @return
     */
    @Override
    public String resolve(Object element, boolean forPrimitive) {
        FieldMetadata metadata = ((FieldMetadata) element);

        StringBuilder sb = new StringBuilder(metadata.getSystemName())
            .append(": ");

        sb.append(
            fieldJavaNameResolver.resolve(element, forPrimitive)
        );

        return sb.toString();
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        throw new UnsupportedOperationException("FieldPluginUiNameResolver#resolve(element, forPrimitive, typeArgument)");
    }

    @Override
    public String getSupportLanguage() {
        return NameResolverManager.PLUGIN_UI;
    }

    @Override
    public boolean isSupport(Object metadata) {
        return metadata instanceof FieldMetadata;
    }
}

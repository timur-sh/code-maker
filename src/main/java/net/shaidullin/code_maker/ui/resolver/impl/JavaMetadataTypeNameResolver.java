package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.type.MetadataType;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class JavaMetadataTypeNameResolver implements NameResolver {
    @Override
    public String resolve(Object element) {
        return resolve(element, true);
    }

    /**
     * @param element
     * @param forPrimitive
     * @return
     */
    @Override
    public String resolve(Object element, boolean forPrimitive) {
        return resolve(element, forPrimitive, null);
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        MetadataType type = ((MetadataType) element);

        return NameResolverManager.getInstance()
            .resolve(getSupportLanguage(), type.getMetadata(), forPrimitive, typeArgument);
    }

    @Override
    public String getSupportLanguage() {
        return NameResolverManager.JAVA;
    }

    @Override
    public boolean isSupport(Object element) {
        return element instanceof MetadataType;
    }
}

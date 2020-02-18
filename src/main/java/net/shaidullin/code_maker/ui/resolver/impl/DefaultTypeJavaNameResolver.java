package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.type.DefaultTypeImpl;
import net.shaidullin.code_maker.core.type.MetadataType;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.PackageUtils;

import java.util.Map;

public class DefaultTypeJavaNameResolver implements NameResolver {
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
        DefaultTypeImpl type = ((DefaultTypeImpl) element);

        StringBuilder sb = new StringBuilder();
        String systemName;

        if (forPrimitive && type.isPrimitive()) {
            if (type.getFqnName().equals(Integer.class.getName())) {
                systemName = "int";

            } else {
                systemName = type.getName().toLowerCase();
            }

        } else {
            systemName = PackageUtils.getLastPart(type.getFqnName());
        }

        sb.append(systemName);
        if (type.getFqnName().equals(Map.class.getName())) {
            sb.append("<String, Object>");
        }

        return sb.toString();
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        throw new UnsupportedOperationException("DefaultTypeJavaNameResolver#resolve(element, forPrimitive, typeArgument)");
    }

    @Override
    public String getSupportLanguage() {
        return NameResolverManager.JAVA;
    }

    @Override
    public boolean isSupport(Object element) {
        return element instanceof DefaultTypeImpl;
    }
}

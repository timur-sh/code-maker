package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.type.DefaultTypeImpl;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

import java.util.Arrays;
import java.util.List;

public class TypeScriptDefaultTypeNameResolver implements NameResolver {
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
        DefaultTypeImpl fieldType = ((DefaultTypeImpl) element);

        List<String> numbers = Arrays.asList("Long", "Integer", "Double", "BigDecimal");
        if (numbers.contains(fieldType.getName())) {
            return "number";
        }

        if (fieldType.getName().equals("UUID") || fieldType.getName().equals("String")) {
            return "string";
        }

        if (fieldType.getName().equals("Date time") || fieldType.getName().equals("LocalTime")) {
            return "Date";
        }

        if (fieldType.getName().equals("Boolean")) {
            return "boolean";
        }

        if (fieldType.getName().equals("Map")) {
            return "{ [key:string]:any; }";
        }

        return fieldType.getName();
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        throw new UnsupportedOperationException("DefaultTypeJavaNameResolver#resolve(element, forPrimitive, typeArgument)");
    }

    @Override
    public String getSupportLanguage() {
        return NameResolverManager.TYPE_SCRIPT;
    }

    @Override
    public boolean isSupport(Object element) {
        return element instanceof DefaultTypeImpl;
    }
}

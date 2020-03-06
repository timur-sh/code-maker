package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class JavaFieldNameResolver implements NameResolver {

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

        StringBuilder sb = new StringBuilder();

        Type type = TypeManager.getInstance()
            .getTypeByUID(metadata.getTypeUID());

        if (metadata.isList()) {
            sb.append("List<")
                .append(getGenericOrDefaultName(metadata, type, false))
                .append(">");

        } else if (metadata.isNullable()) {
            sb.append(getGenericOrDefaultName(metadata, type, false));

        } else {
            sb.append(getGenericOrDefaultName(metadata, type, true));
        }

        return sb.toString();
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        throw new UnsupportedOperationException("FieldPluginUiNameResolver#resolve(element, forPrimitive, typeArgument)");
    }

    protected String getGenericOrDefaultName(FieldMetadata metadata, Type type, boolean forPrimitive) {
        // for generic metadata build generic name
        if (metadata.isGeneric()) {
            return metadata.getTypeParameter();
        }

        if (metadata.getTypeArgumentUID() != null) {
            Type typeArgument = TypeManager.getInstance()
                .getTypeByUID(metadata.getTypeArgumentUID());

            String typeArgumentName = NameResolverManager.getInstance()
                .resolve(getSupportLanguage(), typeArgument, false);

            return NameResolverManager.getInstance()
                .resolve(getSupportLanguage(), type, false, typeArgumentName);
        }

        return NameResolverManager.getInstance()
            .resolve(getSupportLanguage(), type, forPrimitive);
    }


    @Override
    public String getSupportLanguage() {
        return NameResolverManager.JAVA;
    }

    @Override
    public boolean isSupport(Object metadata) {
        return metadata instanceof FieldMetadata;
    }
}

package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class FieldJavaNameResolver implements NameResolver {
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

    private String getGenericOrDefaultName(FieldMetadata metadata, Type type, boolean forPrimitive) {
        // for generic metadata build generic name
        if (metadata.isGeneric()) {
            if (metadata.getTypeArgumentUID() != null) {
                Type typeArgument = TypeManager.getInstance()
                    .getTypeByUID(metadata.getTypeArgumentUID());

                return NameResolverManager.getInstance()
                    .resolve(getSupportLanguage(), typeArgument, forPrimitive);
            }

            return metadata.getTypeParameter();
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

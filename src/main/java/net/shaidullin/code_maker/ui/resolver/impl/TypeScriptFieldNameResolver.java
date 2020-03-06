package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class TypeScriptFieldNameResolver extends JavaFieldNameResolver {

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
            sb.append("Array<")
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


    @Override
    public String getSupportLanguage() {
        return NameResolverManager.TYPE_SCRIPT;
    }

    @Override
    public boolean isSupport(Object metadata) {
        return metadata instanceof FieldMetadata;
    }
}

package net.shaidullin.code_maker.integration.impl.dto.ui;

import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.ui.resolver.NameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import org.apache.commons.lang3.StringUtils;

public class DtoJavaNameResolver implements NameResolver {
    @Override
    public String resolve(Object element) {
        return resolve(element, true);
    }

    /**
     * @param element
     * @param forPrimitive - not used in DTO
     * @return
     */
    @Override
    public String resolve(Object element, boolean forPrimitive) {
        return resolve(element, forPrimitive, null);
    }

    @Override
    public String resolve(Object element, boolean forPrimitive, String typeArgument) {
        DtoMetadata metadata = ((DtoMetadata) element);

        if (metadata.isGeneric()) {
            String genericContent = StringUtils.isBlank(typeArgument)
                ? metadata.getTypeParameter()
                : typeArgument;

            return new StringBuilder().append(metadata.getSystemName())
                .append("<")
                .append(genericContent)
                .append(">")
                .toString();

        } else {
            return metadata.getSystemName();
        }
    }

    @Override
    public String getSupportLanguage() {
        return NameResolverManager.JAVA;
    }

    @Override
    public boolean isSupport(Object metadata) {
        return metadata instanceof DtoMetadata;
    }
}

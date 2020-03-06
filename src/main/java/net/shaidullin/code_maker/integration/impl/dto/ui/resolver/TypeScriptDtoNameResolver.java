package net.shaidullin.code_maker.integration.impl.dto.ui.resolver;

import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class TypeScriptDtoNameResolver extends JavaDtoNameResolver {
    @Override
    public String getSupportLanguage() {
        return NameResolverManager.TYPE_SCRIPT;
    }
}

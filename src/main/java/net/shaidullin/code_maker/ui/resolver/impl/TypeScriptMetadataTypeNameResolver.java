package net.shaidullin.code_maker.ui.resolver.impl;

import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class TypeScriptMetadataTypeNameResolver extends JavaMetadataTypeNameResolver {
    @Override
    public String getSupportLanguage() {
        return NameResolverManager.TYPE_SCRIPT;
    }

}

package net.shaidullin.code_maker.core.type;

import net.shaidullin.code_maker.ui.resolver.NameResolverManager;

public class DefaultTypeImpl extends AbstractType {
    public DefaultTypeImpl() {
    }

    @Override
    public String toString() {
        return NameResolverManager.getInstance()
            .resolve(NameResolverManager.JAVA, this, true);
    }
}

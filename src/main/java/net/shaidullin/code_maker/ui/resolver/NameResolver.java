package net.shaidullin.code_maker.ui.resolver;

public interface NameResolver {
    boolean isSupport(Object element);

    String resolve(Object element);

    String resolve(Object element, boolean forPrimitive);

    String resolve(Object element, boolean forPrimitive, String typeArgument);

    String getSupportLanguage();
}

package net.shaidullin.code_maker.core.metadata;

import java.util.UUID;

/**
 * Represent a generic class
 */
public interface GenericMetadata extends Metadata {
    /**
     * @return flag makes a class to be generic
     */
    boolean isGeneric();

    /**
     * @param generic flag makes a class to be generic
     */
    void setGeneric(boolean generic);

    /**
     * Represents T in Foo<T>
     *
     * @return type parameter (variable type)
     */
    String getTypeParameter();

    /**
     * Represents T in Foo<T>
     *
     * @param parameter - type parameter (type variable)
     */
    void setTypeParameter(String parameter);

    /**
     * Represents String in Foo<String>
     *
     * @return UID of type argument
     */
    UUID getTypeArgumentUID();

    /**
     * Represents String in Foo<String>
     *
     * @param uuid UID of type argument
     */
    void setTypeArgumentUID(UUID uuid);


}

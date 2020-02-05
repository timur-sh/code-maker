package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.Metadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

/**
 * Represent node in the menu tree
 *
 * @param <T>
 */
public interface Node<T extends Node, M extends Metadata> {
    /**
     * @return system name is synonym of folder/package name
     */
    String getSystemName();

    void setSystemName(String systemName);

    /**
     * @return parent node
     */
    T getParent();

    void setParent(T parent);

    /**
     * @return node metadata
     */
    M getMetadata();

    void setMetadata(M metadata);

    /**
     * @return integration object that the Node belongs to
     */
    IntegrationObject getIntegrationObject();

    void setIntegrationObject(IntegrationObject integrationObject);
}

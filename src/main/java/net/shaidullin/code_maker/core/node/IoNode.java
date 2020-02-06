package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.Metadata;
import net.shaidullin.code_maker.integration.IntegrationObject;

/**
 * Node from IntegrationObject context
 *
 * @param <N>
 * @param <M>
 */
public interface IoNode<N extends Node, M extends Metadata> extends Node<N, M> {
    /**
     * @return integration object that the Node belongs to
     */
    IntegrationObject getIntegrationObject();

    void setIntegrationObject(IntegrationObject integrationObject);
}

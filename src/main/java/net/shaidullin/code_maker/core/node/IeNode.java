package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.Metadata;
import net.shaidullin.code_maker.integration.IntegrationElement;

/**
 * Node from IntegrationObject context
 *
 * @param <N>
 * @param <M>
 */
public interface IeNode<N extends Node, M extends Metadata> extends Node<N, M> {
    /**
     * @return integration element that the Node belongs to
     */
    IntegrationElement getIntegrationElement();

    void setIntegrationElement(IntegrationElement integrationElement);
}

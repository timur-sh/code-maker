package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.integration.IntegrationElement;

/**
 * Leaf is the node displayed within tree of {@link IntegrationElement}
 *
 * @param <N>
 * @param <M>
 */
public interface LeafNode<N extends PackageNode, M extends LeafMetadata> extends IeNode<N, M> {

    @Override
    N getParent();

    @Override
    void setParent(N parent);

    @Override
    M getMetadata();

    @Override
    void setMetadata(M metadata);

    void removeNestedNode(Node nestedNode);
}

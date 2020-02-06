package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;

/**
 * Leaf is the node displayed within tree of {@link net.shaidullin.code_maker.integration.IntegrationObject}
 *
 * @param <N>
 * @param <M>
 */
public interface LeafNode<N extends PackageNode, M extends LeafMetadata> extends IoNode<N, M> {

    @Override
    N getParent();

    @Override
    void setParent(N parent);

    @Override
    M getMetadata();

    @Override
    void setMetadata(M metadata);
}

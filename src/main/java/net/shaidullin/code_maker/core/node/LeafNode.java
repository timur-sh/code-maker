package net.shaidullin.code_maker.core.node;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;

public interface LeafNode<N extends PackageNode, M extends LeafMetadata> extends Node<N, M> {

    @Override
    N getParent();

    @Override
    void setParent(N parent);

}

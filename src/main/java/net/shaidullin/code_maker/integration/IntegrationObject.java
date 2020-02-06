package net.shaidullin.code_maker.integration;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.FieldType;

import java.io.FileInputStream;
import java.util.List;

/**
 * It provides a business object such as DTO, Entity, Service etc.
 */
public interface IntegrationObject<N extends PackageNode, M extends LeafMetadata, F extends FieldType<M>> {
    /**
     * Each integration object must provide unique UID
     *
     * @return uid
     */
    String getName();

    /**
     * @return the folder name for packages of the object
     */
    String getFolder();

    /**
     * Generate code and store it
     */
    void generate(PackageNode packageNode);

    /**
     * Initialize integration object when it runs the first time
     *
     * @param moduleNode
     */
    void initialize(ModuleNode moduleNode);

    /**
     * Leaves of this integration object
     *
     * @param packageNode
     * @return
     */
    List<LeafNode<N, M>> getLeaves(PackageNode packageNode);


    LeafNode<N, M> buildLeaf(String systemName, FileInputStream inputStream, PackageNode packageNode);

    F buildFieldType(M metadata);

    /**
     * Declared dependencies that will be used by the integration object. For
     * example - types or DTO in Service
     *
     * @return list of IntegrationObject's classes
     */
    List<Class<IntegrationObject>> declareDependencies();
}

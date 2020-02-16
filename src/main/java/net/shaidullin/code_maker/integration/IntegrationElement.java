package net.shaidullin.code_maker.integration;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.MetadataType;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.ui.toolwindow.tree.NodeTreeMenu;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.util.List;
import java.util.UUID;

/**
 * It provides a business object such as DTO, Entity, Service etc.
 * Each {@link IntegrationElement} serves only one class extends the {@link LeafNode}
 */
public interface IntegrationElement<N extends LeafNode> {
    /**
     * Each integration object must provide unique UID
     *
     * @return uid
     */
    String getUID();

    /**
     * Name of integration object to display by UI
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
    void initialize(ModuleNode moduleNode, NameResolverManager resolverManager);

    /**
     * Leaves of this integration object
     *
     * @param packageNode
     * @return
     */
    List<N> getLeaves(PackageNode packageNode);

    /**
     * Read {@code inputStream} to {@link LeafMetadata}. Then build a fully initialized
     * {@link LeafNode}
     *
     * @param systemName
     * @param inputStream
     * @param packageNode
     * @return
     */
    N buildLeaf(String systemName, FileInputStream inputStream, PackageNode packageNode);

    /**
     * Create a new LeafNode object
     *
     * @param uuid
     * @param parentNode
     * @return
     */
    N assembleLeafTemplate(UUID uuid, PackageNode parentNode);

    /**
     * If the {@link LeafNode}can be represented as a {@link MetadataType},
     * the assembled object is returned. Otherwise it returns null
     *
     * @param node
     * @return
     */
    @Nullable
    MetadataType<? extends LeafMetadata> buildFieldType(N node);

    /**
     * Declare the {@link IntegrationElement} whose leaves are used by
     * the current IE
     *
     * @return list of IntegrationObject's classes
     */
    List<Class<IntegrationElement>> declareDependencies();

    /**
     * Creates a menu for the served {@link LeafNode}.
     * <p>
     * Menu is a part of {@link net.shaidullin.code_maker.ui.toolwindow.NodeTreePanel}
     *
     * @param state of application
     * @return
     */
    NodeTreeMenu<N> createNodeTreeMenu(ApplicationState state);

    /**
     * Creates a workspace panel body for the served {@link LeafNode}.
     *
     * @param project
     * @param tree
     * @param state
     * @return
     */
    WorkspacePanelBody createWorkspacePanelBody(Project project, JTree tree, ApplicationState state);

    /**
     * The {@link LeafNode} can contain a nested nodes. Methods returns a instance of WBP for {@code nestedNode}
     *
     * @param nestedNode
     * @param state
     * @return
     */
    @Nullable
    WorkspacePanelBody createWorkspacePanelBodyForNestedNode(Node nestedNode, Project project, JTree tree, ApplicationState state);

    /**
     * Put fields/operations of leaf to tree node that will be displaying in UI
     *
     * @param treeNode
     * @param leafNode
     */
    void assembleTreeNodeByTreeNode(DefaultMutableTreeNode treeNode, N leafNode);


}

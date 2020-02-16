package net.shaidullin.code_maker.ui.toolwindow.tree;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Provides API to work with NodeTreePanel
 */
public interface NodeTreeMenu<N extends Node> {
    /**
     * @param node
     * @return true if node is allowed for the menu
     */
    boolean isNodeAllowed(Object node);

    /**
     * Save the node in cache
     * Save the node in {@link net.shaidullin.code_maker.core.config.ApplicationState}
     */
    void setSelectedNode(@Nullable Object node);

    /**
     * @return selected node
     */
    @Nullable
    N getSelectedNode();

    /**
     * Show menu of selected node
     *
     * @param invoker
     * @param x
     * @param y
     */
    void show(Component invoker, int x, int y);

    /**
     * Initialize menu
     *
     * @param tree
     * @param project
     */
    void initialize(JTree tree, Project project, WorkspacePanel workspacePanel);


}

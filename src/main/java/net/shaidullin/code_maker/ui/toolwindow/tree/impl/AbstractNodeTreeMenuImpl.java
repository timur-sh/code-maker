package net.shaidullin.code_maker.ui.toolwindow.tree.impl;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.ui.toolwindow.tree.NodeTreeMenu;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractNodeTreeMenuImpl<N extends Node> implements NodeTreeMenu<N> {
    protected final ApplicationState state;
    protected final JPopupMenu popupMenu;
    protected boolean initialized;

    public AbstractNodeTreeMenuImpl(ApplicationState state, JPopupMenu popupMenu) {
        this.state = state;
        this.popupMenu = popupMenu;
    }

    @Override
    public void show(Component invoker, int x, int y) {
        if (!this.initialized) {
            throw new IllegalStateException("NodeTreeMenu is not initialized. Class=" + this.getClass().getSimpleName());
        }
        this.popupMenu.show(invoker, x, y);
    }

    @Override
    public void initialize(JTree tree, Project project, WorkspacePanel workspacePanel) {
        this.initialized = true;
    }
}

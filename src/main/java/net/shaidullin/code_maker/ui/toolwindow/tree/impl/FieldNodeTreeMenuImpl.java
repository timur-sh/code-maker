package net.shaidullin.code_maker.ui.toolwindow.tree.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBPopupMenu;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.listener.*;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class FieldNodeTreeMenuImpl extends AbstractNodeTreeMenuImpl<FieldNode> {
    private FieldNode selectedNode;

    public FieldNodeTreeMenuImpl(ApplicationState state) {
        super(state, new JPopupMenu());
    }

    @Override
    public boolean isNodeAllowed(Object node) {
        if (node == null) {
            return false;
        }

        return node instanceof FieldNode;
    }

    @Override
    public void setSelectedNode(@Nullable Object node) {
        this.selectedNode = (FieldNode) node;
    }

    @Nullable
    @Override
    public FieldNode getSelectedNode() {
        return this.selectedNode;
    }

    @Override
    public void initialize(JTree tree, Project project, WorkspacePanel workspacePanel) {
        if (this.initialized) {
            return;
        }
        super.initialize(tree, project, workspacePanel);

        JMenuItem editField = new JMenuItem("Edit field");
        editField.addActionListener(new EditFieldListener(tree, workspacePanel, project, state));
        popupMenu.add(editField);

        JMenuItem removeField = new JMenuItem("Remove field");
        removeField.addActionListener(new RemoveFieldListener(tree, project, workspacePanel));
        popupMenu.add(removeField);
    }
}

package net.shaidullin.code_maker.ui.toolwindow.tree.impl;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.ui.listener.AddPackageListener;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ElementNodeTreeMenuImpl extends AbstractNodeTreeMenuImpl<ElementNode> {
    private ElementNode selectedNode;

    public ElementNodeTreeMenuImpl(ApplicationState state) {
        super(state, new JPopupMenu());
    }

    @Override
    public boolean isNodeAllowed(Object node) {
        if (node == null) {
            return false;
        }

        return node instanceof ElementNode;
    }

    @Override
    public void setSelectedNode(@Nullable Object node) {
        this.selectedNode = (ElementNode) node;
    }

    @Nullable
    @Override
    public ElementNode getSelectedNode() {
        return this.selectedNode;
    }

    @Override
    public void initialize(JTree tree, Project project, WorkspacePanel workspacePanel) {
        if (this.initialized) {
            return;
        }
        super.initialize(tree, project, workspacePanel);

        JMenuItem createPackage = new JMenuItem("Create package");
        createPackage.addActionListener(new AddPackageListener(state, tree, project));
        popupMenu.add(createPackage);
    }
}

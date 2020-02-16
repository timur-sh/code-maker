package net.shaidullin.code_maker.ui.toolwindow.tree.impl;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.listener.AddLeafListener;
import net.shaidullin.code_maker.ui.listener.GeneratorListener;
import net.shaidullin.code_maker.ui.listener.RemovePackageListener;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PackageNodeTreeMenuImpl extends AbstractNodeTreeMenuImpl<PackageNode> {
    private PackageNode selectedNode;

    public PackageNodeTreeMenuImpl(ApplicationState state) {
        super(state, new JPopupMenu());
    }

    @Override
    public boolean isNodeAllowed(Object node) {
        if (node == null) {
            return false;
        }

        return node instanceof PackageNode;
    }

    @Override
    public void setSelectedNode(@Nullable Object node) {
        this.selectedNode = (PackageNode) node;
    }

    @Nullable
    @Override
    public PackageNode getSelectedNode() {
        return this.selectedNode;
    }

    @Override
    public void initialize(JTree tree, Project project, WorkspacePanel workspacePanel) {
        if (this.initialized) {
            return;
        }
        super.initialize(tree, project, workspacePanel);

        JMenuItem addClass = new JMenuItem("Add class");
        addClass.addActionListener(new AddLeafListener(project, tree, workspacePanel, state));
        popupMenu.add(addClass);
        popupMenu.addSeparator();

        JMenuItem removePackage = new JMenuItem("Remove package");
        removePackage.addActionListener(new RemovePackageListener(tree, project));
        popupMenu.add(removePackage);
        popupMenu.addSeparator();

        JMenuItem generate = new JMenuItem("Generate");
        generate.addActionListener(new GeneratorListener(tree, state));
        popupMenu.add(generate);
    }
}

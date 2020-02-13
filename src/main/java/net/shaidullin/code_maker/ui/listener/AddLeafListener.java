package net.shaidullin.code_maker.ui.listener;


import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class AddLeafListener implements ActionListener {
    private final Project project;
    private final JTree tree;
    private final WorkspacePanel workspacePanel;
    private final ApplicationState state;

    public AddLeafListener(Project project, JTree tree, WorkspacePanel workspacePanel, ApplicationState state) {
        this.project = project;
        this.tree = tree;
        this.workspacePanel = workspacePanel;
        this.state = state;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        PackageNode packageNode = (PackageNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();

        LeafNode leafNode = packageNode.getIntegrationElement()
            .assembleLeafTemplate(UUID.randomUUID());

        WorkspacePanelBody body = packageNode.getIntegrationElement()
            .createWorkspacePanelBody(leafNode, project, tree, state)
            .initialize();

        workspacePanel.render(body);
    }
}

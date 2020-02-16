package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditLeafListener implements ActionListener {
    private final ApplicationState state;
    private final JTree tree;
    private final Project project;
    private final WorkspacePanel workspacePanel;

    public EditLeafListener(ApplicationState state, JTree tree, Project project, WorkspacePanel workspacePanel) {
        this.state = state;
        this.tree = tree;
        this.project = project;
        this.workspacePanel = workspacePanel;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        LeafNode leafNode = (LeafNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();

        WorkspacePanelBody body = leafNode.getIntegrationElement()
            .createWorkspacePanelBody(project, tree, state)
            .initialize(leafNode);

        workspacePanel.render(body);
    }
}

package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditFieldListener implements ActionListener {
    private final JTree tree;
    private final WorkspacePanel workspacePanel;
    private final Project project;
    private final ApplicationState state;

    public EditFieldListener(JTree tree, WorkspacePanel workspacePanel, Project project, ApplicationState state) {
        this.tree = tree;
        this.workspacePanel = workspacePanel;
        this.project = project;
        this.state = state;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        FieldNode fieldNode = (FieldNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();

        WorkspacePanelBody body = fieldNode.getParent()
            .getIntegrationElement()
            .createWorkspacePanelBodyForNestedNode(fieldNode, project, tree, state);

        if (body == null) {
            throw new IllegalStateException(String.format("WorkspacePanelBody for class '%s' not found. Is it registered?",
                fieldNode.getClass().getSimpleName()));
        }

        body.initialize(fieldNode);
        workspacePanel.render(body);
    }
}

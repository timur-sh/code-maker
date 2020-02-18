package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.ReflectionUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RemovePackageListener implements ActionListener {
    private final JTree tree;
    private final Project project;
    private final ApplicationState state;
    private final WorkspacePanel workspacePanel;

    public RemovePackageListener(JTree tree, Project project, ApplicationState state, WorkspacePanel workspacePanel) {
        this.tree = tree;
        this.project = project;
        this.state = state;
        this.workspacePanel = workspacePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        int result = Messages.showYesNoDialog(
            "All classes will be deleted",
            "Delete Package?",
            Messages.getQuestionIcon()
        );

        if (result == 1) {
            return;
        }

        PackageNode packageNode = (PackageNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();

        if (packageNode != null) {
            Node node = ReflectionUtils.getNodeSupportedByWBP(packageNode, workspacePanel, state);
            if (node != null) {
                workspacePanel.closePanel(node);
            }

            String pathToMetadata = FileUtils.buildPathToMetadata(packageNode);
            File directory = new File(pathToMetadata);
            try {
                org.apache.commons.io.FileUtils.cleanDirectory(directory);

            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

            if (!directory.delete()) {
                Messages.showWarningDialog("Package path = " + pathToMetadata,
                    "Cannot Delete Package");
            }
            ToolWindowUtils.refresh(project);
        }
    }
}

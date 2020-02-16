package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.util.ui.tree.TreeUtil;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.ui.toolwindow.tree.CodeMakerTreeVisitor;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.utils.FileUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class RemoveLeafListener implements ActionListener {
    private final JTree tree;
    private final Project project;
    private final WorkspacePanel workspacePanel;

    public RemoveLeafListener(JTree tree, Project project, WorkspacePanel workspacePanel) {
        this.tree = tree;
        this.project = project;
        this.workspacePanel = workspacePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<TreePath> expandedPath = TreeUtil.collectExpandedPaths(tree);
        TreePath path = tree.getSelectionPath();

        int result = Messages.showYesNoDialog(
            "All fields will be deleted",
            "Delete?",
            Messages.getQuestionIcon()
        );

        if (result == 1) {
            return;
        }

        LeafNode leafNode = (LeafNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
        String pathToMetadata = FileUtils.buildLeafFileName(leafNode);
        File classFile = new File(pathToMetadata);
        if (!classFile.delete()) {
            Messages.showWarningDialog(String.format("Path to metadata '%s'. Check access", pathToMetadata),
                "Cannot Delete");
        }
        ToolWindowUtils.refresh(project);
        workspacePanel.closePanel(leafNode);

        TreeUtil.expand(tree, new CodeMakerTreeVisitor(expandedPath), (TreePath p) -> {
        });
    }
}

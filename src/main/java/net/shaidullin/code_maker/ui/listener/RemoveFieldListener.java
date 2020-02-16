package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import com.intellij.util.ui.tree.TreeUtil;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.ui.toolwindow.tree.CodeMakerTreeVisitor;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import net.shaidullin.code_maker.utils.NodeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveFieldListener implements ActionListener {
    private final JTree tree;
    private final Project project;
    private final WorkspacePanel workspacePanel;

    public RemoveFieldListener(JTree tree, Project project, WorkspacePanel workspacePanel) {
        this.tree = tree;
        this.project = project;
        this.workspacePanel = workspacePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<TreePath> expandedPath = TreeUtil.collectExpandedPaths(tree);
        TreePath path = tree.getSelectionPath();

        FieldNode fieldNode = (FieldNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
        LeafNode leafNode = fieldNode.getParent();

        leafNode.removeNestedNode(fieldNode);

        NodeUtils.writeLeafMetadata(leafNode, leafNode.getMetadata());
        ToolWindowUtils.refresh(project);
        workspacePanel.closePanel(fieldNode);
        TreeUtil.expand(tree, new CodeMakerTreeVisitor(expandedPath), (TreePath p) -> {
        });
    }
}

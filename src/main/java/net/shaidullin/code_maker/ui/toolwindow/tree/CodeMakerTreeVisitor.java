package net.shaidullin.code_maker.ui.toolwindow.tree;

import com.intellij.ui.tree.TreeVisitor;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.List;


public class CodeMakerTreeVisitor implements TreeVisitor {
    private final List<TreePath> expandedPath;

    public CodeMakerTreeVisitor(List<TreePath> expandedPath) {
        this.expandedPath = expandedPath;
    }

    @NotNull
    @Override
    public Action visit(@NotNull TreePath path) {
        DefaultMutableTreeNode inspectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();

        for (TreePath treePath : expandedPath) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            if (node.getUserObject() != null && node.getUserObject().equals(inspectedNode.getUserObject())) {
                return Action.CONTINUE;
            }
        }

        return Action.SKIP_CHILDREN;
    }
}

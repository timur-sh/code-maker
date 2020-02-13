package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;
import net.shaidullin.code_maker.utils.FileUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RemovePackageListener implements ActionListener {
    private final JTree tree;
    private final Project project;

    public RemovePackageListener(JTree tree, Project project) {
        this.tree = tree;
        this.project = project;
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

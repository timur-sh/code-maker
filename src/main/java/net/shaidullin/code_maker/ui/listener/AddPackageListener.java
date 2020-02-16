package net.shaidullin.code_maker.ui.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;
import net.shaidullin.code_maker.ui.validator.PackageValidator;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPackageListener implements ActionListener {
    private final ApplicationState state;
    private final JTree tree;
    private final Project project;

    public AddPackageListener(ApplicationState state, JTree tree, Project project) {
        this.state = state;
        this.tree = tree;
        this.project = project;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        ElementNode elementNode = (ElementNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();

        if (elementNode != null) {
            String packageName = JOptionPane.showInputDialog("Input package name");
            state.refreshState();

            PackageValidator packageValidator = new PackageValidator(state.getPackages().get(elementNode));
            packageValidator.validate(packageName);
            if (!packageValidator.isValid()) {
                Messages.
                    showWarningDialog(project, String.join(", ", packageValidator.getResult()),
                        "Validation Error");
                return;
            }

            PackageNode packageNode = NodeUtils.addPackage(elementNode, packageName);
            if (packageNode == null) {
                Messages.showWarningDialog(project, String.format(
                    "Cannot add folder '%s' for element '%s'",
                    packageName,
                    elementNode.getSystemName()
                ), "Error");

            } else {
                ToolWindowUtils.refresh(project);
            }
        }
    }
}

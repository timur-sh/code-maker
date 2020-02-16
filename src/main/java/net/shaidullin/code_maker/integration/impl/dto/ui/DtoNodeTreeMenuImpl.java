package net.shaidullin.code_maker.integration.impl.dto.ui;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.impl.dto.ui.listener.AddDtoFieldListener;
import net.shaidullin.code_maker.ui.listener.EditLeafListener;
import net.shaidullin.code_maker.ui.listener.RemoveLeafListener;
import net.shaidullin.code_maker.ui.toolwindow.tree.impl.AbstractNodeTreeMenuImpl;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DtoNodeTreeMenuImpl extends AbstractNodeTreeMenuImpl<DtoNode> {
    private DtoNode selectedNode;

    public DtoNodeTreeMenuImpl(ApplicationState state, JPopupMenu popupMenu) {
        super(state, popupMenu);
    }

    @Override
    public boolean isNodeAllowed(Object node) {
        return node instanceof DtoNode;
    }

    @Override
    public void setSelectedNode(@Nullable Object node) {
        this.selectedNode = (DtoNode) node;
    }

    @Nullable
    @Override
    public DtoNode getSelectedNode() {
        return this.selectedNode;
    }

    @Override
    public void initialize(JTree tree, Project project, WorkspacePanel workspacePanel) {
        if (this.initialized) {
            return;
        }
        super.initialize(tree, project, workspacePanel);

        JMenuItem editClass = new JMenuItem("Edit class");
        editClass.addActionListener(new EditLeafListener(state, tree, project, workspacePanel));
        popupMenu.add(editClass);

        JMenuItem removeClass = new JMenuItem("Remove class");
        removeClass.addActionListener(new RemoveLeafListener(tree, project, workspacePanel));
        popupMenu.add(removeClass);
        popupMenu.addSeparator();

        JMenuItem addField = new JMenuItem("Add field");
        addField.addActionListener(new AddDtoFieldListener(project, state, tree, workspacePanel));
        popupMenu.add(addField);
    }
}

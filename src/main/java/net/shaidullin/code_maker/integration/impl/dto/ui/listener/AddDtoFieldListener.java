package net.shaidullin.code_maker.integration.impl.dto.ui.listener;

import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.node.FieldNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.impl.dto.ui.DtoFieldWBPImpl;
import net.shaidullin.code_maker.integration.impl.dto.ui.DtoWBPImpl;
import net.shaidullin.code_maker.ui.toolwindow.workspace.WorkspacePanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class AddDtoFieldListener implements ActionListener {
    private final Project project;
    private final ApplicationState state;
    private final JTree tree;
    private final WorkspacePanel workspacePanel;

    public AddDtoFieldListener(Project project, ApplicationState state, JTree tree, WorkspacePanel workspacePanel) {
        this.project = project;
        this.state = state;
        this.tree = tree;
        this.workspacePanel = workspacePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath path = tree.getSelectionPath();

        DtoNode leafNode = (DtoNode) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
        FieldMetadata fieldMetadata = new FieldMetadata();
        FieldNode<DtoNode, FieldMetadata> fieldNode = new FieldNode<>(fieldMetadata, leafNode);


        DtoFieldWBPImpl wbp = new DtoFieldWBPImpl(project, tree, state);
        wbp.initialize(fieldNode);

        fieldMetadata.setUuid(UUID.randomUUID());
        workspacePanel.render(wbp);
    }
}

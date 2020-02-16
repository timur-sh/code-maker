package net.shaidullin.code_maker.ui.toolwindow.workspace.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import net.shaidullin.code_maker.core.Pair;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.ui.ChooseParentDialog;
import net.shaidullin.code_maker.ui.validator.NameValidator;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.UUID;


public abstract class LeafWBPImpl<N extends LeafNode> extends WorkspacePanelBody<N> {
    protected N leafNode;

    //Name, UUID
    protected Pair<String, String> parentData = new Pair<>();
    protected JBTextField parent;
    private JButton btnBrowse;

    public LeafWBPImpl(Project project, JTree tree, ApplicationState state) {
        super(project, tree, state);
    }

    @Override
    public boolean isInProcess(N lefNode) {
        return lefNode.getSystemName().equals(leafNode.getSystemName());
    }

    protected void renderParentBrowse(GridBagConstraints constraints, UUID parentUID) {
        parent = new JBTextField();
        parent.setEnabled(false);

        if (parentUID != null) {
            initializeParent(parentUID);
        }

        btnBrowse = new JButton("Browse");
        btnBrowse.addActionListener(new BrowseAction(leafNode, this, state));

        this.add(new JBLabel("Parent"), constraints);

        GridBagConstraints fieldConstraint = (GridBagConstraints) constraints.clone();
        fieldConstraint.gridx = 1;
        fieldConstraint.gridwidth = 2;
        fieldConstraint.fill = GridBagConstraints.HORIZONTAL;
        this.add(parent, fieldConstraint);

        GridBagConstraints btnConstraint = (GridBagConstraints) constraints.clone();
        btnConstraint.gridx = 3;
        btnConstraint.gridwidth = 1;
        this.add(btnBrowse, btnConstraint);
    }

    protected void initializeParent(UUID parentUID) {
        state.refreshState();

        LeafNode leafNode = state.getLeafByUID(parentUID);
        if (LeafNodeUtils.UNDEFINED_CLASS.equals(leafNode)) {
            parentData = new Pair<>(leafNode.getSystemName(), parentUID.toString());
            parent.setText(leafNode.getSystemName());

        } else {
            LeafMetadata metadata = leafNode.getMetadata();
            parentData = new Pair<>(metadata.getSystemName(), metadata.getUuid().toString());
            parent.setText(metadata.getSystemName());
        }
    }

    @Override
    protected boolean validateModel() {
        String fileName = domainName.getText();

        if (StringUtils.isEmpty(fileName)) {
            Messages.showWarningDialog(project, "Name can't be empty", "Validation");
            return false;
        }

        if (StringUtils.isEmpty(fieldDescription.getText())) {
            Messages.showWarningDialog(project, "Description can't be empty ", "Validation");
            return false;
        }

        NameValidator validator = new NameValidator();
        validator.validate(fileName);
        if (!validator.isValid()) {
            Messages.showWarningDialog(project,
                String.join(", ", validator.getResult()),
                "Validation");
            return false;
        }

        if (oldName != null && !oldName.equals(fileName)) {
            return validateFileExists(fileName);
        }

        return true;
    }

    @Override
    protected void save() {
        LeafMetadata metadata = leafNode.getMetadata();

        if (!StringUtils.isEmpty(oldName) && !oldName.equals(metadata.getSystemName())) {
            // if name is changed, remove old file and create a new one
            String pathToMetadata = FileUtils.buildPathToMetadata(leafNode.getParent());

            File file = new File(FileUtils.buildLeafFileName(pathToMetadata, oldName));
            if (!file.delete()) {
                Messages.showErrorDialog(project, file.getAbsolutePath(), "Can't delete file");
                return;
            }

        }

        NodeUtils.writeLeafMetadata(leafNode, leafNode.getMetadata());
    }

    private boolean validateFileExists(String newFileName) {
        String pathToMetadata = FileUtils.buildPathToMetadata(leafNode.getParent());
        File file = new File(FileUtils.buildLeafFileName(pathToMetadata, newFileName));
        if (file.exists()) {
            Messages.showErrorDialog(project, "File already exists", "Error");
            return false;
        }

        return true;
    }

    private class BrowseAction extends AbstractAction {
        private final JPanel jPanel;
        private final ApplicationState state;
        private final LeafNode leafNode;

        BrowseAction(LeafNode leafNode, JPanel jPanel, ApplicationState state) {
            this.leafNode = leafNode;
            this.jPanel = jPanel;
            this.state = state;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Frame frame = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, jPanel);
            ChooseParentDialog parentDialog = new ChooseParentDialog(leafNode, frame, state);
            parentDialog.createDialog(parent, parentData);
        }
    }
}

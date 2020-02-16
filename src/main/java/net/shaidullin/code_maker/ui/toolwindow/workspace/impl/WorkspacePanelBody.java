package net.shaidullin.code_maker.ui.toolwindow.workspace.impl;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.tree.TreeUtil;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.ui.toolwindow.tree.CodeMakerTreeVisitor;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class WorkspacePanelBody<N extends Node> extends JPanel {
    protected static final Insets COMPONENT_INSETS = JBUI.insets(4);
    protected String oldName;

    protected final Project project;
    private final JTree tree;

    protected final ApplicationState state;

    protected JBTextField domainName;
    protected JTextField fieldDescription;
    private JButton btnSave;
    private JButton btnReset;

    public WorkspacePanelBody(Project project, JTree tree, ApplicationState state) {
        super(new GridBagLayout());

        this.project = project;
        this.tree = tree;
        this.state = state;
    }

    /**
     * @return name of workspace
     */
    public abstract String getWorkspaceName();

    public abstract WorkspacePanelBody initialize(N node);

    public abstract boolean isInProcess(N node);

    protected void renderToolbar(GridBagConstraints constraints) {
        btnSave = new JButton("Save");
        btnSave.addActionListener(new SaveListener());
        btnReset = new JButton("Reset");
        btnReset.addActionListener(new ResetListener());

        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.add(Box.createHorizontalStrut(4));
        toolBar.add(btnSave);
        toolBar.add(Box.createHorizontalStrut(4));
        toolBar.add(btnReset);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);
        toolBar.setBorder(null);
        this.add(toolBar, constraints);
    }

    protected void initialize(String name, String description) {
        this.add(new JBLabel("Name"),
            new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));

        domainName = new JBTextField();
        domainName.setText(name);
        this.add(domainName,
            new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        this.add(new JBLabel("Description"),
            new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));

        fieldDescription = new JTextField();
        fieldDescription.setText(description);
        this.add(fieldDescription,
            new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));
    }

    protected abstract void save();

    protected void afterSuccessSave() {
    }

    protected abstract boolean validateModel();

    protected abstract void apply();

    protected abstract void reset();

    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (validateModel()) {
                apply();

                List<TreePath> expandedPath = TreeUtil.collectExpandedPaths(tree);

                save();
                afterSuccessSave();
                ToolWindowUtils.refresh(project);

                TreeUtil.expand(tree, new CodeMakerTreeVisitor(expandedPath), (TreePath p) -> {
                });
            }
        }
    }

    private class ResetListener implements ActionListener {
        ResetListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
        }
    }

}

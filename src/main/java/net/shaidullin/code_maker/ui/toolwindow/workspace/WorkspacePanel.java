package net.shaidullin.code_maker.ui.toolwindow.workspace;

import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;

import javax.swing.*;
import java.awt.*;

public class WorkspacePanel {
    private static final Dimension DECORATOR_DIMENSIONS = new Dimension(500, 100);


    private JPanel mainPanel;
    private WorkspacePanelBody body;

    private JBScrollPane jbScrollPane;
    private TitledSeparator title;
    private JButton btnCancel;

    public WorkspacePanel() {
    }

    public JPanel createPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(JBUI.Borders.empty(8));
        mainPanel.setMinimumSize(DECORATOR_DIMENSIONS);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener((l) -> {
            if (body != null) {
                deleteBaseImplPanel();
            }
        });

        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.add(Box.createHorizontalStrut(4));
        toolBar.add(btnCancel);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.setFloatable(false);
        toolBar.setOpaque(false);
        toolBar.setBorder(null);
        mainPanel.add(toolBar, BorderLayout.SOUTH);

        return mainPanel;
    }

    /**
     * Очистить (закрыть) панель редактирования
     *
     * @param node
     */
    @SuppressWarnings("unchecked")
    public void closePanel(Node node) {
        if (body != null && body.isInProcess(node)) {
            deleteBaseImplPanel();
        }
    }

    public void render(WorkspacePanelBody newBody) {
        if (jbScrollPane != null) {
            mainPanel.remove(jbScrollPane);
        }

        this.body = newBody;
        jbScrollPane = new JBScrollPane(this.body);
        title = new TitledSeparator(newBody.getWorkspaceName());
        mainPanel.add(title, BorderLayout.NORTH);

        mainPanel.add(jbScrollPane, BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.repaint();
    }

    private void deleteBaseImplPanel() {
        if (jbScrollPane != null) {
            mainPanel.remove(jbScrollPane);
            mainPanel.remove(title);
            mainPanel.validate();
            mainPanel.repaint();

            body = null;
            jbScrollPane = null;
            title = null;
        }
    }

}

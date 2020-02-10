package net.shaidullin.code_maker.ui.toolwindow;

import com.intellij.openapi.project.Project;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.JBUI;
import net.shaidullin.code_maker.core.config.ApplicationState;

import javax.swing.*;
import java.awt.*;

public class WorkspacePanel {
    private static final Dimension DECORATOR_DIMENSIONS = new Dimension(500, 100);

    private final Project project;
    private final ApplicationState state;
    private JPanel mainPanel;
    //    private BaseImplEditPanel baseImplEditPanel;
    private JBScrollPane jbScrollPane;
    private TitledSeparator title;
    private JButton btnCancel;

    public WorkspacePanel(Project project, ApplicationState state) {
        this.project = project;
        this.state = state;
    }

    public JPanel createPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(JBUI.Borders.empty(8));
        mainPanel.setMinimumSize(DECORATOR_DIMENSIONS);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener((l) -> {
//            if (baseImplEditPanel != null) {
//                deleteBaseImplPanel();
//            }
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

//    /**
//     * Очистить (закрыть) панель редактирования
//     * @param leafNode
//     */
//    public void clearBasePanelBeforeDelete(LeafNode leafNode) {
//        if (baseImplEditPanel!= null && baseImplEditPanel.isInProcess(leafNode)) {
//            deleteBaseImplPanel();
//        }
//    }
//
//    public void renderEntityPanel(EntityMetadata metadata, JTree tree) {
//        title = new TitledSeparator("Class metadata");
//        renderPanelInternal(new EntityEditPanelImpl(metadata, project, tree, title, state));
//    }
//
//    public void renderDomainPanel(DomainMetadata metadata, JTree tree) {
//        title = new TitledSeparator("Class metadata");
//        renderPanelInternal(new DomainEditPanel(metadata, project, tree, title, state));
//    }
//
//    public void renderServicePanel(AbstractMetadata metadata, JTree tree) {
//        title = new TitledSeparator("Class metadata");
//        renderPanelInternal(new ServicePanel(metadata, project, tree, title, state));
//    }
//
//    public void renderFieldPanel(FieldMetadata metadata, ClassNode classNode, JTree tree) {
//        title = new TitledSeparator("Field metadata");
//        renderPanelInternal(new FieldEditPanel(metadata, classNode, project, tree, title, state));
//    }
//
//    public void renderArgumentPanel(OperationMetadata operationMetadata,  FieldMetadata fieldMetadata, ClassNode classNode, JTree tree) {
//        title = new TitledSeparator("Field metadata");
//        renderPanelInternal(new ArgumentEditPanel(operationMetadata, fieldMetadata, classNode, project, tree, title, state));
//    }
//
//    public void renderOperationPanel(OperationMetadata metadata, ClassNode classNode, JTree tree) {
//        title = new TitledSeparator("Operation metadata");
//        renderPanelInternal(new OperationEditPanel(metadata, classNode, project, tree, title, state));
//    }

//    private void renderPanelInternal(BaseImplEditPanel newPanel) {
//        if (jbScrollPane != null) {
//            mainPanel.remove(jbScrollPane);
//        }
//
//        baseImplEditPanel = newPanel;
//        jbScrollPane = new JBScrollPane(baseImplEditPanel);
//        mainPanel.add(title, BorderLayout.NORTH);
//
//        mainPanel.add(jbScrollPane, BorderLayout.CENTER);
//        mainPanel.validate();
//        mainPanel.repaint();
//    }

    private void deleteBaseImplPanel() {
        if (jbScrollPane != null) {
            mainPanel.remove(jbScrollPane);
            mainPanel.remove(title);
            mainPanel.validate();
            mainPanel.repaint();
//            baseImplEditPanel = null;
            jbScrollPane = null;
            title = null;
        }
    }

}

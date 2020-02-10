package net.shaidullin.code_maker.ui.toolwindow;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.JBUI;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.ModuleNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CodeMakerToolWindowPanel extends JPanel {
    private static final Logger LOG = Logger.getInstance(CodeMakerToolWindowPanel.class);

    public static final String TOOL_WINDOW_ID = "Code-maker";
    private static final String GENERATOR_ACTIONS_GROUP = "GeneratorActions";

    private final Project project;
    private final ApplicationState state;
    private NodeTreePanel nodeTreePanel;
    private WorkspacePanel workspacePanel;


    private ModuleNode activeModule;
    private ElementNode activeElement;
    private final ComboBox<ModuleNode> modulesComboBox = new ComboBox<>();
    private final DefaultComboBoxModel<ModuleNode> modulesModel = new DefaultComboBoxModel<>();

    private final ComboBox<ElementNode> elementComboBox = new ComboBox<>();
    private final DefaultComboBoxModel<ElementNode> elementModel = new DefaultComboBoxModel<>();

    public CodeMakerToolWindowPanel(Project project, ApplicationState state) {
        super(new BorderLayout());

        this.project = project;
        this.state = state;

        ActionGroup generatorActionsGroup = (ActionGroup) ActionManager.getInstance().getAction(GENERATOR_ACTIONS_GROUP);
        ActionToolbar generatorToolbar = ActionManager.getInstance().createActionToolbar(TOOL_WINDOW_ID, generatorActionsGroup, false);

        Box toolBarBox = Box.createHorizontalBox();
        toolBarBox.add(generatorToolbar.getComponent());

        setBorder(JBUI.Borders.empty(1));
        add(toolBarBox, BorderLayout.WEST);
        add(createToolPanel(), BorderLayout.CENTER);

        generatorToolbar.getComponent().setVisible(true);
    }

    private JPanel createToolPanel() {
        initializeComboBoxes();

        workspacePanel = new WorkspacePanel(project, state);
        nodeTreePanel = new NodeTreePanel(state, project, workspacePanel);

        JSplitPane jSplitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            nodeTreePanel.createPanel(activeElement),
            workspacePanel.createPanel()
        );

        JLabel progressLabel = new JLabel(" ");
        JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);
        progressBar.setMinimum(0);
        Dimension progressBarSize = new Dimension(100, progressBar.getPreferredSize().height);
        progressBar.setMinimumSize(progressBarSize);
        progressBar.setPreferredSize(progressBarSize);
        progressBar.setMaximumSize(progressBarSize);

        JToolBar progressPanel = new JToolBar(JToolBar.HORIZONTAL);
        progressPanel.add(Box.createHorizontalStrut(4));
        progressPanel.add(new JLabel("Module: "));
        progressPanel.add(Box.createHorizontalStrut(4));
        progressPanel.add(modulesComboBox);
        progressPanel.addSeparator();
        progressPanel.add(Box.createHorizontalStrut(18));
        progressPanel.add(new JLabel("Element: "));
        progressPanel.add(Box.createHorizontalStrut(4));
        progressPanel.add(elementComboBox);
        progressPanel.add(Box.createHorizontalStrut(4));
        progressPanel.addSeparator();
        progressPanel.add(Box.createHorizontalStrut(4));
        progressPanel.add(progressLabel);
        progressPanel.add(Box.createHorizontalGlue());
        progressPanel.setFloatable(false);
        progressPanel.setOpaque(false);
        progressPanel.setBorder(null);

        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.add(jSplitPane, BorderLayout.CENTER);
        toolPanel.add(progressPanel, BorderLayout.NORTH);

        return toolPanel;
    }

    private void initializeComboBoxes() {
        modulesComboBox.setModel(modulesModel);
        int preferredHeight = modulesComboBox.getPreferredSize().height;
        modulesComboBox.setPreferredSize(new Dimension(250, preferredHeight));
        modulesComboBox.setMaximumSize(new Dimension(350, preferredHeight));
        modulesComboBox.addActionListener((ActionEvent e) -> {
            activeModule = (ModuleNode) ((ComboBox) e.getSource()).getSelectedItem();
            refreshElements();
        });

        elementComboBox.setModel(elementModel);
        elementComboBox.setPreferredSize(new Dimension(250, preferredHeight));
        elementComboBox.setMaximumSize(new Dimension(350, preferredHeight));
        elementComboBox.addActionListener((ActionEvent e) -> {
            activeElement = (ElementNode) ((ComboBox) e.getSource()).getSelectedItem();
            if (nodeTreePanel != null) {
                nodeTreePanel.refresh(activeElement);
            }
        });

        refreshAll();
    }

    /**
     * Обновиь все модели
     */
    public void refreshAll() {
        ModuleNode selectedModule = (ModuleNode) modulesComboBox.getSelectedItem();
        ElementNode selectedElement = (ElementNode) elementComboBox.getSelectedItem();

        modulesComboBox.removeAll();
        modulesModel.removeAllElements();
        for (ModuleNode module : state.getModules()) {
            if (activeModule == null) {
                activeModule = module;
            }
            modulesModel.addElement(module);
        }

        refreshElements();
        if (nodeTreePanel != null) {
            nodeTreePanel.refresh(activeElement);
        }

        modulesComboBox.setSelectedItem(selectedModule);
        elementComboBox.setSelectedItem(selectedElement);
    }


    /**
     * Обновить элементы выбранной модели
     */
    private void refreshElements() {
        elementComboBox.removeAll();
        elementModel.removeAllElements();
        if (activeModule != null && state.getElements() != null) {
            if (state.getElements().get(activeModule) == null) {
                return;
            }

            for (ElementNode elementNode : state.getElements().get(activeModule)) {
                if (activeElement == null) {
                    activeElement = elementNode;
                }

                elementModel.addElement(elementNode);
            }
        }

    }
}

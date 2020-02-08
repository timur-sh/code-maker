package net.shaidullin.code_maker.ui.settings;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextComponentAccessor;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.*;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.validate.ModuleValidator;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.JsonUtils;
import net.shaidullin.code_maker.utils.NodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class CodeMakerSettingsPanel {
    private static final String DEFAULT_GENERATE_PATH = "generate_code";

    private static final Insets COMPONENT_INSETS = JBUI.insets(4);
    private static final Dimension DECORATOR_DIMENSIONS = new Dimension(300, 50);

    private final Project project;
    private final ApplicationState state;

    private TextFieldWithBrowseButton generatePath;
    private SettingsTableModel cmTableModel;
    private JBTable moduleTable;

    public CodeMakerSettingsPanel(Project project, ApplicationState state) {
        this.project = project;
        this.state = state;
    }

    public JPanel createPanel() {
        state.refreshState();

        JPanel wholePanel = new JPanel(new BorderLayout());
        wholePanel.setBorder(JBUI.Borders.empty(8, 8, 4, 8));

        JPanel commonPanel = new JPanel(new GridBagLayout());
        commonPanel.setOpaque(false);

        commonPanel.add(new TitledSeparator("Common settings"),
            new GridBagConstraints(0, 0, 4, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        commonPanel.add(new JLabel("Folder for generated files:"),
            new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));
        generatePath = new TextFieldWithBrowseButton();
        generatePath.addBrowseFolderListener(
            "",
            "Location for generated files",
            project,
            FileChooserDescriptorFactory.createSingleFolderDescriptor(),
            TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT
        );
        setupTextFieldDefaultValue(generatePath.getTextField());

        commonPanel.add(generatePath,
            new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        commonPanel.add(buildClassPathPanel(),
            new GridBagConstraints(0, 2, 4, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, COMPONENT_INSETS, 0, 0));
        wholePanel.add(commonPanel, BorderLayout.CENTER);

        return wholePanel;
    }

    private void setupTextFieldDefaultValue(@NotNull JTextField textField) {
        String defaultShellPath = null;
        if (!StringUtil.isEmptyOrSpaces(state.getGeneratePath())) {
            defaultShellPath = state.getGeneratePath();
        }

        if (StringUtil.isEmptyOrSpaces(defaultShellPath)) {
            return;
        }

        textField.setText(defaultShellPath);
    }

    private JPanel buildClassPathPanel() {
        cmTableModel = new SettingsTableModel(state);
        moduleTable = new JBTable(cmTableModel);

        moduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        moduleTable.setStriped(true);
        moduleTable.getTableHeader().setReorderingAllowed(false);

        ToolbarDecorator tableDecorator = ToolbarDecorator.createDecorator(moduleTable);
        tableDecorator.setAddAction(new AddLocationAction(moduleTable));
        tableDecorator.setRemoveAction(new RemoveLocationAction());
        tableDecorator.setRemoveActionUpdater(new DisableForDefaultUpdater());
        tableDecorator.setPreferredSize(DECORATOR_DIMENSIONS);

        JPanel modulePanel = new JPanel(new BorderLayout());
        modulePanel.add(new TitledSeparator("Module settings"), BorderLayout.NORTH);
        modulePanel.add(tableDecorator.createPanel(), BorderLayout.CENTER);

        return modulePanel;
    }

    public boolean isModified() {
        return !Objects.equals(generatePath.getText(), state.getGeneratePath())
            || cmTableModel.isModified(state);
    }

    public void apply() {
        state.setGeneratePath(generatePath.getText());
        cmTableModel.apply(state);

        state.refreshState();
    }

    public void reset() {
        setupTextFieldDefaultValue(generatePath.getTextField());
        cmTableModel.reset(state);

        state.refreshState();
    }

    private void showError(Project project, String formattedMessage) {
        Messages.showErrorDialog(project, formattedMessage, "");
    }

    private String getDefaultGeneratePath() {
        return Paths.get(new File(project.getBasePath(), DEFAULT_GENERATE_PATH).getAbsolutePath())
            .normalize()
            .toAbsolutePath()
            .toFile()
            .getAbsolutePath();
    }

    abstract class ToolbarAction extends AbstractAction implements AnActionButtonRunnable {
        private static final long serialVersionUID = 7091312536206510956L;

        @Override
        public void run(AnActionButton anActionButton) {
            actionPerformed(null);
        }
    }

    /**
     * Process the addition of a configuration location.
     */
    private class AddLocationAction extends ToolbarAction {
        private static final long serialVersionUID = -7266120887003483814L;
        private final JBTable moduleTable;

        AddLocationAction(JBTable moduleTable) {
            this.moduleTable = moduleTable;
            putValue(Action.NAME, "Add module");
            putValue(Action.SHORT_DESCRIPTION, "Add module");
            putValue(Action.LONG_DESCRIPTION, "Add module");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            FileChooserDescriptor descriptor = new ExtensionFileChooserDescriptor(
                (String) getValue(Action.NAME),
                (String) getValue(Action.SHORT_DESCRIPTION),
                false,
                "json"
            );

            VirtualFile toSelect;
            String configFilePath = getDefaultGeneratePath();
            if (!StringUtils.isBlank(configFilePath)) {
                toSelect = LocalFileSystem.getInstance().findFileByPath(configFilePath);
            } else {
                toSelect = project.getBaseDir();
            }

            VirtualFile chosen = FileChooser.chooseFile(descriptor, project, toSelect);
            if (chosen != null) {

                ModuleNode moduleNode = new ModuleNode();

                File jsonFile = new File(chosen.getPath());
                moduleNode.setSystemName(FileUtils.getFileName(jsonFile.getName()));
                moduleNode.setRootMetadataPath(jsonFile.getParent());
                NodeUtils.reloadMetadata(moduleNode);

                ModuleValidator validator = new ModuleValidator();
                validator.validate(moduleNode);

                if (!validator.isValid()) {
                    showError(project, StringUtils.join(validator.getResult(), ", "));
                    return;
                }

                if (cmTableModel.getModuleNodes().contains(moduleNode)) {
                    Messages.showWarningDialog(project, "Module was already added", "Cannot Add Module");

                } else {
                    cmTableModel.addModule(moduleNode);
                    moduleTable.revalidate();
                    moduleTable.repaint();
                }
            }
        }
    }

    /**
     * Process the removal of a configuration location.
     */
    private class RemoveLocationAction extends ToolbarAction {
        private static final long serialVersionUID = -799542186049804472L;

        RemoveLocationAction() {
            putValue(Action.NAME, "Remove module");
            putValue(Action.SHORT_DESCRIPTION, "Remove module");
            putValue(Action.LONG_DESCRIPTION, "Remove module");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = moduleTable.getSelectedRow();
            if (selectedIndex == -1) {
                return;
            }

            cmTableModel.removeModuleAt(selectedIndex);
            moduleTable.revalidate();
            moduleTable.repaint();
        }
    }

    private class DisableForDefaultUpdater implements AnActionButtonUpdater {
        @Override
        public boolean isEnabled(AnActionEvent e) {
            int selectedItem = moduleTable.getSelectedRow();
            return selectedItem >= 0;
        }
    }
}

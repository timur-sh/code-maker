package net.shaidullin.code_maker;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.ui.settings.CodeMakerSettingsPanel;

import javax.swing.*;


/**
 * The "configurable component" required by IntelliJ IDEA to provide a Swing form for inclusion into the 'Settings'
 * dialog. Registered in {@code plugin.xml} as a {@code projectConfigurable} extension.
 */
public class CodeMakerConfigurable implements Configurable {
    private static final Logger LOG = Logger.getInstance(CodeMakerConfigurable.class);

    private final CodeMakerSettingsPanel settingsPanel;

    public CodeMakerConfigurable(Project project, ApplicationState state) {
        this.settingsPanel = new CodeMakerSettingsPanel(project, state);
    }

    @Override
    public String getDisplayName() {
        return "Code Maker";
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        return settingsPanel.createPanel();
    }

    @Override
    public boolean isModified() {
        return settingsPanel.isModified();
    }

    @Override
    public void apply() {
        settingsPanel.apply();
    }


    @Override
    public void reset() {
        settingsPanel.reset();
    }
}

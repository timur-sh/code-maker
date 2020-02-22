package net.shaidullin.code_maker.integration.impl.dto;

import com.intellij.ui.TitledSeparator;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.integration.IntegrationElementSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static net.shaidullin.code_maker.ui.settings.CodeMakerSettingsPanel.COMPONENT_INSETS;
import static net.shaidullin.code_maker.ui.settings.CodeMakerSettingsPanel.DECORATOR_DIMENSIONS;

public class DtoElementSettings implements IntegrationElementSettings {
    public final static String CACHE_STORAGE_INTERFACE_KEY = "cache-storage-interface-key";
    public final static String DTO_CACHE_INTERFACE_KEY = "dto-cache-interface-key";
    public final static String DTO_PARENT_INTERFACE_KEY = "dto-parent-interface-key";

    private final ApplicationState state;

    private JTextField cacheStorageInterfaceJText;
    private JTextField dtoCacheInterfaceJText;
    private JTextField dtoParentInterfaceJText;


    public DtoElementSettings(ApplicationState state) {
        this.state = state;
    }

    @Override
    public JPanel createPanel() {
        JPanel commonPanel = new JPanel(new GridBagLayout());
        commonPanel.setMinimumSize(DECORATOR_DIMENSIONS);

        commonPanel.add(new TitledSeparator("DTO settings:"),
            new GridBagConstraints(0, 0, 4, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        commonPanel.add(new JLabel("Cache storage interface:"),
            new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));
        cacheStorageInterfaceJText = new JTextField();
        cacheStorageInterfaceJText.setText(state.getConfigurationValue(CACHE_STORAGE_INTERFACE_KEY));
        commonPanel.add(cacheStorageInterfaceJText,
            new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        commonPanel.add(new JLabel("DTO cache interface:"),
            new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));
        dtoCacheInterfaceJText = new JTextField();
        dtoCacheInterfaceJText.setText(state.getConfigurationValue(DTO_CACHE_INTERFACE_KEY));
        commonPanel.add(dtoCacheInterfaceJText,
            new GridBagConstraints(1, 2, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));


        commonPanel.add(new JLabel("DTO parent interface:"),
            new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, COMPONENT_INSETS, 0, 0));
        dtoParentInterfaceJText = new JTextField();
        dtoParentInterfaceJText.setText(state.getConfigurationValue(DTO_PARENT_INTERFACE_KEY));
        commonPanel.add(dtoParentInterfaceJText,
            new GridBagConstraints(1, 3, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, COMPONENT_INSETS, 0, 0));

        return commonPanel;
    }

    @Override
    public boolean isModified() {
        return !Objects.equals(cacheStorageInterfaceJText.getText(), state.getConfigurationValueOrEmpty(CACHE_STORAGE_INTERFACE_KEY))
            || !Objects.equals(dtoCacheInterfaceJText.getText(), state.getConfigurationValueOrEmpty(DTO_CACHE_INTERFACE_KEY))
            || !Objects.equals(dtoParentInterfaceJText.getText(), state.getConfigurationValueOrEmpty(DTO_PARENT_INTERFACE_KEY));
    }

    @Override
    public void apply() {
        state.setConfigurationValue(CACHE_STORAGE_INTERFACE_KEY, cacheStorageInterfaceJText.getText());
        state.setConfigurationValue(DTO_CACHE_INTERFACE_KEY, dtoCacheInterfaceJText.getText());
        state.setConfigurationValue(DTO_PARENT_INTERFACE_KEY, dtoParentInterfaceJText.getText());
    }

    @Override
    public void reset() {
        cacheStorageInterfaceJText.setText(state.getConfigurationValue(CACHE_STORAGE_INTERFACE_KEY));
        dtoCacheInterfaceJText.setText(state.getConfigurationValue(DTO_CACHE_INTERFACE_KEY));
        dtoParentInterfaceJText.setText(state.getConfigurationValue(DTO_PARENT_INTERFACE_KEY));

    }
}

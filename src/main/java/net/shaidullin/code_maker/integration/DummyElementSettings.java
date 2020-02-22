package net.shaidullin.code_maker.integration;

import jdk.internal.jline.internal.Nullable;

import javax.swing.*;

/**
 * Dummy setting. There are some integration elements that do not
 * allow a custom settings
 */
public class DummyElementSettings implements IntegrationElementSettings {
    @Nullable
    @Override
    public JPanel createPanel() {
        return null;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() {

    }

    @Override
    public void reset() {

    }
}

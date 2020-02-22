package net.shaidullin.code_maker.integration;

import jdk.internal.jline.internal.Nullable;

import javax.swing.*;

public interface IntegrationElementSettings {
    @Nullable
    JPanel createPanel();

    boolean isModified();

    void apply();

    void reset();

}

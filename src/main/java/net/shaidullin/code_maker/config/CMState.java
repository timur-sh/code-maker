package net.shaidullin.code_maker.config;

import net.shaidullin.code_maker.core.node.ModuleNode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CMState implements Serializable {
    private Map<String, String> configuration = new HashMap<>();
    private List<ModuleNode> moduleNodes = new ArrayList<>();

    /**
     * No-args constructor for deserialization.
     */
    public CMState() {
        super();
    }

    public void setConfiguration(final Map<String, String> deserialisedConfiguration) {
        configuration = deserialisedConfiguration;
    }

    @NotNull
    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public String get(String key) {
        return configuration.get(key);
    }

    public void put(String key, String value) {
        configuration.put(key, value);
    }

    public List<ModuleNode> getModuleNodes() {
        return moduleNodes;
    }

    public void setModuleNodes(List<ModuleNode> moduleNodes) {
        this.moduleNodes = moduleNodes;
    }
}
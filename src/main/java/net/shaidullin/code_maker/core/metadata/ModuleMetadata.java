package net.shaidullin.code_maker.core.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.shaidullin.code_maker.core.node.ModuleNode;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains metadata of module. Example of JSON config:
 * {
 * "systemName": "News",
 * "usedModules": [
 * "Author"
 * ]
 * }
 */
public class ModuleMetadata extends AbstractMetadata {
    private List<String> usedModules = new ArrayList<>();
    private String fqnPackage;

    public ModuleMetadata() {
    }

    public List<String> getUsedModules() {
        return usedModules;
    }

    public void setUsedModules(List<String> usedModules) {
        this.usedModules = usedModules;
    }

    public String getFqnPackage() {
        return fqnPackage;
    }

    public void setFqnPackage(String fqnPackage) {
        this.fqnPackage = fqnPackage;
    }


    @JsonIgnore
    public boolean isModuleAllowed(ModuleNode other) {
        return usedModules.contains(other.getSystemName()) || other.getSystemName().equals(this.systemName);
    }
}

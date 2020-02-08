package net.shaidullin.code_maker.core.metadata;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains metadata of module. Example of JSON config:
 * {
 * "systemName": "News",
 * "fqnPackageParts": [
 * "com",
 * "news"
 * ],
 * "usedModules": [
 * "Author"
 * ]
 * }
 */
public class ModuleMetadata extends AbstractMetadata {
    private List<String> usedModules = new ArrayList<>();

    public ModuleMetadata() {
    }

    public List<String> getUsedModules() {
        return usedModules;
    }

    public void setUsedModules(List<String> usedModules) {
        this.usedModules = usedModules;
    }
}

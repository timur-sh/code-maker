package net.shaidullin.code_maker.core.metadata;

import java.util.ArrayList;
import java.util.List;


/**
 * Contains metadata of module. Example of JSON config:
 * {
 * "systemName": "News",
 * "fqnPartsPackage": [
 * "com",
 * "news"
 * ],
 * "usedModules": [
 * "Author"
 * ],
 * "defaultDtoInterface": "com.news.jpa.domain.DomainObject",
 * }
 */
public class ModuleMetadata extends AbstractMetadata {
    private String dtoInterface;
    private List<String> usedModules = new ArrayList<>();
    private List<String> fqnPackageParts = new ArrayList<>();

    public ModuleMetadata() {
    }

    public String getDtoInterface() {
        return dtoInterface;
    }

    public void setDtoInterface(String dtoInterface) {
        this.dtoInterface = dtoInterface;
    }

    public List<String> getUsedModules() {
        return usedModules;
    }

    public void setUsedModules(List<String> usedModules) {
        this.usedModules = usedModules;
    }

    @Override
    public List<String> getFqnPackageParts() {
        return this.fqnPackageParts;
    }

    @Override
    public void setFqnPackageParts(List<String> fqnPackageParts) {
        this.fqnPackageParts = fqnPackageParts;
    }
}

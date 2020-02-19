package net.shaidullin.code_maker.integration.impl.dto.generator.impl;

import com.intellij.openapi.ui.Messages;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.Type;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.generator.model.DtoJavaCacheModel;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.ui.generator.AbstractGenerator;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.PackageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DtoCacheJavaGenerator extends AbstractGenerator {
    private final static String TEMPLATE = "DtoJavaCacheTemplate.ftlh";
    private final static String CACHE_PACKAGE = "cache";

    private final PackageNode packageNode;
    private final ApplicationState state;

    // PathToPackage, Content
    private Map<String, String> generationCode = new HashMap<>();
    private final NameResolverManager resolverManager = NameResolverManager.getInstance();

    public DtoCacheJavaGenerator(PackageNode packageNode, ApplicationState state) {
        super();
        this.packageNode = packageNode;
        this.state = state;
    }

    @Override
    public DtoCacheJavaGenerator generate() {

        List<LeafNode> leafNodes = state.getLeaves().get(packageNode);
        String packagePath = PackageUtils.assembleFqnClassName(packageNode, true);
        String cachePackage = String.join(".", packagePath, CACHE_PACKAGE);

        String pathToCacheFolder = String.join(
            FileUtils.SEPARATOR,
            FileUtils.buildPathToGeneratedData(state, packageNode, true),
            CACHE_PACKAGE
        );


        if (!FileUtils.createFolder(pathToCacheFolder)) {
            Messages.showWarningDialog("Can't create a directory 'cache'", "Warning");
            return this;
        }

        for (LeafNode leafNode : leafNodes) {
            DtoNode dtoNode = (DtoNode) leafNode;
            DtoMetadata metadata = dtoNode.getMetadata();
            if (!metadata.isCachable()) {
                continue;
            }

            List<String> importedPackages = new ArrayList<>();

            Type cacheType = TypeUtils.getType(metadata.getCacheKeyTypeUID(), state);
            if (cacheType.isRequiredImport()) {
                importedPackages.add(cacheType.getFqnName());
            }

            String domainPackage = PackageUtils.assembleFqnClassName(dtoNode);
            importedPackages.add(domainPackage);

            DtoJavaCacheModel cacheModel = new DtoJavaCacheModel();
            cacheModel.setKey(resolverManager.resolveJava(cacheType, false));

            cacheModel.setValue(metadata.getSystemName());
            cacheModel.setSystemName(metadata.getSystemName() + "Cache");
            String fsCacheFileName = String.format("%s%s%s.%s", pathToCacheFolder,
                FileUtils.SEPARATOR,
                cacheModel.getSystemName(), FileUtils.JAVA_EXTENSION);

            Map<String, Object> root = new HashMap<>();
            root.put(MODEL, cacheModel);
            root.put(IMPORTED_PACKAGES, importedPackages);
            root.put(PACKAGE, cachePackage);

            String generationCode = this.renderTemplate(root, TEMPLATE);
            this.generationCode.put(fsCacheFileName, generationCode);
        }
        return this;
    }

    @Override
    public void save() {
        for (String fsCacheFileName : generationCode.keySet()) {
            File file = new File(fsCacheFileName);

            FileUtils.saveContent(file.getAbsolutePath(), generationCode.get(fsCacheFileName));
        }
    }
}

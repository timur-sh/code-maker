package net.shaidullin.code_maker.integration.impl.dto.generator.impl;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.DtoElementSettings;
import net.shaidullin.code_maker.integration.impl.dto.generator.model.DtoFieldJavaModel;
import net.shaidullin.code_maker.integration.impl.dto.generator.model.DtoJavaModel;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.impl.utils.DtoUtils;
import net.shaidullin.code_maker.ui.generator.AbstractGenerator;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.PackageUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DtoJavaGenerator extends AbstractGenerator {
    private final static String DTO_JAVA_TEMPLATE = "DtoJavaTemplate.ftlh";
    private final PackageNode packageNode;
    private final ApplicationState state;

    private Map<DtoNode, String> generationCode = new HashMap<>();
    private final NameResolverManager resolverManager = NameResolverManager.getInstance();

    public DtoJavaGenerator(PackageNode packageNode, ApplicationState state) {
        super();
        this.packageNode = packageNode;
        this.state = state;
    }

    @Override
    public DtoJavaGenerator generate() {
        String leafPackage = PackageUtils.assembleFqnClassName(packageNode);

        String domainInterface = state.getConfigurationValue(DtoElementSettings.DTO_PARENT_INTERFACE_KEY);
        String cacheInterface = state.getConfigurationValue(DtoElementSettings.DTO_CACHE_INTERFACE_KEY);

        List<LeafNode> leafNodes = state.getLeaves().get(packageNode);

        for (LeafNode leafNode : leafNodes) {
            DtoNode dtoNode = (DtoNode) leafNode;
            DtoMetadata metadata = dtoNode.getMetadata();

            List<String> importedPackages = new ArrayList<>();

            DtoJavaModel model = new DtoJavaModel();
            model.setSystemName(metadata.getSystemName());
            model.setSignatureClassName(resolverManager.resolveJava(metadata, false));

            if (metadata.getParentUID() != null) {
                LeafNode parentClassNode = state.getLeafByUID(metadata.getParentUID());
                if (!parentClassNode.equals(LeafNodeUtils.UNDEFINED_CLASS)) {
                    String parentPackageName = PackageUtils.assembleFqnClassName(parentClassNode);
                    importedPackages.add(parentPackageName);
                    model.setParent(parentClassNode.getMetadata().getSystemName());
                }

            } else if (StringUtils.isNoneEmpty(domainInterface) && !metadata.isCachable()) {
                String parentInterface = PackageUtils.getLastPart(domainInterface);
                model.getInterfaces().add(parentInterface);
                importedPackages.add(domainInterface);
            }

            if (metadata.isCachable() && StringUtils.isNotBlank(cacheInterface)) {
                String cachableInterfaceName = PackageUtils.getLastPart(cacheInterface);
                model.getInterfaces().add(cachableInterfaceName);
                importedPackages.add(cacheInterface);

            }

            importedPackages.addAll(DtoUtils.buildImportedPackages(dtoNode, state));
            importedPackages = importedPackages.stream().distinct().collect(Collectors.toList());

            model.setFields(metadata.getFields().stream()
                .map(field ->
                    new DtoFieldJavaModel(resolverManager.resolveJava(field, false), field, TypeUtils.getType(field.getTypeUID(), state))
                ).collect(Collectors.toSet())
            );

            Map<String, Object> root = new HashMap<>();
            root.put(MODEL, model);
            root.put(IMPORTED_PACKAGES, importedPackages);
            root.put(PACKAGE, leafPackage);

            String generationCode = this.renderTemplate(root, DTO_JAVA_TEMPLATE);
            this.generationCode.put(dtoNode, generationCode);

        }

        return this;
    }

    @Override
    public void save() {
        String pathToGeneratedData = FileUtils.buildPathToGeneratedData(state, packageNode);

        for (DtoNode dtoNode : generationCode.keySet()) {
            File file = new File(pathToGeneratedData, String.join(".", dtoNode.getSystemName(), FileUtils.JAVA_EXTENSION));

            FileUtils.saveContent(file.getAbsolutePath(), generationCode.get(dtoNode));
        }
    }
}

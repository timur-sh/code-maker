package net.shaidullin.code_maker.integration.impl.dto.generator.impl;

import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.impl.dto.generator.model.DtoFieldTypeScriptModel;
import net.shaidullin.code_maker.integration.impl.dto.generator.model.DtoTypeScriptModel;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.utils.DtoUtils;
import net.shaidullin.code_maker.ui.generator.AbstractGenerator;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.FileUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static net.shaidullin.code_maker.core.node.utils.LeafNodeUtils.UNDEFINED_CLASS;


public class DtoTypeScriptGenerator extends AbstractGenerator {
    private final static String DTO_TYPESCRIPT_TEMPLATE = "DtoTypeScriptTemplate.ftlh";
    public static final String TS_DOMAINS = "domains";

    private final PackageNode packageNode;
    private final ApplicationState state;

    private List<String> generationCodes = new ArrayList<>();
    private final NameResolverManager resolverManager = NameResolverManager.getInstance();

    public DtoTypeScriptGenerator(PackageNode packageNode, ApplicationState state) {
        super();
        this.packageNode = packageNode;
        this.state = state;
    }

    @Override
    public DtoTypeScriptGenerator generate() {
        List<DtoNode> leafNodes = state.getLeaves().get(packageNode).stream()
            .map(l -> (DtoNode) l)
            .collect(Collectors.toList());
        DtoUtils.sortNodesByParent(leafNodes, state);

        List<DtoTypeScriptModel> domainModels = new ArrayList<>();

        for (DtoNode dtoNode : leafNodes) {
            DtoMetadata domainMetadata = dtoNode.getMetadata();

            DtoTypeScriptModel domainModel = new DtoTypeScriptModel();
            domainModel.setSystemName(domainMetadata.getSystemName());
            domainModel.setSignatureClassName(resolverManager.resolveTypeScript(domainMetadata, false));

            if (domainMetadata.getParentUID() != null) {
                LeafNode parentClassNode = state.getLeafByUID(domainMetadata.getParentUID());
                if (!parentClassNode.equals(UNDEFINED_CLASS)) {
                    domainModel.setParent(resolverManager.resolveJava(parentClassNode.getMetadata(), false));
                }
            }

            domainModel.setFields(domainMetadata.getFields().stream()
                .map(field ->
                    new DtoFieldTypeScriptModel(resolverManager.resolveTypeScript(field, false), field, TypeUtils.getType(field.getTypeUID(), state))
                ).collect(Collectors.toSet())
            );

            domainModels.add(domainModel);
        }


        Map<String, Object> root = new HashMap<>();
        root.put(TS_DOMAINS, domainModels);

        String generationCode = this.renderTemplate(root, DTO_TYPESCRIPT_TEMPLATE);
        this.generationCodes.add(generationCode);

        return this;
    }

    @Override
    public void save() {
        String pathToGeneratedData = FileUtils.buildPathToGeneratedData(state, packageNode.getParent().getParent(), "typescript");
        FileUtils.createFolder(pathToGeneratedData);

        for (String generatedCode : generationCodes) {
            File file = new File(pathToGeneratedData, String.join(".", packageNode.getSystemName() + "-api", FileUtils.TYPE_SCRIPT_EXTENSION));

            FileUtils.saveContent(file.getAbsolutePath(), generatedCode);
        }
    }
}

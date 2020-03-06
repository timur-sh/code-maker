package net.shaidullin.code_maker.integration.impl.dto;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.JBPopupMenu;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.FieldMetadata;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.*;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.core.type.MetadataType;
import net.shaidullin.code_maker.core.type.TypeUtils;
import net.shaidullin.code_maker.integration.AbstractIntegrationElement;
import net.shaidullin.code_maker.integration.IntegrationElement;
import net.shaidullin.code_maker.integration.IntegrationElementSettings;
import net.shaidullin.code_maker.integration.impl.dto.generator.impl.DtoCacheJavaGenerator;
import net.shaidullin.code_maker.integration.impl.dto.generator.impl.DtoJavaGenerator;
import net.shaidullin.code_maker.integration.impl.dto.generator.impl.DtoTypeScriptGenerator;
import net.shaidullin.code_maker.integration.impl.dto.metadata.DtoMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoElementMetadata;
import net.shaidullin.code_maker.integration.impl.dto.node.DtoNode;
import net.shaidullin.code_maker.integration.impl.dto.ui.*;
import net.shaidullin.code_maker.integration.impl.dto.ui.resolver.JavaDtoNameResolver;
import net.shaidullin.code_maker.integration.impl.dto.ui.resolver.TypeScriptDtoNameResolver;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.ui.toolwindow.tree.NodeTreeMenu;
import net.shaidullin.code_maker.ui.toolwindow.workspace.impl.WorkspacePanelBody;
import net.shaidullin.code_maker.utils.JsonUtils;
import net.shaidullin.code_maker.utils.NodeUtils;
import net.shaidullin.code_maker.utils.PackageUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DtoIntegrationElement extends AbstractIntegrationElement<DtoNode> {
    private static final String NAME = "Dto";
    private static final String FOLDER = "domain";
    private static final String UID = "2427bcb4-3822-48ff-94a7-6d9b5615a969";

    @Override
    public String getUID() {
        return UID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getFolder() {
        return FOLDER;
    }

    @Override
    public boolean generate(PackageNode packageNode, ApplicationState state) {
        if (!super.generate(packageNode, state)) {
            return false;
        }

        new DtoJavaGenerator(packageNode, state)
            .generate()
            .save();

        new DtoTypeScriptGenerator(packageNode, state)
            .generate()
            .save();

        new DtoCacheJavaGenerator(packageNode, state)
            .generate()
            .save();

        return true;
    }

    @Override
    public void initialize(ModuleNode moduleNode, NameResolverManager resolverManager) {
        super.initialize(moduleNode, resolverManager);

        resolverManager.register(new JavaDtoNameResolver());
        resolverManager.register(new TypeScriptDtoNameResolver());
    }

    @Override
    public DtoNode buildLeaf(String systemName, FileInputStream inputStream, PackageNode packageNode) {
        DtoMetadata dtoMetadata = JsonUtils.readValue(inputStream, DtoMetadata.class);
        return LeafNodeUtils.buildDtoNode(packageNode, systemName, this, dtoMetadata);
    }

    @Override
    public DtoElementMetadata buildElementMetadata(ElementNode elementNode) {
        DtoElementMetadata metadata = new DtoElementMetadata();
        metadata.setUuid(UUID.randomUUID());
        metadata.setSystemName(elementNode.getSystemName());
        metadata.setDescription(elementNode.getSystemName());

        return metadata;
    }

    @Override
    public ElementNode assembleElementNode(ElementNode elementNode) {
        elementNode.setMetadata(NodeUtils.readMetadata(elementNode, DtoElementMetadata.class));

        return elementNode;
    }

    @Override
    public DtoNode assembleLeafTemplate(UUID uuid, PackageNode parentNode) {
        DtoMetadata metadata = new DtoMetadata();
        metadata.setUuid(uuid);

        DtoNode node = new DtoNode();
        node.setMetadata(metadata);
        node.setParent(parentNode);
        node.setIntegrationElement(this);

        return node;
    }

    @Nullable
    @Override
    public MetadataType<? extends LeafMetadata> buildFieldType(DtoNode node) {
        Objects.requireNonNull(node.getMetadata());
        String fqnName = PackageUtils.assembleFqnClassName(node);

        return TypeUtils.buildDtoMetadataFieldType(node.getMetadata().getUuid(),
            node.getMetadata().getSystemName(),
            false,
            true,
            fqnName,
            node.getMetadata());
    }

    @Override
    public List<Class<IntegrationElement>> declareDependencies() {
        return new ArrayList<>();
    }

    @Override
    public NodeTreeMenu<DtoNode> createNodeTreeMenu(ApplicationState state) {
        return new DtoNodeTreeMenuImpl(state, new JBPopupMenu());
    }

    @Override
    public WorkspacePanelBody createWorkspacePanelBody(Project project, JTree tree, ApplicationState state) {
        return new DtoWBPImpl(project, tree, state);
    }

    @Nullable
    @Override
    public WorkspacePanelBody createWorkspacePanelBodyForNestedNode(Node nestedNode, Project project, JTree tree, ApplicationState state) {
        if (nestedNode.getParent() instanceof DtoNode) {
            return new DtoFieldWBPImpl(project, tree, state);
        }

        return null;
    }

    @Override
    public void assembleTreeNodeByTreeNode(DefaultMutableTreeNode treeNode, DtoNode leafNode) {
        for (FieldMetadata field : leafNode.getMetadata().getFields()) {
            treeNode.add(new DefaultMutableTreeNode(new FieldNode<>(field, leafNode)));
        }
    }

    @Override
    public IntegrationElementSettings createSettingsPanel(ApplicationState state) {
        return new DtoElementSettings(state);
    }

}

package net.shaidullin.code_maker.core.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import net.shaidullin.code_maker.CodeMakerPlugin;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.node.utils.LeafNodeUtils;
import net.shaidullin.code_maker.core.type.TypeManager;
import net.shaidullin.code_maker.dto.DtoIntegrationObject;
import net.shaidullin.code_maker.integration.IntegrationObject;
import net.shaidullin.code_maker.integration.IntegrationObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@State(name = CodeMakerPlugin.ID_PLUGIN, storages = {@Storage("code-maker-idea.xml")})
public class ApplicationState implements PersistentStateComponent<CMState> {
    private static final String GENERATE_PATH = "generate-path";

    private ElementNode usedElement;
    private PackageNode usedPackage;
    private LeafNode usedLeaf;

    private Map<ModuleNode, List<ElementNode>> elements = new HashMap<>();
    private Map<ElementNode, List<PackageNode>> packages = new HashMap<>();
    private Map<PackageNode, List<LeafNode>> leaves = new HashMap<>();

    private CMState state = defaultApplicationSettings();
    private TypeManager typeManager;
    private final IntegrationObjectRegistry INTEGRATION_OBJECT_REGISTRY = new IntegrationObjectRegistry();


    public ApplicationState() {
        this.initialize();
    }

    private void initialize() {
        INTEGRATION_OBJECT_REGISTRY.register(new DtoIntegrationObject());

        typeManager = TypeManager.getInstance();
    }

    @NotNull
    private CMState defaultApplicationSettings() {
        return new CMState();
    }

    @Override
    public CMState getState() {
        return state;
    }

    @Override
    public void loadState(@Nullable final CMState sourceApplicationSettings) {
        state = Objects.requireNonNullElseGet(sourceApplicationSettings, this::defaultApplicationSettings);
    }

    @Nullable
    public ElementNode getUsedElement() {
        return usedElement;
    }

    public void setUsedElement(ElementNode usedElement) {
        this.usedElement = usedElement;
    }

    @Nullable
    public PackageNode getUsedPackage() {
        return usedPackage;
    }

    public void setUsedPackage(PackageNode usedPackage) {
        this.usedPackage = usedPackage;
    }

    @Nullable
    public LeafNode getUsedLeaf() {
        return usedLeaf;
    }

    public void setUsedLeaf(LeafNode usedLeaf) {
        this.usedLeaf = usedLeaf;
    }

    public TypeManager getTypeManager() {
        return typeManager;
    }

    /**
     * Refresh TypeStorage and Nodes
     *
     * @return ApplicationState
     */
    public ApplicationState refreshState() {
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        configurationManager.performConfiguration(this);

        if (typeManager.isInitialized()) {
            typeManager.reinitializeStorage(this);

        } else {
            typeManager.initialize(this);
        }

        return this;
    }

    public String getGeneratePath() {
        return state.get(GENERATE_PATH);
    }

    public void setGeneratePath(String path) {
        state.put(GENERATE_PATH, path);
    }

    public List<ModuleNode> getModules() {
        return state.getModuleNodes();
    }

    public Map<ModuleNode, List<ElementNode>> getElements() {
        return elements;
    }

    public Map<ElementNode, List<PackageNode>> getPackages() {
        return packages;
    }

    public Map<PackageNode, List<LeafNode>> getLeaves() {
        return leaves;
    }

    public Collection<IntegrationObject> getIntegrationObjects() {
        return INTEGRATION_OBJECT_REGISTRY.getAll();
    }

    public LeafNode getClassByUID(UUID uuid) {
        return leaves.values().stream()
            .flatMap(Collection::stream)
            .filter(c -> c.getMetadata().getUuid().equals(uuid)).findFirst()
            .orElse(LeafNodeUtils.UNDEFINED_CLASS);
    }

}

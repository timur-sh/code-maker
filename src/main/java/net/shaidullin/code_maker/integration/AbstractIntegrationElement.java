package net.shaidullin.code_maker.integration;

import net.shaidullin.code_maker.core.metadata.ElementMetadata;
import net.shaidullin.code_maker.core.metadata.MetadataSettings;
import net.shaidullin.code_maker.core.node.ElementNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.ui.resolver.NameResolverManager;
import net.shaidullin.code_maker.utils.FileUtils;
import net.shaidullin.code_maker.utils.NodeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public abstract class AbstractIntegrationElement<N extends LeafNode> implements IntegrationElement<N> {
    @Override
    public void initialize(ModuleNode moduleNode, NameResolverManager resolverManager) {
        // create folders of integration objects
        String path = StringUtils.join(
            Arrays.asList(FileUtils.buildPathToMetadata(moduleNode), this.getFolder()),
            FileUtils.SEPARATOR);

        File fsPath = new File(path);
        if (!fsPath.exists() && !fsPath.mkdirs()) {
            throw new RuntimeException(String.format(
                "Folder '%s' is not exists. Cannot create a new one",
                path
            ));
        }

        ElementNode elementNode = IoUtils.assembleElementNode(this, moduleNode);
        ElementMetadata metadata = new ElementMetadata();
        metadata.setUuid(UUID.randomUUID());
        metadata.setSystemName(elementNode.getSystemName());
        NodeUtils.writeMetadata(elementNode, metadata, false);
    }

    @Override
    public List<N> getLeaves(PackageNode packageNode) {
        List<N> leafNodes = new ArrayList<>();

        String pathToMetadata = FileUtils.buildPathToMetadata(packageNode);
        for (String fileName : FileUtils.getFiles(pathToMetadata)) {
            if (MetadataSettings.METADATA_FILE_NAME.equals(fileName)) {
                continue;
            }

            String systemName = FileUtils.getFileName(fileName);
            File leafFile = new File(pathToMetadata, fileName);
            try (FileInputStream inputStream = new FileInputStream(leafFile)) {
                N node = this.buildLeaf(systemName, inputStream, packageNode);
                leafNodes.add(node);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return leafNodes;
    }
}

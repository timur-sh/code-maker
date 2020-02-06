package net.shaidullin.code_maker.integration;

import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.PackageNode;
import net.shaidullin.code_maker.core.type.FieldType;
import net.shaidullin.code_maker.utils.FileHelper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractIntegrationObject<N extends PackageNode, M extends LeafMetadata, F extends FieldType<M>>
    implements IntegrationObject<N, M, F> {
    @Override
    public final void initialize(ModuleNode moduleNode) {
        // create folders of integration objects
        String path = StringUtils.join(
            List.of(FileHelper.buildPathToMetadata(moduleNode), this.getFolder()),
            FileHelper.SEPARATOR);

        File fsPath = new File(path);
        if (!fsPath.exists() && !fsPath.mkdirs()) {
            throw new RuntimeException(String.format(
                "Folder '%s' is not exists. Cannot create a new one",
                path
            ));
        }
    }

    @Override
    public List<LeafNode<N, M>> getLeaves(PackageNode packageNode) {
        List<LeafNode<N, M>> leafNodes = new ArrayList<>();

        String pathToMetadata = FileHelper.buildPathToMetadata(packageNode);
        for (String fileName : FileHelper.getFiles(pathToMetadata)) {
            // [0] - filename, [1] - extension
            String[] parts = fileName.split("\\.");

            String systemName = parts.length > 0
                ? parts[0]
                : fileName;


            File leafFile = new File(pathToMetadata, fileName);
            try (FileInputStream inputStream = new FileInputStream(leafFile)) {
                LeafNode<N, M> node = this.buildLeaf(systemName, inputStream, packageNode);
                leafNodes.add(node);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return leafNodes;
    }
}

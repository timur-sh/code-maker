package net.shaidullin.code_maker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.shaidullin.code_maker.core.metadata.Metadata;
import net.shaidullin.code_maker.core.metadata.MetadataSettings;
import net.shaidullin.code_maker.core.node.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NodeUtils {

    /**
     * Read metadata from {@link MetadataSettings#METADATA_FILE_NAME}
     * *
     *
     * @param node
     * @param metadataClass
     * @param <M>
     * @return
     */
    public static <M extends Metadata> M readMetadata(Node node, Class<M> metadataClass) {
        String pathToMetadata = FileUtils.buildPathToMetadata(node);

        File file = new File(pathToMetadata, MetadataSettings.METADATA_FILE_NAME);
        if (!file.exists()) {
            throw new RuntimeException("File not found. Name=" + pathToMetadata);
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            M metadata = JsonUtils.readValue(inputStream, metadataClass);
            metadata.getFqnPackageParts().add(node.getSystemName());

            return metadata;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write metadata of node to {@link MetadataSettings#METADATA_FILE_NAME}
     *
     * @param node
     */
    public static void writeMetadata(Node node) {
        String pathToMetadata = FileUtils.buildPathToMetadata(node);
        File file = new File(pathToMetadata, MetadataSettings.METADATA_FILE_NAME);

        try {
            String content = JsonUtils.getObjectMapper()
                .writeValueAsString(node.getMetadata());
            FileUtils.saveContent(file.getAbsolutePath(), content);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
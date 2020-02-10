package net.shaidullin.code_maker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.shaidullin.code_maker.core.metadata.LeafMetadata;
import net.shaidullin.code_maker.core.metadata.Metadata;
import net.shaidullin.code_maker.core.metadata.MetadataSettings;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.IeNode;
import net.shaidullin.code_maker.core.node.LeafNode;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.Node;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class NodeUtils {

    public static void reloadMetadata(ModuleNode node) {

        String pathToMetadata = FileUtils.buildPathToMetadata(node);
        File file = new File(pathToMetadata, String.join(".", node.getSystemName(), "json"));
        if (!file.exists()) {
            throw new RuntimeException("File not found. Name=" + file.getAbsolutePath());
        }

        try {
            ModuleMetadata metadata = JsonUtils.getObjectMapper().readValue(file, ModuleMetadata.class);
            node.setMetadata(metadata);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read metadata from {@link MetadataSettings#METADATA_FILE_NAME}
     * *
     *
     * @param node
     * @param metadataClass
     * @param <M>
     * @return
     */
    public static <M extends Metadata> M readMetadata(IeNode node, Class<M> metadataClass) {
        String pathToMetadata = FileUtils.buildPathToMetadata(node);

        File file = new File(pathToMetadata, MetadataSettings.METADATA_FILE_NAME);
        if (!file.exists()) {
            throw new RuntimeException("File not found. Name=" + file.getAbsolutePath());
        }

        try (FileInputStream inputStream = new FileInputStream(file)) {
            return JsonUtils.readValue(inputStream, metadataClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write metadata of node to {@link MetadataSettings#METADATA_FILE_NAME}
     *
     * @param node
     * @param metadata
     * @param skipIfExists
     */
    public static void writeMetadata(Node node, Metadata metadata, boolean skipIfExists) {
        String pathToMetadata = FileUtils.buildPathToMetadata(node);
        File file = new File(pathToMetadata, MetadataSettings.METADATA_FILE_NAME);
        if (skipIfExists && file.exists()) {
            return;
        }

        writeMetadata(file.getAbsolutePath(), metadata);
    }

    /**
     * Save leaf metadata to JSON file
     */
    public static void writeLeafMetadata(LeafNode node, LeafMetadata leafMetadata) {
        String pathToMetadata = FileUtils.buildPathToMetadata(node);
        String absoluteFileName = String.join(".", pathToMetadata, FileUtils.LEAF_METADATA_EXTENSION);
        writeMetadata(absoluteFileName, leafMetadata);
    }

    /**
     * Write metadata of node to {@link MetadataSettings#METADATA_FILE_NAME}
     *
     * @param pathToMetadata
     * @param metadata
     */
    public static void writeMetadata(String pathToMetadata, Metadata metadata) {
        try {
            String content = JsonUtils.getObjectMapper().writeValueAsString(metadata);
            FileUtils.saveContent(pathToMetadata, content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
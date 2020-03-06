package net.shaidullin.code_maker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.shaidullin.code_maker.core.config.ApplicationState;
import net.shaidullin.code_maker.core.metadata.ModuleMetadata;
import net.shaidullin.code_maker.core.node.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    public static final String TYPE_SCRIPT_EXTENSION = "ts";
    public static final String JAVA_EXTENSION = "java";
    public static final String LEAF_METADATA_EXTENSION = "json";
    public static final String SEPARATOR = File.separator;

    public static boolean exists(String path, String name) {
        File file = new File(path, name);
        return file.exists();
    }

    /**
     * Read a content of the path and return names of child folders
     *
     * @param path in file system
     * @return an empty list if no one found
     */
    public static List<String> getFolders(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            return new ArrayList<>();
        }

        String[] directories = directory.list((current, name) -> new File(current, name).isDirectory());

        if (directories == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(directories));
    }

    /**
     * Read a content of the path and return names of existed files
     *
     * @param path
     * @return
     */
    public static List<String> getFiles(String path) {
        File directory = new File(path);
        if (!directory.exists() || !directory.isDirectory()) {
            return new ArrayList<>();
        }

        String[] files = directory.list((current, name) -> new File(current, name).isFile());

        if (files == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(files));
    }

    /**
     * Create a folder if it was not created
     *
     * @param rootNode
     * @param directory
     * @return true if folder created or exists
     */
    public static boolean createFolder(Node rootNode, String directory) {
        String path = FileUtils.buildPathToMetadata(rootNode);
        return createFolder(path, directory);
    }

    /**
     * Create a folder if it was not created
     *
     * @param path
     * @param directory
     * @return true if folder created or exists
     */
    public static boolean createFolder(String path, String directory) {
        File newDirectory = new File(path, directory);
        return createFolder(newDirectory.getAbsolutePath());
    }

    /**
     * Create a folder if it was not created
     *
     * @param path
     * @return true if folder created or exists
     */
    public static boolean createFolder(String path) {
        File newDirectory = new File(path);
        if (newDirectory.exists()) {
            return true;
        }

        return newDirectory.mkdirs();
    }

    private static List<String> assemblePathsToNode(Node node) {
        List<String> parts = new ArrayList<>();
        if (!(node instanceof ModuleNode)) {
            parts.add(node.getSystemName());
        }

        while (node.getParent() != null) {
            node = node.getParent();
            if (!(node instanceof ModuleNode)) {
                parts.add(node.getSystemName());
            }
        }

        return parts;
    }

    public static String buildPathToMetadata(Node node) {
        List<String> parts = assemblePathsToNode(node);

        while (node != null) {
            if (node instanceof ModuleNode) {
                parts.add(((ModuleNode) node).getRootMetadataPath());
                break;
            }

            node = node.getParent();
        }

        Collections.reverse(parts);
        String path = StringUtils.join(parts, SEPARATOR);

        File file = new File(path);
        return file.getPath();
    }

    public static String buildPathToGeneratedData(ApplicationState state, ModuleNode node, String path) {
        List<String> parts = new ArrayList<>();
        parts.add(state.getGeneratePath());
        parts.add(node.getSystemName());
        parts.add(path);

        File file = new File(StringUtils.join(parts, SEPARATOR));
        return file.getPath();
    }

    public static String buildPathToGeneratedData(ApplicationState state, Node node) {
        return buildPathToGeneratedData(state, node, false);
    }

    public static String buildPathToGeneratedData(ApplicationState state, Node node, boolean withoutElement) {
        List<String> parts = new ArrayList<>();

        if (node instanceof LeafNode) {
            parts.add(node.getSystemName());
            node = node.getParent();
        }

        if (node instanceof PackageNode) {
            ElementNode elementNode = ((PackageNode) node).getParent();
            if (!withoutElement) {
                parts.add(elementNode.getSystemName());
            }

            parts.add(node.getSystemName());
            node = elementNode.getParent();
        }

        if (node instanceof ElementNode) {
            throw new UnsupportedOperationException("Use assembleFqnClassName(PackageNode) instead");
        }

        if (node instanceof ModuleNode) {
            ModuleMetadata moduleMetadata = ((ModuleMetadata) node.getMetadata());

            parts.addAll(
                Arrays.asList(moduleMetadata.getFqnPackage().split("\\."))
            );
            parts.add(node.getSystemName());
        }

        parts.add(state.getGeneratePath());

        Collections.reverse(parts);
        String path = StringUtils.join(parts, SEPARATOR);

        File file = new File(path);
        return file.getPath();
    }

    /**
     * Save generated content
     *
     * @param packageNode is used to build a path
     * @param name        is file name
     * @param content     to be stored
     */
    public static void saveGeneratedContent(ApplicationState state, PackageNode packageNode, String name, Object content) {
        String folder = buildPathToGeneratedData(state, packageNode);
        String fileName = FileUtils.buildLeafFileName(folder, name);

        try {
            saveContent(fileName, JsonUtils.getObjectMapper().writeValueAsString(content));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save {@code content} in the {@code filePath}
     *
     * @param filePath path to file in FS
     * @param content  content
     * @throws RuntimeException
     */
    public static void saveContent(String filePath, String content) {
        File file = new File(filePath);

        try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            fileWriter.write(content);
            fileWriter.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildLeafFileName(LeafNode node) {
        String pathToMetadata = buildPathToMetadata(node);
        pathToMetadata = String.join(".", pathToMetadata, LEAF_METADATA_EXTENSION);

        File newFile = new File(pathToMetadata);
        return newFile.getPath();
    }

    public static String buildLeafFileName(String directory, String fileName) {
        return buildLeafFileName(directory, fileName, LEAF_METADATA_EXTENSION);
    }

    public static String buildLeafFileName(String directory, String fileName, String extension) {
        fileName = String.join(".", fileName, extension);

        File newFile = new File(directory, fileName);
        return newFile.getPath();
    }

    /**
     * Split {@code file} by dot and return a filename without extension
     *
     * @param file
     * @return
     */
    public static String getFileName(String file) {
        String[] parts = file.split("\\.");

        return parts.length > 0
            ? parts[0]
            : file;
    }
}

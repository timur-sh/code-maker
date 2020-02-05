package net.shaidullin.code_maker.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.shaidullin.code_maker.config.ApplicationState;
import net.shaidullin.code_maker.core.node.ModuleNode;
import net.shaidullin.code_maker.core.node.Node;
import net.shaidullin.code_maker.core.node.PackageNode;
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

public class FileHelper {
    public static final String SEPARATOR = File.separator;

    /**
     * Read a content of the path and return names of child folders
     *
     * @param path in file system
     * @return an empty list if no one found
     */
    public static List<String> getFolders(String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return new ArrayList<>();

        }
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());

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

        String[] files = directory.list((current, name) -> new File(current, name).isFile());

        if (files == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(Arrays.asList(files));
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
        if (newDirectory.exists()) {
            return true;
        }

        return newDirectory.mkdir();
    }

    private static List<String> buildPathPartsOfNode(Node node) {
        List<String> parts = new ArrayList<>();
        parts.add(node.getSystemName());

        while (node.getParent() != null) {
            node = node.getParent();
            parts.add(node.getSystemName());
        }

        return parts;
    }

    public static String buildPathToMetadata(Node node) {
        List<String> parts = buildPathPartsOfNode(node);

        if (node instanceof ModuleNode) {
            parts.add(((ModuleNode) node).getPath());
        }

        Collections.reverse(parts);
        String path = StringUtils.join(parts, SEPARATOR);

        File file = new File(path);
        return file.getPath();
    }

    public static String buildPathToGeneratedData(ApplicationState state, Node node) {
        List<String> parts = buildPathPartsOfNode(node);
        if (state.getGeneratePath() != null) {
            parts.add(state.getGeneratePath());
        }

        Collections.reverse(parts);
        String path = StringUtils.join(parts, SEPARATOR);

        File file = new File(path);
        return file.getPath();
    }

    /**
     * Save content
     *
     * @param packageNode is used to build a path
     * @param name        is file name
     * @param content     to be stored
     */
    public static void saveContent(ApplicationState state, PackageNode packageNode, String name, Object content) {
        String folder = buildPathToGeneratedData(state, packageNode);
        String fileName = FileHelper.buildAbsoluteFileName(folder, name);

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

    public static String buildAbsoluteFileName(String directory, String fileName) {
        return buildAbsoluteFileName(directory, fileName, "json");
    }

    public static String buildAbsoluteFileName(String directory, String fileName, String extension) {
        fileName = fileName + "." + extension;

        File newFile = new File(directory, fileName);
        return newFile.getPath();
    }
}

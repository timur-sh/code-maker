package net.shaidullin.code_maker.ui.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Arrays;

/**
 * Custom FileChooser Descriptor that allows the specification of a file extension.
 */
public class ExtensionFileChooserDescriptor extends FileChooserDescriptor {
    private static final String JAR_EXTENSION = "jar";

    private final String[] fileExtensions;
    private final boolean allowFilesInJars;

    /**
     * Construct a file chooser descriptor for the given file extension.
     *
     * @param title            the dialog title.
     * @param description      the dialog description.
     * @param allowFilesInJars may files within JARs be selected?
     * @param fileExtensions   the file extension(s).
     */
    public ExtensionFileChooserDescriptor(String title,
                                          String description,
                                          boolean allowFilesInJars,
                                          String... fileExtensions) {
        super(true, false, containsJar(fileExtensions), containsJar(fileExtensions), allowFilesInJars, false);

        setTitle(title);
        setDescription(description);

        this.fileExtensions = sortAndMakeLowercase(fileExtensions);
        this.allowFilesInJars = allowFilesInJars;
    }

    private static boolean containsJar(String[] extensions) {
        if (extensions == null) {
            return false;
        }
        for (String extension : extensions) {
            if ("jar".equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    private String[] sortAndMakeLowercase(String[] strings) {
        Arrays.sort(strings);
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].toLowerCase();
        }
        return strings;
    }

    @Override
    public boolean isFileSelectable(VirtualFile file) {
        return fileExtensionMatches(file);
    }

    @Override
    public boolean isFileVisible(VirtualFile file, boolean showHiddenFiles) {
        return file.isDirectory()
            || fileExtensionMatches(file)
            || (isJar(file) && allowFilesInJars);
    }

    private boolean isJar(VirtualFile file) {
        String currentExtension = file.getExtension();
        return currentExtension != null && JAR_EXTENSION.equalsIgnoreCase(currentExtension);
    }

    private boolean fileExtensionMatches(VirtualFile file) {
        String currentExtension = file.getExtension();
        return currentExtension != null && Arrays.binarySearch(fileExtensions, currentExtension.toLowerCase()) >= 0;
    }
}

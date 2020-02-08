package net.shaidullin.code_maker;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;


/**
 * Main class for the CodeMakerPlugin scanning plug-in.
 */
public final class CodeMakerPlugin implements ProjectComponent {

    /**
     * The plugin ID. Caution: It must be identical to the String set in build.gradle at intellij.pluginName
     */
    public static final String ID_PLUGIN = "CodeMakerPlugin-IDEA";

    private static final Logger LOG = com.intellij.openapi.diagnostic.Logger.getInstance(CodeMakerPlugin.class);
    private final Project project;

    /**
     * Construct a plug-in instance for the given project.
     *
     * @param project the current project.
     */
    public CodeMakerPlugin(
        @NotNull final Project project
    ) {
        this.project = project;

        LOG.info("Generator Plugin loaded with project base dir: \"" + getProjectPath() + "\"");

        disableCheckStyleLogging();
    }

    private void disableCheckStyleLogging() {
        try {
            // This is a nasty hack to get around IDEA's DialogAppender sending any errors to the Event Log,
            // which would result in Generator parse errors spamming the Event Log.
            org.apache.log4j.Logger.getLogger("com.puppycrawl.tools.checkstyle.TreeWalker").setLevel(Level.OFF);
        } catch (Exception e) {
            LOG.warn("Unable to suppress logging from CheckStyle's TreeWalker", e);
        }
    }

    public Project getProject() {
        return project;
    }

    @Nullable
    private File getProjectPath() {
        final VirtualFile baseDir = project.getBaseDir();
        if (baseDir == null) {
            return null;
        }

        return new File(baseDir.getPath());
    }

    public void projectOpened() {
        LOG.debug("Project opened.");
    }

    public static String currentPluginVersion() {
        final IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId(ID_PLUGIN));
        if (plugin != null) {
            return plugin.getVersion();
        }
        return "unknown";
    }

    @NotNull
    public String getComponentName() {
        return ID_PLUGIN;
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }
}

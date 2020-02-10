package net.shaidullin.code_maker.ui.toolwindow;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.ui.content.Content;
import net.shaidullin.code_maker.core.config.ApplicationState;
import org.jetbrains.annotations.NotNull;

public class CodeMakerToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        final Content toolContent = toolWindow.getContentManager().getFactory().createContent(
            new CodeMakerToolWindowPanel(project, ServiceManager.getService(project, ApplicationState.class)),
            "Code Maker",
            false);
        toolWindow.getContentManager().addContent(toolContent);

        toolWindow.setTitle("Code Maker");
        toolWindow.setType(ToolWindowType.DOCKED, null);
    }
}

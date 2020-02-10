package net.shaidullin.code_maker.ui.toolwindow.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import net.shaidullin.code_maker.ui.toolwindow.CodeMakerToolWindowPanel;

public class ToolWindowUtils {
    public static void refresh(Project project) {
        final ToolWindow toolWindow = ToolWindowManager.getInstance(project)
            .getToolWindow(CodeMakerToolWindowPanel.TOOL_WINDOW_ID);

        final Content content = toolWindow.getContentManager().getContent(0);
        if (content != null && content.getComponent() instanceof CodeMakerToolWindowPanel) {
            ((CodeMakerToolWindowPanel) content.getComponent()).refreshAll();
        }
    }
}

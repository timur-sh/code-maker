package net.shaidullin.code_maker.ui.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import net.shaidullin.code_maker.ui.toolwindow.utils.ToolWindowUtils;


/**
 * Update application state and refresh tool window
 */
public class Update extends AnAction {

    @Override
    public void actionPerformed(final AnActionEvent event) {
        final Project project = DataKeys.PROJECT.getData(event.getDataContext());
        if (project == null) {
            return;
        }

        ToolWindowUtils.refresh(project);
    }

}

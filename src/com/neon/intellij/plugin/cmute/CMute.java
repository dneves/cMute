package com.neon.intellij.plugin.cmute;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

/**
 * User: dneves
 * Date: 9/30/11 Time: 5:13 PM
 */
public class CMute implements ToolWindowFactory
{

    private Project currentProject;

    private ToolWindow toolWindow;

    private final MainPanel view = new MainPanel();

    public CMute()
    {

    }

    @Override
    public void createToolWindowContent( Project project, ToolWindow toolWindow )
    {
        this.currentProject = project;
        this.toolWindow = toolWindow;

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent( view, "", false );
        toolWindow.getContentManager().addContent( content );
    }

}

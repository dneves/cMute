package com.neon.intellij.plugin.cmute.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.neon.intellij.plugin.cmute.BuddyBean;
import com.neon.intellij.plugin.cmute.BuddyLogic;

/**
 * User: laught
 * Date: 17-10-2011 Time: 0:00
 */
public class ShareCodeAction extends AnAction
{

    private final BuddyBean buddy;

    public ShareCodeAction( BuddyBean buddy )
    {
        super(
            buddy.getRosterEntry().getName(),
            "Share selected line with " + buddy.getRosterEntry().getName(),
            BuddyLogic.getInstance().getIconByPresence( buddy.getPresence() ) );

        this.buddy = buddy;
    }

    public void actionPerformed( AnActionEvent anActionEvent )
    {
        System.out.println( "ShareCodeAction . actionPerformed( " + anActionEvent + " ) : TODO ." );

//        Project project = anActionEvent.getData( PlatformDataKeys.PROJECT );
//        VirtualFile file = anActionEvent.getData( PlatformDataKeys.VIRTUAL_FILE );
//
//        Editor textEditor = FileEditorManager.getInstance( project ).getSelectedTextEditor();
//        CaretModel caretModel = textEditor.getCaretModel();
//
//        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
//        int logicalLine = logicalPosition.line;
//        int visualLine = logicalLine + 1;
//        int logicalColumn = logicalPosition.column;
//
//        ShareCodeCommand command = new ShareCodeCommand( buddy );
//        command.setProject(project);
//        command.setFile(file);
//        command.setLine(logicalLine);
//        command.execute();
    }

}

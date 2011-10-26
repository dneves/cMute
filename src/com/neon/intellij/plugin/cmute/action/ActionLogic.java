package com.neon.intellij.plugin.cmute.action;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.neon.intellij.plugin.cmute.BuddyBean;
import com.neon.intellij.plugin.cmute.ConnectionProvider;

import java.util.List;

/**
 * User: laught
 * Date: 03-10-2011 Time: 3:01
 */
public class ActionLogic
{

    private static ActionLogic INSTANCE = null;

    private ActionLogic()
    {

    }

    public static synchronized ActionLogic getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new ActionLogic();
        }
        return INSTANCE;
    }

    private DefaultActionGroup getDefaultActionGroup()
    {
        AnAction anAction = ActionManager.getInstance().getAction( ActionConstants.ACTION_GROUP_ID );
        if ( anAction instanceof DefaultActionGroup )
        {
            DefaultActionGroup actionGroup = ( DefaultActionGroup ) anAction;
            return actionGroup;
        }
        return null;
    }

    public void updateActions(  )
    {
        clearActions();

        boolean isAuthenticated = ConnectionProvider.getInstance().isAuthenticated();
        if ( isAuthenticated )
        {
            try
            {
                List<BuddyBean> onlineBuddies = ConnectionProvider.getInstance().getOnlineBuddies();
                addAction( onlineBuddies );
            }
            catch ( Exception e )
            {
                // TODO : handle exception
                e.printStackTrace();
            }
        }
    }

    private void addAction( List< BuddyBean > buddyList )
    {
        if ( buddyList != null )
        {
            for ( BuddyBean buddy : buddyList )
            {
                if ( buddy.isCodeSharingEnabled() )
                {
                    addShareCodeAction( buddy );
                }
            }
        }
    }
    private void addShareCodeAction( BuddyBean forBuddy )
    {
        // TODO : add keyboard shortcut to each action
        ShareCodeAction buddyAction = new ShareCodeAction( forBuddy );
        String actionID = ActionConstants.ACTION_PREFIX + forBuddy.getRosterEntry().getUser();
        ActionManager.getInstance().registerAction( actionID, buddyAction );
        addAction( buddyAction, getDefaultActionGroup() );
    }

    private void addAction( AnAction action, DefaultActionGroup toGroup )
    {
        if ( toGroup != null )
        {
            toGroup.addAction( action );
        }
    }

    private String getID( AnAction anAction )
    {
        return ActionManager.getInstance().getId( anAction );
    }

    private void unregisterAction( AnAction anAction )
    {
        ActionManager.getInstance().unregisterAction( getID( anAction ) );
    }

    private void clearActions()
    {
        DefaultActionGroup actionGroup = getDefaultActionGroup();
        if ( actionGroup != null )
        {
            for ( AnAction anAction : actionGroup.getChildActionsOrStubs() )
            {
                unregisterAction( anAction );
            }
            actionGroup.removeAll();
        }
    }
}

package com.neon.intellij.plugin.cmute;


import com.neon.intellij.plugin.cmute.images.PluginImages;
import org.jivesoftware.smack.packet.Presence;

import javax.swing.Icon;

/**
 * User: laught
 * Date: 02-10-2011 Time: 17:04
 */
public class BuddyLogic
{

    private enum STATUS_MESSAGES
    {
        FREE_TO_CHAT( "Free to chat", PluginImages.STATUS.ONLINE ),
        ON_THE_PHONE( "On the phone", PluginImages.STATUS.BUSY );

        private final String message;
        private final Icon icon;

        STATUS_MESSAGES( String message, Icon icon )
        {
            this.message = message;
            this.icon = icon;
        }

        public String getMessage()
        {
            return message;
        }

        public Icon getIcon()
        {
            return icon;
        }
    };

    private static BuddyLogic INSTANCE = null;

    private BuddyLogic()
    {

    }

    public static synchronized BuddyLogic getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new BuddyLogic();
        }
        return INSTANCE;
    }

    public Icon getIconByPresence( Presence presence )
	{
		Icon result = PluginImages.STATUS.OFFLINE;
		if ( presence != null )
		{
            String statusMessage = presence.getStatus();
            result = getIconByStatusMessage( statusMessage );
            if ( result == null )
            {
                result = getIconByMode( presence.getMode() );
            }
            if ( result == null )
            {
                result = getIconByPresenceType( presence.getType() );
            }
		}
		return result;
	}

    private Icon getIconByStatusMessage( String statusMessage )
    {
        Icon result = null;
        if ( statusMessage != null )
        {
            for ( STATUS_MESSAGES STATUS : STATUS_MESSAGES.values() )
            {
                if ( STATUS.getMessage().equalsIgnoreCase( statusMessage ) )
                {
                    result = STATUS.getIcon();
                    break;
                }
            }
        }
        return result;
    }

    private Icon getIconByPresenceType( Presence.Type type )
    {
        Icon result = PluginImages.STATUS.ONLINE;
        if ( type != null )
        {
            switch ( type )
            {
                case available :
                    result = PluginImages.STATUS.ONLINE;
                    break;
                case unavailable :
                    result = PluginImages.STATUS.OFFLINE;
                    break;
            }
        }
        return result;
    }

    private Icon getIconByMode( Presence.Mode mode )
    {
        Icon result = PluginImages.STATUS.ONLINE;
        if ( mode != null )
        {
            switch ( mode )
            {
                case available : // Available ( default )
                    result = PluginImages.STATUS.ONLINE;
                    break;
                case chat : // Free to chat
                    result = PluginImages.STATUS.ONLINE;
                    break;
                case dnd : // Do Not Disturb
                    result = PluginImages.STATUS.BUSY;
                    break;
                case away : // Away
                    result = PluginImages.STATUS.AWAY;
                    break;
                case xa: // Away for an extended period of time
                    result = PluginImages.STATUS.AWAY_EXTENDED;
                    break;
            }
        }
        return result;
    }
}

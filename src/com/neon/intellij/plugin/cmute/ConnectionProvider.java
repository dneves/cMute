package com.neon.intellij.plugin.cmute;


import com.neon.intellij.plugin.cmute.action.ActionLogic;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.*;

/**
 * User: laught
 * Date: 09-10-2011 Time: 23:35
 */
public class ConnectionProvider
{
    private static final boolean DEBUG = true;


    private static ConnectionProvider INSTANCE = null;

    public static final String SOFTWARE_VERSION = "0.0.1";

    public static final String SOFTWARE_NAME = "cMute";

    public static final String SOFTWARE_NAME_SHORT = "cMute";

    public static final String AGENT_NAME = SOFTWARE_NAME_SHORT + " " + SOFTWARE_VERSION;

    private final Map< String, BuddyBean > rosterEntryMap = new HashMap< String, BuddyBean >(  );

    private Connection connection = null;

    private final List< MyListener > LISTENERS = new LinkedList<MyListener>();


    private ConnectionProvider()
    {

    }

    public static synchronized ConnectionProvider getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new ConnectionProvider();
        }
        return INSTANCE;
    }


    public void addListener( MyListener listener )
    {
        if ( listener != null )
        {
            LISTENERS.add( listener );
        }
    }

    private void fireOnLogin()
    {
        for ( MyListener listener : LISTENERS )
        {
            listener.onLogin();
        }
    }

    private void fireOnDisconnect( final Exception e )
    {
        for ( MyListener listener : LISTENERS )
        {
            listener.onDisconnect( e );
        }
    }

    private void fireChatCreated( final Chat chat )
    {
        fireChatCreated( chat, null );
    }
    private void fireChatCreated( final Chat chat, final Message message )
    {
        for ( MyListener listener : LISTENERS )
        {
            listener.onChatCreated( chat, message );
        }
    }

    private void fireOnPresenceChanged( final Presence presence )
    {
        for ( MyListener listener : LISTENERS )
        {
            listener.onPresenceChanged( presence );
        }
    }


    public boolean isConnected()
	{
		return connection != null && connection.isConnected();
	}

	public boolean isAuthenticated()
	{
		return isConnected() && connection.isAuthenticated();
	}

    private void connect( String host, int port, String serviceName ) throws XMPPException
    {
        if ( ! isConnected() )
        {
            ConnectionConfiguration config = new ConnectionConfiguration( host, port, serviceName );
            connection = new XMPPConnection( config );
            connection.connect();

            connection.addConnectionListener( new ConnectionListener()
            {
                @Override
                public void connectionClosed()
                {
                    ActionLogic.getInstance().updateActions();
                    fireOnDisconnect( null );
                }

                @Override
                public void connectionClosedOnError(Exception e)
                {
                    fireOnDisconnect( e );
                }

                @Override
                public void reconnectingIn(int i)
                {
                    System.out.println( "reconnectingIn() " + i );
                }

                @Override
                public void reconnectionSuccessful()
                {
                    System.out.println( "reconnectionSuccessful()" );
                }

                @Override
                public void reconnectionFailed(Exception e)
                {
                    System.out.println( "reconnectionFailed() : " + e );
                }
            });
        }
    }

    public void disconnect()
    {
        if ( isConnected() )
        {
            connection.disconnect();
	        connection = null;
            rosterEntryMap.clear();
        }
    }

    private void validateSettings( SettingsBean settingsBean ) throws IllegalArgumentException
    {
        if ( settingsBean == null )
        {
            throw new IllegalArgumentException( "Invalid settings" );
        }
        // TODO : validate host, port, username and password
    }

    public void connect( SettingsBean settingsBean ) throws Exception
    {
        validateSettings( settingsBean );

        connect( settingsBean.getHost(), settingsBean.getPort(), settingsBean.getService() );
        if ( ! connection.isAuthenticated() )
        {
            connection.login( settingsBean.getUsername(), settingsBean.getPassword(), AGENT_NAME );

            connection.getRoster().addRosterListener( new RosterListener()
            {
                @Override
                public void entriesAdded(Collection<String> strings)
                {
                    // TODO : refresh buddy list
                    System.out.println( "entriesAdded() : " + strings );
                }

                @Override
                public void entriesUpdated(Collection<String> strings)
                {
                    // TODO : refresh buddy list
                    System.out.println( "entriesUpdated() : " + strings );
                }

                @Override
                public void entriesDeleted(Collection<String> strings)
                {
                    // TODO : refresh buddy list
                    System.out.println( "entriesDeleted() : " + strings );
                }

                @Override
                public void presenceChanged( Presence presence )
                {
                    BuddyBean buddyBean = getBuddyByPresence( presence );
                    if ( buddyBean == null )
                    {
                        buddyBean = getBuddyByUsername( getUsername( presence.getFrom() ) );
                    }
                    buddyBean.setCodeSharingEnabled( isCodeSharingEnable( presence ) );
                    buddyBean.setPresence( presence );

                    ActionLogic.getInstance().updateActions();

                    fireOnPresenceChanged( presence );
                }
            });

            connection.getChatManager().addChatListener( new ChatManagerListener()
            {
                @Override
                public void chatCreated( Chat chat, boolean createdLocally )
                {
                    chat.addMessageListener(new MessageListener() {
                        @Override
                        public void processMessage(Chat chat, Message message) {
                            fireChatCreated(chat, message);
                        }
                    });
                    fireChatCreated( chat );
                }
            });

            fireOnLogin();
        }
    }

    public void createChat( BuddyBean buddy )
    {
        if ( isAuthenticated() )
        {
            String userID = buddy.getRosterEntry().getUser();
            Chat chat = connection.getChatManager().getThreadChat( userID );
            if ( chat == null )
            {
                chat = connection.getChatManager().createChat( userID, userID, null );
            }
            else
            {
                fireChatCreated( chat );
            }
        }
    }


    private boolean isCodeSharingEnable( BuddyBean buddy )
    {
        return buddy == null ? false : isCodeSharingEnable( buddy.getPresence() );
    }

    private boolean isCodeSharingEnable( Presence presence )
    {
        boolean result = false;
        if ( presence != null )
        {
            String from = presence.getFrom();
            String clientName = getResourceName( from );
            result = clientName != null && clientName.startsWith( SOFTWARE_NAME_SHORT );
        }
        return result || DEBUG;
    }

    private BuddyBean createBuddyBean( RosterEntry entry )
    {
        BuddyBean result = new BuddyBean();
        result.setRosterEntry( entry );
        result.setPresence( getPresence( entry ) );
        result.setCodeSharingEnabled( isCodeSharingEnable( result ) );
        rosterEntryMap.put( entry.getUser(), result );
        return result;
    }

    public List< BuddyBean > getOnlineBuddies() throws XMPPException
    {
        List< BuddyBean > result = new LinkedList<BuddyBean>();

        for ( BuddyBean buddy : getAllBuddies() )
        {
            if ( BuddyLogic.getInstance().isOnline(buddy) )
            {
                result.add( buddy );
            }
        }
        return result;
    }

    public List< BuddyBean > getAllBuddies()
    {
        List< BuddyBean > result = new LinkedList< BuddyBean >();
        if ( isAuthenticated() )
        {
            Collection<RosterEntry> rosters = connection.getRoster().getEntries();
            for ( RosterEntry entry : rosters )
            {
                BuddyBean buddyBean = createBuddyBean( entry );
                result.add( buddyBean );
            }
            Collections.sort( result, new BuddyComparator() );
        }
        return result;
    }

    private String getUsername( String participant )
    {
        String result = participant;
        if ( result != null )
        {
            String[] split = result.split( "/" );
            result = split[ 0 ];
        }
        return result;
    }

    private String getResourceName( String from )
    {
        String result = from;
        if ( result != null )
        {
            String[] split = result.split( "/" );
            result = split[ ( ( split.length > 1 ) ? 1 : 0 ) ];
        }
        return result;
    }

    public BuddyBean getBuddyByUsername( String username )
    {
        BuddyBean buddyBean = null;
        if ( isAuthenticated() )
        {
            RosterEntry rosterEntry = connection.getRoster().getEntry( getUsername( username ) );
            buddyBean = createBuddyBean( rosterEntry );
        }
        return buddyBean;
    }

    public BuddyBean getBuddyByPresence( Presence presence )
    {
        BuddyBean result = null;
        if ( presence != null )
        {
            result = rosterEntryMap.get( getUsername( presence.getFrom() ) );
        }
        return result;
    }

    private Presence getPresence( RosterEntry entry )
	{
		if ( isAuthenticated() )
		{
			Roster roster = connection.getRoster();
			return entry == null ? null : roster.getPresence( entry.getUser() );
		}
		return null;
	}

    public void sendMessage( Chat chat, String message ) throws Exception
    {
        if ( chat != null && message != null )
        {
            chat.sendMessage( message );
        }
    }
}

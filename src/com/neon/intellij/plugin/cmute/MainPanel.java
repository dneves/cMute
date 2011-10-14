package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * User: laught
 * Date: 09-10-2011 Time: 23:18
 */
public class MainPanel extends JPanel
{

    private final SettingsPanel panelSettings = new SettingsPanel();

    private final JButton buttonConnect = new JButton( "Connect" );


    private int currentSelectedIndex = 0;

    private final JTabbedPane tabsHolder = new JTabbedPane();

    private final BuddyListPanel panelBuddy = new BuddyListPanel();


    public MainPanel()
    {
        setupComponents();
        setupListeners();
        setupLayout();
    }

    private void setupComponents()
    {
        tabsHolder.addTab( "Buddies", panelBuddy );
        currentSelectedIndex = tabsHolder.getSelectedIndex();
    }

    private void setupListeners()
    {
        buttonConnect.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                doLogin();
            }
        });

        ConnectionProvider.getInstance().addListener( new MyListenerAdapter()
        {
            @Override
            public void onLogin()
            {
                buttonConnect.setText( "Disconnect" );
            }

            @Override
            public void onDisconnect( Exception e )
            {
                buttonConnect.setText( "Connect" );
                removeAllTabs();
            }

            @Override
            public void onChatCreated( Chat chat, Message message )
            {
                openChat( chat, message );
            }
        });

        tabsHolder.addChangeListener( new ChangeListener()
        {
            @Override
            public void stateChanged( ChangeEvent e )
            {
                int selectedIndex = tabsHolder.getSelectedIndex();
                if ( selectedIndex > 0 && selectedIndex != currentSelectedIndex )
                {
                    stopBlink( selectedIndex );
                }
                currentSelectedIndex = selectedIndex;
            }
        });
    }

    private void setupLayout()
    {
        JPanel panelNorth = new JPanel( new BorderLayout( 5, 5 ) );
        panelNorth.add( panelSettings, BorderLayout.NORTH );
        panelNorth.add( buttonConnect, BorderLayout.SOUTH );

        setLayout(new BorderLayout(5, 5));
        add( panelNorth, BorderLayout.NORTH );
        add( tabsHolder, BorderLayout.CENTER );
    }


    private void doLogin()
    {
        if ( ConnectionProvider.getInstance().isConnected() )
        {
            ConnectionProvider.getInstance().disconnect();
        }
        else
        {
            SettingsBean settings = panelSettings.save();

            try
            {
                ConnectionProvider.getInstance().connect( settings );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    private void removeAllTabs()
    {
        for ( int i = 1; i < tabsHolder.getTabCount(); i++ )
        {
            tabsHolder.removeTabAt( i );
        }
        CHATS.clear();
    }


    private final Map<Chat, ChatPanel > CHATS = new HashMap<Chat, ChatPanel>();

    private void openChat( Chat chat, Message message )
    {
        String username = chat.getParticipant();

        ChatPanel panel = CHATS.get( chat );
        if ( panel == null )
        {
            BuddyBean buddy = ConnectionProvider.getInstance().getBuddyByUsername( username );
            panel = new ChatPanel( chat, buddy );
            panel.processReceivedMessage( message );

            ChatTabComponent tabComponent = new ChatTabComponent( this, chat, buddy );

            CHATS.put(chat, panel);

            addTab( username, tabComponent, panel );
        }

//        int tabIndex = tabsHolder.indexOfTab( username );
//        selectIndex( tabIndex );
    }

    private void addTab( final String tabTitle, final Component tabComponent, final Container tabContainer )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                tabsHolder.addTab( tabTitle, tabContainer );
                if ( tabComponent != null )
                {
                    int idx = tabsHolder.getTabCount() - 1;
                    tabsHolder.setTabComponentAt( idx, tabComponent );
                }
            }
        });
    }

    private void selectIndex( final int index )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                tabsHolder.setSelectedIndex( index );
            }
        });
    }

    public void closeChat( ChatTabComponent tabComponent )
    {
        CHATS.remove( tabComponent.getChat() );
        tabsHolder.remove( tabsHolder.indexOfTabComponent( tabComponent ) );
    }

    private void stopBlink( int atIndex )
    {
        // TODO : tab changed to a chat one. stop chat tab component atIndex from blinking.
        System.out.println( "TODO : stop tab blinking" );
    }

    public JTabbedPane getTabsHolder()
    {
        return tabsHolder;
    }




    public static void main( String ... args )
    {
        MainPanel view = new MainPanel();

        final JFrame frame = new JFrame( ConnectionProvider.SOFTWARE_NAME + " - v" + ConnectionProvider.SOFTWARE_VERSION );
        frame.setLayout( new BorderLayout( 5, 5 ) );
        frame.add(view, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo( null );
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                frame.setVisible( true );
            }
        });
    }

}
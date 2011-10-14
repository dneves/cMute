package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.packet.Presence;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


/**
 * User: laught
 * Date: 09-10-2011 Time: 23:19
 */
public class BuddyListPanel extends JPanel
{

    private final DefaultListModel listModel = new DefaultListModel();

    private final JList list = new JList( listModel );

    private final JScrollPane scrollPane = new JScrollPane( list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );


    public BuddyListPanel()
    {
        setupComponents();
        setupListeners();
        setupLayout();
    }

    private void setupComponents()
    {
        list.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		list.setLayoutOrientation( JList.VERTICAL );
//		list.setVisibleRowCount( -1 );
		list.setCellRenderer( new BuddyListRenderer() );
    }

    private void setupListeners()
    {
        list.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if ( e.getClickCount() == 2 )
                {
                    ConnectionProvider.getInstance().createChat( getSelectedBuddy() );
                }
            }
        });

        ConnectionProvider.getInstance().addListener( new MyListenerAdapter()
        {
            @Override
            public void onLogin()
            {
                reload();
            }

            @Override
            public void onDisconnect( Exception e )
            {
                clear();
            }

            @Override
            public void onPresenceChanged( Presence presence )
            {
                reload();
            }
        });
    }

    private void setupLayout()
    {
        setLayout( new BorderLayout( 5, 5 ) );
        add( scrollPane, BorderLayout.CENTER );
    }

    private BuddyBean getSelectedBuddy()
    {
        BuddyBean result = null;

        Object selectedObject = list.getSelectedValue();
        if ( selectedObject instanceof BuddyBean )
        {
            result = ( BuddyBean ) selectedObject;
        }
        return result;
    }

    private void clear()
    {
        listModel.clear();
    }

    private void reload()
    {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run()
            {
                clear();
                List< BuddyBean > list = ConnectionProvider.getInstance().getAllBuddies();
                for ( BuddyBean buddy : list )
                {
                    listModel.addElement( buddy );
                }
            }
        });
    }
}
package com.neon.intellij.plugin.cmute;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: laught
 * Date: 30-09-2011 Time: 2:36
 */
public class ChatTabComponent extends JPanel
{

	private final JLabel labelIcon = new JLabel( );

	private final JLabel labelTitle = new JLabel(  );

	private final JButton buttonClose = new JButton( "X" );

	private final BuddyBean buddy;

    private final MainPanel mainPanel;

    private final Chat chat;

	public ChatTabComponent( MainPanel mainPanel, Chat chat, BuddyBean withBuddy )
	{
        this.mainPanel = mainPanel;
        this.chat = chat;
		this.buddy = withBuddy;

		setupComponents();
		setupListeners();
		setupLayout();
	}

	private void setupComponents()
	{
		setOpaque( false );
		labelIcon.setText( "" );

        String name = buddy.getRosterEntry().getName();
		labelTitle.setText( name );

		buttonClose.setMargin( new Insets( 0, 0, 0, 0 ) );
	}

	private void setupListeners()
	{
		buttonClose.addActionListener( new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
                mainPanel.closeChat( ChatTabComponent.this );
			}
		} );

        chat.addMessageListener( new MessageListener()
        {
            @Override
            public void processMessage( Chat chat, Message message )
            {
                JTabbedPane tabsHolder = mainPanel.getTabsHolder();

                int indexOfThis = tabsHolder.indexOfTabComponent( ChatTabComponent.this );
                int selectedIndex = tabsHolder.getSelectedIndex();

                if ( selectedIndex != indexOfThis )
                {
                    // TODO : blink this tab component
                    System.out.println( "ChatTabComponent . messageListener() : TODO - make this tab component blink !" );
                }
            }
        });
	}

	private void setupLayout()
	{
		TableLayout thisLayout = new TableLayout(
			new double[] { TableLayout.MINIMUM, TableLayout.FILL, TableLayout.MINIMUM },
			new double[] { TableLayout.MINIMUM }
		);
		thisLayout.setVGap( 5 );
		thisLayout.setHGap( 5 );
		setLayout( thisLayout );
		add( labelIcon, new TableLayoutConstraints( 0, 0, 0, 0 ) );
		add( labelTitle, new TableLayoutConstraints( 1, 0, 1, 0 ) );
		add( buttonClose, new TableLayoutConstraints( 2, 0, 2, 0 ) );
	}

    public BuddyBean getBuddy()
    {
        return buddy;
    }

    public Chat getChat()
    {
        return chat;
    }
}

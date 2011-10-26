package com.neon.intellij.plugin.cmute;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: laught
 * Date: 30-09-2011 Time: 2:15
 */
public class ChatPanel extends JPanel
{

    private static final SimpleDateFormat DF = new SimpleDateFormat( "[ HH:mm:ss ]" );

	private final JLabel labelUser = new JLabel(  );

	private final JTextArea textArea = new JTextArea(  );

	private final JScrollPane scrollPane = new JScrollPane( textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

	private final JTextField textField = new JTextField(  );


    private final Chat chat;
    private final BuddyBean buddyBean;

	public ChatPanel( final Chat chat, final BuddyBean buddy )
	{
        this.chat = chat;
        this.buddyBean = buddy;

		setupComponents();
		setupListeners();
		setupLayout();

        // TODO : cursor should always be inside textbox.
	}

	private void setupComponents()
	{
		textArea.setWrapStyleWord( true );
		textArea.setLineWrap( true );
		textArea.setEditable( false );
	}

	private void setupListeners()
	{
		textField.addKeyListener( new KeyAdapter()
		{
            @Override
            public void keyReleased( KeyEvent e )
            {
                if ( e.getKeyCode() == KeyEvent.VK_ENTER )
                {
                    sendMessage();
                }
            }
        } );

        chat.addMessageListener( new MessageListener()
        {
            @Override
            public void processMessage( Chat chat, Message message )
            {
                processReceivedMessage( message );
            }
        });
	}

    public void processReceivedMessage( Message message )
    {
        if ( message != null && message.getBody() != null )
        {
            String name = buddyBean.getRosterEntry().getName();
            append( DF.format( new Date() ) + " " + name + " : " + message.getBody() );
        }
    }

	private void setupLayout()
	{
		TableLayout thisLayout = new TableLayout(
			new double[] { TableLayout.FILL },
			new double[] { TableLayout.MINIMUM, TableLayout.FILL, TableLayout.MINIMUM }
		);
		setLayout( thisLayout );
		add( labelUser, new TableLayoutConstraints( 0, 0, 0, 0 ) );
		add( scrollPane, new TableLayoutConstraints( 0, 1, 0, 1 ) );
		add( textField, new TableLayoutConstraints( 0, 2, 0, 2 ) );
	}

    private void clear()
    {
        labelUser.setText( "" );
        textArea.setText( "" );
    }

    private void sendMessage()
    {
        String msg = textField.getText();
        if ( ! msg.trim().isEmpty() )
        {
            try
            {
                ConnectionProvider.getInstance().sendMessage( chat, msg );
                append( DF.format( new Date() ) + " Me : " + msg );
                textField.setText( "" );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
    }

    public void append( final String text )
    {
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                textArea.append( text + "\n" );
            }
        } );
    }
}

package com.neon.intellij.plugin.cmute;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * User: laught
 * Date: 09-10-2011 Time: 23:11
 */
public class SettingsPanel extends JPanel
{

    private final JLabel labelHost = new JLabel( "Host" );
    private final JTextField textHost = new JTextField();

    private final JLabel labelPort = new JLabel( "Port" );
    private final JTextField textPort = new JTextField( 4 );

    private final JLabel labelService = new JLabel( "Service Name" );
    private final JTextField textService = new JTextField();

    private final JLabel labelUsername = new JLabel( "Username" );
    private final JTextField textUsername = new JTextField();

    private final JLabel labelPassword = new JLabel( "Password" );
    private final JPasswordField textPassword = new JPasswordField();


    public SettingsPanel()
    {
        setupComponents();
        setupListeners();
        setupLayout();
    }

    private void setupComponents()
    {

    }

    private void setupListeners()
    {

    }

    private void setupLayout()
    {
        TableLayout thisLayout = new TableLayout(
            new double[] { TableLayout.MINIMUM, TableLayout.FILL, TableLayout.MINIMUM, TableLayout.PREFERRED },
            new double[] { TableLayout.MINIMUM, TableLayout.MINIMUM, TableLayout.MINIMUM, TableLayout.MINIMUM }
        );
        thisLayout.setHGap( 5 );
        thisLayout.setVGap( 5 );
        this.setLayout(thisLayout);
        this.add( labelHost, new TableLayoutConstraints( 0, 0, 0, 0 ) );
        this.add( textHost, new TableLayoutConstraints( 1, 0, 1, 0 ) );
        this.add( labelPort, new TableLayoutConstraints( 2, 0, 2, 0 ) );
        this.add( textPort, new TableLayoutConstraints( 3, 0, 3, 0 ) );
        this.add( labelService, new TableLayoutConstraints( 0, 1, 0, 1 ) );
        this.add( textService, new TableLayoutConstraints( 1, 1, 3, 1 ) );
        this.add( labelUsername, new TableLayoutConstraints( 0, 2, 0, 2 ) );
        this.add( textUsername, new TableLayoutConstraints( 1, 2, 3, 2 ) );
        this.add( labelPassword, new TableLayoutConstraints( 0, 3, 0, 3 ) );
        this.add( textPassword, new TableLayoutConstraints( 1, 3, 3, 3 ) );
    }


    public SettingsBean save()
    {
        SettingsBean result = new SettingsBean();

        int port = 5222;
        try
        {
            port = Integer.parseInt( textPort.getText() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        result.setHost( textHost.getText() );
        result.setPort( port );
        result.setService( textService.getText() );
        result.setUsername( textUsername.getText() );
        result.setPassword( new String( textPassword.getPassword() ) );

        return result;
    }

}
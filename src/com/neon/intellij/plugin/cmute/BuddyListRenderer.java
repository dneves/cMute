package com.neon.intellij.plugin.cmute;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.Component;

/**
 * User: laught
 * Date: 30-09-2011 Time: 1:38
 */
public class BuddyListRenderer implements ListCellRenderer
{

	private final JPanel comp = new JPanel(  );

	private final JLabel labelPresence = new JLabel();

	private final JLabel labelName = new JLabel();


	public BuddyListRenderer()
	{
		setupComponents();
		setupLayout();
	}

	private void setupComponents()
	{
		comp.setOpaque( true );
	}

	private void setupLayout()
	{
		TableLayout thisLayout = new TableLayout(
			new double[] { TableLayout.MINIMUM, TableLayout.MINIMUM, TableLayout.FILL },
			new double[] { TableLayout.MINIMUM }
		);
		thisLayout.setHGap( 5 );
		thisLayout.setVGap( 5 );
		comp.setLayout( thisLayout );
		comp.add( labelPresence, new TableLayoutConstraints( 0, 0, 0, 0 ) );
//		comp.add( new JSeparator( JSeparator.VERTICAL ), new TableLayoutConstraints( 1, 0, 1, 0 ) );
		comp.add( labelName, new TableLayoutConstraints( 2, 0, 2, 0 ) );
	}

	@Override
	public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus )
	{
		if ( value instanceof BuddyBean )
		{
			BuddyBean buddy = ( BuddyBean ) value;

			String name = buddy.getRosterEntry().getName();

			labelPresence.setText( "" );
			labelPresence.setIcon( BuddyLogic.getInstance().getIconByPresence( buddy.getPresence() ) );
			labelName.setText( name );
		}
		else
		{
			labelPresence.setVisible( false );
			labelName.setText( value.toString() );
		}

		comp.setBackground( isSelected ? list.getSelectionBackground() : list.getBackground() );
		comp.setForeground( isSelected ? list.getSelectionForeground() : list.getForeground() );
		return comp;
	}

}

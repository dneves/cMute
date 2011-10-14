package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

import java.util.Comparator;

/**
 * User: laught
 * Date: 30-09-2011 Time: 3:07
 */
public class BuddyComparator implements Comparator<BuddyBean>
{

	@Override
	public int compare( BuddyBean o1, BuddyBean o2 )
	{
		int result = comparePresence( o1.getPresence(), o2.getPresence() );
		if ( result == 0 )
		{
			result = compareName( o1.getRosterEntry(), o2.getRosterEntry() );
		}
		return result;
	}

	private int comparePresence( Presence p1, Presence p2 )
	{
        int result = 0;

        Presence.Mode m1 = p1 == null ? null : p1.getMode();
        Presence.Mode m2 = p2 == null ? null : p2.getMode();

        Integer v1 = getModeValue( m1 );
        Integer v2 = getModeValue( m2 );

        result = v1.compareTo( v2 );

		return result;
	}

	private int compareName( RosterEntry entry1, RosterEntry entry2 )
	{
		int result = 0;
		String name1 = entry1 == null ? null : entry1.getName();
		String name2 = entry2 == null ? null : entry2.getName();
		result = name1 != null && name2 != null ? name1.compareTo( name2 ) :
			name1 == null ? 1 : -1;
		return result;
	}

    private Integer getModeValue( Presence.Mode mode )
    {
        Integer result = new Integer( 5 );
        if ( mode != null )
        {
            switch ( mode )
            {
                case available :
                    result = new Integer( 0 );
                    break;
                case chat :
                    result = new Integer( 1 );
                    break;
                case dnd :
                    result = new Integer( 2 );
                    break;
                case away :
                    result = new Integer( 3 );
                    break;
                case xa :
                    result = new Integer( 4 );
                    break;
            }
        }
        return result;
    }
}

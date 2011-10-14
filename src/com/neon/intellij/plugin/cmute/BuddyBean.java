package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

/**
 * User: laught
 * Date: 29-09-2011 Time: 20:17
 */
public class BuddyBean
{

	private RosterEntry rosterEntry;

	private Presence presence;

    private boolean codeSharingEnabled = false;


	public BuddyBean()
	{

	}

	public RosterEntry getRosterEntry()
	{
		return rosterEntry;
	}

	public void setRosterEntry( RosterEntry rosterEntry )
	{
		this.rosterEntry = rosterEntry;
	}

	public Presence getPresence()
	{
		return presence;
	}

	public void setPresence( Presence presence )
	{
		this.presence = presence;
	}

    public boolean isCodeSharingEnabled() {
        return codeSharingEnabled;
    }

    public void setCodeSharingEnabled(boolean codeSharingEnabled) {
        this.codeSharingEnabled = codeSharingEnabled;
    }

    @Override
    public String toString() {
        return "BuddyBean{" +
                "rosterEntry=" + rosterEntry +
                ", presence=" + presence +
                ", codeSharingEnabled=" + codeSharingEnabled +
                '}';
    }
}

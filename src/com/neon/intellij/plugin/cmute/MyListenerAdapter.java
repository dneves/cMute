package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

/**
 * User: laught
 * Date: 10-10-2011 Time: 1:08
 */
public class MyListenerAdapter implements MyListener
{

    @Override
    public void onLogin()
    {

    }

    @Override
    public void onDisconnect(Exception e)
    {

    }

    @Override
    public void onChatCreated( Chat chat, Message message )
    {

    }

    @Override
    public void onPresenceChanged( Presence presence )
    {

    }
}

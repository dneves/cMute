package com.neon.intellij.plugin.cmute;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

/**
 * User: laught
 * Date: 10-10-2011 Time: 0:26
 */
public interface MyListener
{

    public void onLogin();

    public void onDisconnect(Exception e);

    public void onChatCreated(Chat chat, Message message);

    public void onPresenceChanged(Presence presence);

}

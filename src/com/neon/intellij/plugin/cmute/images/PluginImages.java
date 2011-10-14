package com.neon.intellij.plugin.cmute.images;

import javax.swing.ImageIcon;
import java.awt.Dimension;

/**
 * User: laught
 * Date: 29-09-2011 Time: 23:32
 */
public class PluginImages
{
	public static final Dimension DEFAULT_SIZE = new Dimension( 22, 22 );


	private static final String PATH = "com/neon/intellij/plugin/cmute/images/";

	public static final ImageIcon APP = ImageLoader.getImageIconSilent( PATH + "gtalk-widget.png" );

	public static class TOOLBAR
	{
		public static final ImageIcon CONNECT = ImageLoader.getImageIconSilent( PATH + "socket.png" );

		public static final ImageIcon DISCONNECT = ImageLoader.getImageIconSilent( PATH + "dialog-cancel-5.png" );

        public static final ImageIcon CLOSE = ImageLoader.getImageIconSilent( PATH + "Close-2-icon-16.png" );
	}

	public static class STATUS
	{
		private static final String STATUS_PATH = PATH + "status/";

		public static final ImageIcon AWAY = ImageLoader.getImageIconSilent( STATUS_PATH + "user-away-2.png" );

		public static final ImageIcon AWAY_EXTENDED = ImageLoader.getImageIconSilent( STATUS_PATH + "user-away-extended.png" );

		public static final ImageIcon BUSY = ImageLoader.getImageIconSilent( STATUS_PATH + "user-busy-2.png" );

		public static final ImageIcon INVISIBLE = ImageLoader.getImageIconSilent( STATUS_PATH + "user-invisible-2.png" );

		public static final ImageIcon OFFLINE = ImageLoader.getImageIconSilent( STATUS_PATH + "user-offline-2.png" );

		public static final ImageIcon ONLINE = ImageLoader.getImageIconSilent( STATUS_PATH + "user-online-2.png" );
	}
}

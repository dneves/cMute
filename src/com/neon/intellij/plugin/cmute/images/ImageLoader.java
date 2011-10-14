package com.neon.intellij.plugin.cmute.images;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

/**
 * User: laught
 * Date: 29-09-2011 Time: 23:50
 */
public class ImageLoader
{

	private ImageLoader()
	{
	}

	public static Image readImage( URL url )
		throws Exception
	{
		return ImageIO.read( url );
	}

	public static URL getURL( String imagePath )
	{
		return new ImageLoader().getClass().getClassLoader().getResource( imagePath );
	}

	public static ImageIcon getImageIcon( String imagePath )
		throws Exception
	{
		return new ImageIcon( readImage( getURL( imagePath ) ) );
	}
	public static ImageIcon getImageIconSilent( String imagePath )
	{
		ImageIcon imageIcon = null;
		try
		{
			imageIcon = getImageIcon( imagePath );
		}
		catch ( Exception e )
		{
//			e.printStackTrace();
		}
		return imageIcon;
	}

	public static Image getIcon( String imagePath )
		throws Exception
	{
		return readImage( getURL( imagePath ) );
	}

	public static Image getIconSilent( String imagePath )
	{
		Image image = null;
		try
		{
			image = getIcon( imagePath );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		return image;
	}
}

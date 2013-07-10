package ge.utils.spring.support;

import ge.utils.ico.Ico;
import org.apache.log4j.Logger;

import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyEditorSupport;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageResourcesEditorSupport extends PropertyEditorSupport
{
    private static Logger logger = Logger.getLogger( ImageResourcesEditorSupport.class );

    private static Map<String, Image> cache = new HashMap<String, Image>();

    public void setAsText( String text ) throws
                                         IllegalArgumentException
    {
        if ( cache.containsKey( text ) == false )
        {
            Image image = null;

            if ( ( text != null ) && ( text.length() != 0 ) )
            {
                if ( text.contains( ":" ) == true )
                {
                    String iconName = text.substring( 0, text.indexOf( ":" ) );
                    int index = Integer.parseInt( text.substring( text.indexOf( ":" ) + 1 ) );

                    byte[] bytes = loadResourceData( iconName );

                    image = Ico.getImage( bytes, index );
                }
                else
                {
                    byte[] bytes = loadResourceData( text );

                    if ( ( bytes != null ) && ( bytes.length != 0 ) )
                    {
                        image = Toolkit.getDefaultToolkit().createImage( bytes );
                    }
                }
            }

            if ( image != null )
            {
                cache.put( text, image );
            }
        }

        setValue( cache.get( text ) );
    }

    private byte[] loadResourceData( String fileName )
    {
        InputStream inputStream = null;
        try
        {
            if ( ( fileName != null ) && ( fileName.length() != 0 ) )
            {
                URL url = getClass().getResource( fileName );

                if ( url == null )
                {
                    url = ClassLoader.getSystemClassLoader().getResource( fileName );

                    if ( url == null )
                    {
                        logger.trace( "Failed to load resource file: " + fileName );
                        return null;
                    }
                }

                inputStream = url.openStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] b = new byte[ 1000 ];

                while ( true )
                {
                    int read = inputStream.read( b );

                    if ( read == -1 )
                    {
                        break;
                    }
                    else
                    {
                        byteArrayOutputStream.write( b, 0, read );
                    }
                }

                return byteArrayOutputStream.toByteArray();
            }
        }
        catch ( IOException e )
        {
            logger.trace( "Failed to load resource file: " + fileName );
        }
        finally
        {
            try
            {
                if ( inputStream != null )
                {
                    inputStream.close();
                }
            }
            catch ( IOException e )
            {

            }
        }

        return null;
    }
}

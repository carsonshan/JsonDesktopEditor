package ge.utils.bundle;

import ge.utils.ico.Ico;
import org.apache.log4j.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Resources
{
    private static Logger logger = Logger.getLogger( Resources.class );

    private String resourceName;

    private ResourceBundle resourceBundle;

    private static Map<String, Resources> bundles =
            new HashMap<String, Resources>();

    private Resources( String resourceName )
    {
        if ( resourceName == null )
        {
            logger.error( "resourceName cannot be null." );
            throw new IllegalArgumentException( "resourceName cannot be null." );
        }
        else
        {
            this.resourceName = resourceName;
            initialise();
        }
    }

    private void initialise()
    {
        try
        {
            String bundleName = resourceName + ".resources";
            resourceBundle =
                    ResourceBundle.getBundle( bundleName, Locale.getDefault(), ClassLoader.getSystemClassLoader() );
        }
        catch ( Exception e )
        {
            logger.error( "Failed to find resource bundle: " + resourceName + ".resources" );
        }

        if ( resourceBundle == null )
        {
            logger.error( "Failed to find resource bundle: " + resourceName + ".resources" );
        }
    }

    public static Resources getInstance( String resourceBundleName )
    {
        Resources definition = null;

        if ( bundles.containsKey( resourceBundleName ) == false )
        {
            definition = new Resources( resourceBundleName );

            bundles.put( resourceBundleName, definition );
        }
        else
        {
            definition = bundles.get( resourceBundleName );
        }

        return definition;
    }

    public String getResourceString( String... resourceName )
    {
        if ( ( resourceName == null ) || ( resourceName.length == 0 ) )
        {
            logger.error( "resourceName cannot be null or empty." );
            throw new IllegalArgumentException( "resourceName cannot be null or empty." );
        }

        String retVal = null;

        if ( resourceBundle != null )
        {
            String resource = "";

            for ( String part : resourceName )
            {
                if ( resource.length() != 0 )
                {
                    resource += ".";
                }

                resource += part;
            }

            try
            {
                retVal = resourceBundle.getString( resource );
            }
            catch ( Exception ignored )
            {
                logger.trace( "Failed to find resource: " + resource );
            }
        }

        return retVal;
    }

    public String getResourceString( Class<?> clazz, String... resourceSuffix )
    {
        return getResourceString( convertResourceName( clazz, resourceSuffix ) );
    }

    public Character getResourceCharacter( String... resourceName )
    {
        String value = getResourceString( resourceName );

        Character retVal;

        if ( ( value != null ) && ( value.length() == 1 ) )
        {
            retVal = value.charAt( 0 );
        }
        else
        {
            retVal = null;
        }

        return retVal;
    }

    public Character getResourceCharacter( Class<?> clazz, String... resourceSuffix )
    {
        return getResourceCharacter( convertResourceName( clazz, resourceSuffix ) );
    }

    public Image getResourceImage( String... resourceName )
    {
        String imageName = getResourceString( resourceName );

        if ( ( imageName != null ) && ( imageName.length() != 0 ) )
        {
            if ( imageName.contains( ":" ) == true )
            {
                String iconName = imageName.substring( 0, imageName.indexOf( ":" ) );
                int index = Integer.parseInt( imageName.substring( imageName.indexOf( ":" ) + 1 ) );

                byte[] bytes = loadResourceData( iconName );

                return Ico.getImage( bytes, index );
            }
            else
            {
                byte[] bytes = loadResourceData( imageName );

                if ( ( bytes != null ) && ( bytes.length != 0 ) )
                {
                    return Toolkit.getDefaultToolkit().createImage( bytes );
                }
            }
        }

        return null;
    }

    public Image getResourceImage( Class<?> clazz, String... resourceSuffix )
    {
        return getResourceImage( convertResourceName( clazz, resourceSuffix ) );
    }

    public Icon getResourceIcon( Class<?> clazz, String... resourceSuffix )
    {
        return getResourceIcon( convertResourceName( clazz, resourceSuffix ) );
    }

    public Icon getResourceIcon( String... resourceName )
    {
        Image image = getResourceImage( resourceName );

        if ( image != null )
        {
            return new ImageIcon( image );
        }

        return null;
    }

    public byte[] getResourceData( Class<?> clazz, String... resourceSuffix )
    {
        return getResourceData( convertResourceName( clazz, resourceSuffix ) );
    }

    public byte[] getResourceData( String... resourceName )
    {
        String fileName = getResourceString( resourceName );

        return loadResourceData( fileName );
    }

    private byte[] loadResourceData( String fileName )
    {
        InputStream inputStream = null;
        try
        {
            if ( ( fileName != null ) && ( fileName.length() != 0 ) )
            {
                if ( fileName.charAt( 0 ) != '/' )
                {
                    String packagePath = resourceName.replace( '.', '/' );

                    fileName = packagePath + '/' + fileName;
                }

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

    private String[] convertResourceName( Class<?> clazz, String[] resourceSuffix )
    {
        List<String> retVal = new ArrayList<String>();

        retVal.add( clazz.getName() );

        if ( ( resourceSuffix != null ) && ( resourceSuffix.length != 0 ) )
        {
            for ( String suffix : resourceSuffix )
            {
                retVal.add( suffix );
            }
        }

        return retVal.toArray( new String[ retVal.size() ] );
    }
}

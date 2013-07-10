package ge.utils.loader;

import org.apache.log4j.Logger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 06/03/13
 * Time: 14:48
 */
public class DynamicLoader
{
    private static Logger logger = Logger.getLogger( DynamicLoader.class );

    public static void addJar( File file )
    {
        try
        {
            URL url = file.toURI().toURL();

            URLClassLoader systemLoader = ( URLClassLoader ) ClassLoader.getSystemClassLoader();

            URL urls[] = systemLoader.getURLs();
            for ( int i = 0; i < urls.length; i++ )
            {
                if ( urls[ i ].toString().equalsIgnoreCase( url.toString() ) )
                {
                    return;
                }
            }

            logger.debug( "Loading jar: " + file.getPath() );

            Class systemClass = URLClassLoader.class;

            Method systemMethod = systemClass.getDeclaredMethod( "addURL", new Class[]{ URL.class } );

            systemMethod.setAccessible( true );
            systemMethod.invoke( systemLoader, new Object[]{ url } );
        }
        catch ( MalformedURLException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( IllegalAccessException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }
}

package ge.utils.spring.context;

import ge.utils.bundle.Resources;
import ge.utils.spring.support.IconResourcesEditorSupport;
import ge.utils.spring.support.ImageResourcesEditorSupport;
import ge.utils.spring.support.ResourcesEditorSupport;
import org.springframework.beans.BeansException;

import javax.swing.Icon;
import java.awt.Image;
import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 05/03/13
 * Time: 09:59
 */
public class ClasspathApplicationContext extends ConfigurableClassPathXmlApplicationContext
{
    private static Map<String, ClasspathApplicationContext> contexts =
            new HashMap<String, ClasspathApplicationContext>();

    private ClasspathApplicationContext( String configLocation ) throws BeansException
    {
        super( configLocation );
    }

    private ClasspathApplicationContext( String configLocation, ClassLoader classLoader ) throws BeansException
    {
        super( configLocation, classLoader );
    }

    public static ClasspathApplicationContext getInstance( String configLocation )
    {
        if ( contexts.containsKey( configLocation ) == false )
        {
            ClasspathApplicationContext classpathApplicationContext = new ClasspathApplicationContext( configLocation );

            contexts.put( configLocation, classpathApplicationContext );
        }

        return contexts.get( configLocation );
    }

    public static ClasspathApplicationContext getInstance( String configLocation, ClassLoader classLoader )
    {
        if ( contexts.containsKey( configLocation ) == false )
        {
            ClasspathApplicationContext classpathApplicationContext =
                    new ClasspathApplicationContext( configLocation, classLoader );

            contexts.put( configLocation, classpathApplicationContext );
        }

        return contexts.get( configLocation );
    }

    @Override
    public Map<Class<?>, Class<? extends PropertyEditor>> createCustomEditors()
    {
        Map<Class<?>, Class<? extends PropertyEditor>> retVal =
                new HashMap<Class<?>, Class<? extends PropertyEditor>>();

        retVal.put( Resources.class, ResourcesEditorSupport.class );

        retVal.put( Icon.class, IconResourcesEditorSupport.class );

        retVal.put( Image.class, ImageResourcesEditorSupport.class );

        return retVal;
    }
}

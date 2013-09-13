package ge.framework.application;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/02/13
 * Time: 17:01
 */
public class Main
{
    public static void main( String[] args )
            throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
                   IllegalAccessException
    {
//        dumpProperties();

//        ApplicationFramework.start( "Test.Multi.Application", args );
        ApplicationFramework.start( "JsonEditor.Application",args );
    }

    public static void dumpProperties()
    {
        Properties properties = System.getProperties();

        List<String> keys = new ArrayList<String>();

        for ( Object o : properties.keySet() )
        {
            keys.add( ( String ) o );
        }

        Collections.sort( keys );

        for ( String key : keys )
        {
            String property = properties.getProperty( key );

            System.out.println( key + "=" + property );
        }
    }
}

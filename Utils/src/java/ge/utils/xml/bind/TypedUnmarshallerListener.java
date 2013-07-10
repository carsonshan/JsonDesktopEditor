package ge.utils.xml.bind;

import javax.xml.bind.Unmarshaller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/03/13
 * Time: 19:13
 */
public class TypedUnmarshallerListener extends Unmarshaller.Listener
{
    private Map<Class, UnmarshallerListener> listeners = new HashMap<Class, UnmarshallerListener>();

    public <TYPE extends Object> void setListener( Class<TYPE> aClass, UnmarshallerListener<TYPE> listener )
    {
        if ( aClass != null )
        {
            if ( listener == null )
            {
                listeners.remove( aClass );
            }
            else
            {
                listeners.put( aClass, listener );
            }
        }
    }

    public <TYPE extends Object> UnmarshallerListener<TYPE> getListener( Class<TYPE> aClass )
    {
        return listeners.get( aClass );
    }

    @Override
    public void beforeUnmarshal( Object source, Object parent )
    {
        List<Class> allTypes = getAllTypes( source.getClass() );

        for ( Class allType : allTypes )
        {
            if ( listeners.containsKey( allType ) == true )
            {
                UnmarshallerListener marshallerListener = listeners.get( allType );

                marshallerListener.beforeUnmarshal( source, parent );
            }
        }
    }

    @Override
    public void afterUnmarshal( Object source, Object parent )
    {
        List<Class> allTypes = getAllTypes( source.getClass() );

        for ( Class allType : allTypes )
        {
            if ( listeners.containsKey( allType ) == true )
            {
                UnmarshallerListener marshallerListener = listeners.get( allType );

                marshallerListener.afterUnmarshal( source, parent );
            }
        }
    }

    private List<Class> getAllTypes( Class aClass )
    {
        List<Class> retVal = new ArrayList<Class>();

        while ( aClass != null )
        {
            if ( retVal.contains( aClass ) == false )
            {
                retVal.add( aClass );
            }

            Class<?>[] interfaces = aClass.getInterfaces();

            for ( Class<?> anInterface : interfaces )
            {
                if ( retVal.contains( anInterface ) == false )
                {
                    retVal.add( anInterface );
                }
            }

            if ( aClass == Object.class )
            {
                aClass = null;
            }
            else
            {
                aClass = aClass.getSuperclass();
            }
        }

        return retVal;
    }
}

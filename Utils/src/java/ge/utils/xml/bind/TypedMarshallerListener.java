package ge.utils.xml.bind;

import javax.xml.bind.Marshaller;
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
public class TypedMarshallerListener extends Marshaller.Listener
{
    private Map<Class, MarshallerListener> listeners = new HashMap<Class, MarshallerListener>();

    public <TYPE extends Object> void setListener( Class<TYPE> aClass, MarshallerListener<TYPE> listener )
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

    public <TYPE extends Object> MarshallerListener<TYPE> getListener( Class<TYPE> aClass )
    {
        return listeners.get( aClass );
    }

    @Override
    public void beforeMarshal( Object source )
    {
        List<Class> allTypes = getAllTypes( source.getClass() );

        for ( Class allType : allTypes )
        {
            if ( listeners.containsKey( allType ) == true )
            {
                MarshallerListener marshallerListener = listeners.get( allType );

                marshallerListener.beforeMarshal( source );
            }
        }
    }

    @Override
    public void afterMarshal( Object source )
    {
        List<Class> allTypes = getAllTypes( source.getClass() );

        for ( Class allType : allTypes )
        {
            if ( listeners.containsKey( allType ) == true )
            {
                MarshallerListener marshallerListener = listeners.get( allType );

                marshallerListener.afterMarshal( source );
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

package ge.utils.bundle;

import ge.utils.text.StringArgumentMessageFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ArgumentResource
{
    private Resources resources;

    private Class clazz;

    private String[] resourceName;

    private Map<String, Object> arguments = new HashMap<String, Object>();

    public ArgumentResource( Resources resources, String... resourceName )
    {
        this.resources = resources;
        this.resourceName = resourceName;
    }

    public ArgumentResource( Resources resources, Class clazz, String... resourceName )
    {
        this.resources = resources;
        this.clazz = clazz;
        this.resourceName = resourceName;
    }

    public Map<String, Object> getArguments()
    {
        return arguments;
    }

    public void setArguments( Map<String, Object> arguments )
    {
        this.arguments = arguments;
    }

    public Object getArgument( String argumentName )
    {
        return arguments.get( argumentName );
    }

    public void putArgument( String argumentName, String value )
    {
        arguments.put( argumentName, value );
    }

    public void putArgument( String argumentName, Date value )
    {
        arguments.put( argumentName, value );
    }

    public void putArgument( String argumentName, Number value )
    {
        arguments.put( argumentName, value );
    }

    public void putArgument( String argumentName, ArgumentResource value )
    {
        arguments.put( argumentName, value );
    }

    public String getResourceString()
    {
        String resourceString;

        if ( clazz != null )
        {
            resourceString = resources.getResourceString( clazz, resourceName );
        }
        else
        {
            resourceString = resources.getResourceString( resourceName );
        }

        if ( arguments.isEmpty() == true )
        {
            return resourceString;
        }
        else
        {
            Map<String, Object> convertedArguments = new HashMap<String, Object>();

            for ( String key : arguments.keySet() )
            {
                Object argument = arguments.get( key );

                if ( argument instanceof ArgumentResource )
                {
                    ArgumentResource argumentResource = ( ArgumentResource ) argument;
                    convertedArguments.put( key, argumentResource.getResourceString() );
                }
                else
                {
                    convertedArguments.put( key, argument );
                }
            }

            return StringArgumentMessageFormat.format( resourceString, convertedArguments );
        }
    }
}

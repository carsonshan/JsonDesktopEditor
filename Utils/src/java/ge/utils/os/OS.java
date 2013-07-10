package ge.utils.os;

public class OS
{
    public static final OSType osType;

    public static boolean isWindows()
    {
        return ( osType == OSType.WINDOWS );
    }

    public static boolean isMac()
    {
        return ( osType == OSType.MAC );
    }

    public static boolean isUnix()
    {
        return ( osType == OSType.UNIX );
    }

    public static boolean isUnknown()
    {
        return ( osType == OSType.UNKNOWN );
    }

    static
    {
        String os = System.getProperty( "os.name" ).toLowerCase();

        if ( os.indexOf( "win" ) != -1 )
        {
            osType = OSType.WINDOWS;
        }
        else if ( os.indexOf( "mac" ) != -1 )
        {
            osType = OSType.MAC;
        }
        else if ( ( os.indexOf( "nix" ) != -1 ) || ( os.indexOf( "nux" ) != -1 ) )
        {
            osType = OSType.UNIX;
        }
        else
        {
            osType = OSType.UNKNOWN;
        }
    }
}

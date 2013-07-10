package ge.utils.file;

import ge.utils.os.OS;

import java.io.File;

public class FileUtils
{
    public static boolean isFileValidName( File file )
    {
        if ( file.exists() == true )
        {
            return true;
        }
        else
        {
            if ( isFileNameValid( file.getName() ) == false )
            {
                return false;
            }
            else if ( isFileValidName( file.getParentFile() ) == false )
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    public static boolean isFileNameValid( String filename )
    {
        String invalidChars;
        if ( OS.isWindows() )
        {
            invalidChars = "\\/:*?\"<>|";
        }
        else if ( OS.isMac() )
        {
            invalidChars = "/:";
        }
        else
        {
            invalidChars = "/";
        }

        char[] chars = filename.toCharArray();
        for ( int i = 0; i < chars.length; i++ )
        {
            if ( ( invalidChars.indexOf( chars[ i ] ) >= 0 ) || ( chars[ i ] < '\u0020' ) ||
                    ( chars[ i ] > '\u007e' && chars[ i ] < '\u00a0' ) )
            {
                return false;
            }
        }

        return true;
    }

    public static void deleteDirectory( File directory )
    {
        if ( ( directory != null ) && ( directory.exists() == true ) && ( directory.isDirectory() == true ) )
        {
            File[] files = directory.listFiles();

            for ( File file : files )
            {
                if ( file.isDirectory() == true )
                {
                    deleteDirectory( directory );
                }
                else
                {
                    file.delete();
                }
            }

            directory.delete();
        }
    }
}

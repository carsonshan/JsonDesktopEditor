package ge.framework.frame.multi.objects;

import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.utils.file.LockFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/07/13
 * Time: 15:59
 */
public class MultiFrameDefinition<FRAME extends MultiApplicationFrame, CONFIG extends MultiFrameConfiguration> extends
                                                                                                               FrameDefinition<FRAME, CONFIG>
{
    private transient String configurationName;

    public String getConfigurationName()
    {
        return configurationName;
    }

    public void setConfigurationName( String configurationName ) throws IllegalAccessException
    {
        testInitialised();
        this.configurationName = configurationName;
    }

    public File getConfigurationFile( File location )
    {
        return new File( getMetadataDirectory( location ), configurationName );
    }

    public boolean isDirectory( File location )
    {
        File typeConfigurationFile = getConfigurationFile( location );

        return typeConfigurationFile.exists();
    }

    public boolean doesConfigurationFileExist( File location )
    {
        File typeConfigurationFile = getConfigurationFile( location );
        return typeConfigurationFile.exists();
    }

    public boolean isConfigurationFileLocked( File location )
    {
        try
        {
            File typeConfigurationFile = getConfigurationFile( location );
            return LockFile.isFileLocked( typeConfigurationFile );
        }
        catch ( IOException e )
        {
            return false;
        }
    }
}

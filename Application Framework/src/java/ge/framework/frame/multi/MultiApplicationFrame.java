package ge.framework.frame.multi;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.multi.command.FileCommandBar;
import ge.framework.frame.multi.menu.MultiFrameFileMenu;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameConfiguration;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.HeadlessException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/07/13
 * Time: 16:00
 */
public abstract class MultiApplicationFrame<APPLICATION extends MultiFrameApplication,
        CONFIG extends MultiFrameConfiguration> extends
                                                ApplicationFrame<APPLICATION,
                                                        MultiFrameDefinition,
                                                        CONFIG>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    private FileCommandBar fileCommandBar;

    public MultiApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseApplicationFrame()
    {
        fileCommandBar = new FileCommandBar( this );
        
        initialiseMultiApplicationFrame();
    }

    protected abstract void initialiseMultiApplicationFrame();

    @Override
    protected FileMenu createFileMenu()
    {
        return new MultiFrameFileMenu( this );
    }

    public void open( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        this.frameInstanceDetailsObject = frameInstanceDetailsObject;

        boolean newConfig = loadFrameConfiguration( frameInstanceDetailsObject.getName() );
        loadFrameData();

        setLayoutDirectory( frameInstanceDetailsObject.getMetadataDirectory() );

        if ( newConfig == false )
        {
            resetToDefault();
            saveLayoutData();
        }
        else
        {
            loadLayoutData();

            setStatusBarVisible( frameConfiguration.isStatusBarVisible() );
            setAutoHideAreaVisible( frameConfiguration.isToolButtonsVisible() );
        }
    }

    public FileCommandBar getFileCommandBar()
    {
        return fileCommandBar;
    }

    @Override
    public void setTitle( String title )
    {
        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "exTitle" );
        }

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "applicationName", application.getName() );
        arguments.put( "frameName", frameDefinition.getName() );
        arguments.put( "title", title );

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }

    @Override
    protected File getConfigurationFile( String name )
    {
        return frameInstanceDetailsObject.getConfigurationFile();
    }
}

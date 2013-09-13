package ge.framework.frame.single;

import ge.framework.application.single.SingleFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.single.menu.SingleFrameFileMenu;
import ge.framework.frame.single.objects.SingleFrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.Logger;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 08:43
 */
public abstract class SingleApplicationFrame<APPLICATION extends SingleFrameApplication> extends ApplicationFrame<APPLICATION, SingleFrameDefinition>
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private static Logger logger = Logger.getLogger( SingleApplicationFrame.class );

    private static File userHome = new File( System.getProperty( "user.home" ) );

    public SingleApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseApplicationFrame()
    {
        initialiseSingleApplicationFrame();

        loadFrameData();

        setLayoutDirectory( frameDefinition.getMetadataDirectory( userHome ) );

        loadLayoutData();

        setStatusBarVisible( application.isStatusBarVisible() );
        setAutoHideAreaVisible( application.isToolButtonsVisible() );
    }

    protected abstract void initialiseSingleApplicationFrame();

    @Override
    protected FileMenu createFileMenu()
    {
        return new SingleFrameFileMenu( this );
    }

    @Override
    protected void saveApplicationFrame()
    {
        application.setStatusBarVisible( isStatusBarVisible() );
        application.setToolButtonsVisible( isAutoHideAreaVisible() );
    }

    @Override
    public void setTitle( String title )
    {
        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( SingleApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( SingleApplicationFrame.class, "frame", "exTitle" );
        }

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "frameName", frameDefinition.getName() );
        arguments.put( "title", title );

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }

    protected final void processWindowClose()
    {
        saveFrame();
    }
}

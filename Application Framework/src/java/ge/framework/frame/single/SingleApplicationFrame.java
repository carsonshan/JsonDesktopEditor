package ge.framework.frame.single;

import ge.framework.application.multi.dialog.properties.AbstractApplicationPropertiesPage;
import ge.framework.application.multi.dialog.properties.GeneralApplicationPropertiesPage;
import ge.framework.application.multi.objects.ApplicationConfiguration;
import ge.framework.application.single.SingleFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.single.dialog.properties.GeneralSingleFramePropertiesPage;
import ge.framework.frame.single.menu.SingleFrameFileMenu;
import ge.framework.frame.single.objects.SingleFrameConfiguration;
import ge.framework.frame.single.objects.SingleFrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.Logger;

import java.awt.HeadlessException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 08:43
 */
public abstract class SingleApplicationFrame<APPLICATION extends SingleFrameApplication, CONFIG extends SingleFrameConfiguration> extends
                                                                                                                                  ApplicationFrame<APPLICATION, SingleFrameDefinition, CONFIG>
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

        boolean newConfig = loadFrameConfiguration( frameDefinition.getName() );

        loadFrameData();

        setLayoutDirectory( frameDefinition.getMetadataDirectory( userHome ) );

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

    @Override
    protected File getConfigurationFile( String name )
    {
        File metadataDirectory = frameDefinition.getMetadataDirectory( userHome );
        return new File( metadataDirectory, name );
    }

    protected abstract void initialiseSingleApplicationFrame();

    @Override
    public final List<AbstractFramePropertiesPage> getFrameConfigurationPages()
    {
        List<AbstractFramePropertiesPage> retVal = new ArrayList<AbstractFramePropertiesPage>();

        retVal.add( new GeneralSingleFramePropertiesPage() );

        List<AbstractFramePropertiesPage> applicationConfigurationPages =
                getOtherFrameConfigurationPages();

        if ( ( applicationConfigurationPages != null ) && ( applicationConfigurationPages.isEmpty() == false ) )
        {
            retVal.addAll( applicationConfigurationPages );
        }

        return retVal;
    }

    protected abstract List<AbstractFramePropertiesPage> getOtherFrameConfigurationPages();

    @Override
    protected FileMenu createFileMenu()
    {
        return new SingleFrameFileMenu( this );
    }

    public void setAskBeforeExit( boolean askBeforeExit )
    {
        frameConfiguration.setAskBeforeExit( askBeforeExit );
    }

    public boolean isAskBeforeExit()
    {
        return frameConfiguration.isAskBeforeExit();
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
}

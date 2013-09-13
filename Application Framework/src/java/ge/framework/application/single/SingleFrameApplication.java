package ge.framework.application.single;

import ge.framework.application.core.Application;
import ge.framework.application.core.dialog.properties.AbstractApplicationPropertiesPage;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.multi.dialog.properties.GeneralMultiApplicationPropertiesPage;
import ge.framework.application.single.dialog.properties.GeneralSingleApplicationPropertiesPage;
import ge.framework.application.single.objects.SingleApplicationConfiguration;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.framework.frame.single.objects.SingleFrameDefinition;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.swing.Icon;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 07:47
 */
public abstract class SingleFrameApplication<FRAME extends SingleApplicationFrame, DEFINITION extends SingleFrameDefinition> extends
                                                                                                                             Application<SingleApplicationConfiguration>
{
    private static Logger logger = Logger.getLogger( SingleFrameApplication.class );

    protected ApplicationWindowAdapter applicationWindowAdapter = new ApplicationWindowAdapter( this );

    private FRAME applicationFrame;

    private DEFINITION frameDefinition;

    @Override
    protected void startupApplication()
    {
        if ( applicationFrame == null )
        {
            try
            {
                Class<? extends FRAME> frameClass = frameDefinition.getFrameClass();

                Constructor<? extends FRAME> constructor =
                        frameClass.getConstructor( getClass() );

                applicationFrame = constructor.newInstance( this );

                applicationFrame.initialise( frameDefinition );

                applicationFrame.addWindowListener( applicationWindowAdapter );
            }
            catch ( Exception e )
            {
                logger.error( e.getMessage(), e );
                System.exit( -1 );
            }
        }
        else
        {
            logger.error( "Attempting to start an already started application" );
        }
    }

    @Override
    protected void validateApplicationObject()
    {
        notNull( frameDefinition );
    }

    public final void setFrameDefinition( DEFINITION frameDefinition ) throws IllegalAccessException
    {
        testInitialised();
        this.frameDefinition = frameDefinition;
    }

    public final DEFINITION getFrameDefinition()
    {
        return frameDefinition;
    }

    public boolean isStatusBarVisible()
    {
        return configuration.isStatusBarVisible();
    }

    public boolean isToolButtonsVisible()
    {
        return configuration.isToolButtonsVisible();
    }

    public void setStatusBarVisible( boolean statusBarVisible )
    {
        configuration.setStatusBarVisible( statusBarVisible );
    }

    public void setToolButtonsVisible( boolean autoHideAreaVisible )
    {
        configuration.setToolButtonsVisible( autoHideAreaVisible );
    }

    public static class ApplicationWindowAdapter extends WindowAdapter
    {
        private SingleFrameApplication singleFrameApplication;

        public ApplicationWindowAdapter( SingleFrameApplication singleFrameApplication )
        {
            this.singleFrameApplication = singleFrameApplication;
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            Object source = e.getSource();
            if ( singleFrameApplication.applicationFrame == source )
            {
                singleFrameApplication.applicationFrame = null;
            }
        }
    }

    @Override
    public final CloseOrExitEnum closeOrExit()
    {
        return CloseOrExitEnum.EXIT;
    }

    @Override
    public Frame discoverFocusedFrame()
    {
        return applicationFrame;
    }

    @Override
    public Icon getLargeIcon()
    {
        return frameDefinition.getLargeIcon();
    }

    @Override
    public Image getSmallImage()
    {
        return frameDefinition.getSmallImage();
    }

    @Override
    protected ApplicationFrame getFocusedFrame()
    {
        return applicationFrame;
    }

    @Override
    protected void closeAllFrames()
    {
        applicationFrame.removeWindowListener( applicationWindowAdapter );
        applicationFrame.close();
    }

    @Override
    protected boolean isExitProcessRequired()
    {
        return false;
    }

    @Override
    protected void updateApplication()
    {
    }

    protected final void initialiseApplicationConfiguration()
    {
        initialiseSingleFrameApplicationConfiguration();
    }

    protected abstract void initialiseSingleFrameApplicationConfiguration();

    public final List<PropertiesDialogPage<? extends ApplicationConfiguration>> getApplicationConfigurationPages()
    {
        List<PropertiesDialogPage<? extends ApplicationConfiguration>> retVal =
                new ArrayList<PropertiesDialogPage<? extends ApplicationConfiguration>>();

        retVal.add( new GeneralSingleApplicationPropertiesPage() );

        List<AbstractApplicationPropertiesPage> multiApplicationConfigurationPages =
                getSingleApplicationConfigurationPages();

        if ( ( multiApplicationConfigurationPages != null ) &&
                ( multiApplicationConfigurationPages.isEmpty() == false ) )
        {
            retVal.addAll( multiApplicationConfigurationPages );
        }

        return retVal;
    }

    public abstract List<AbstractApplicationPropertiesPage> getSingleApplicationConfigurationPages();
}

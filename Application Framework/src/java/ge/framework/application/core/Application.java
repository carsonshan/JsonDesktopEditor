package ge.framework.application.core;

import ge.framework.application.core.dialog.ExitDialog;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.message.enums.MessageResult;
import ge.utils.spring.ApplicationContextAwareObject;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/07/13
 * Time: 16:19
 */
public abstract class Application extends ApplicationContextAwareObject
{
    private static Logger logger = Logger.getLogger( Application.class );

    private boolean allowMultipleApplications;

    private boolean resetting;

    private String name;

    public final boolean isAllowMultipleApplications()
    {
        return allowMultipleApplications;
    }

    public final void setAllowMultipleApplications( boolean allowMultipleApplications ) throws IllegalAccessException
    {
        testInitialised();
        this.allowMultipleApplications = allowMultipleApplications;
    }

    public final void startup( String[] args )
    {
        logger.debug( "Starting application" );

        initialiseApplication( args );

        startupApplication();
    }

    protected abstract void initialiseApplication( String[] args );

    protected abstract void startupApplication();

    public boolean processExit()
    {
        logger.debug( "Processing Exit" );
        if ( isExitProcessRequired() == true )
        {
            System.exit( 0 );
            return true;
        }
        else
        {
            if ( resetting == false )
            {
                if ( isAskBeforeExit() == true )
                {
                    ExitDialog exitDialog = new ExitDialog( getFocusedFrame() );

                    MessageResult messageResult = exitDialog.doModal();

                    setAskBeforeExit( !exitDialog.isCheckBoxSelected() );

                    if ( messageResult == MessageResult.CANCEL )
                    {
                        return false;
                    }
                }
            }

            logger.debug( "Closing frames." );

            closeAllFrames();

            updateApplication();

            return true;
        }
    }

    protected abstract void updateApplication();

    protected abstract ApplicationFrame getFocusedFrame();

    protected abstract void closeAllFrames();

    protected abstract boolean isAskBeforeExit();

    protected abstract void setAskBeforeExit( boolean askBeforeExit );

    protected abstract boolean isExitProcessRequired();

    public abstract CloseOrExitEnum closeOrExit();

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}

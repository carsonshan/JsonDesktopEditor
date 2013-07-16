package ge.framework.application.single;

import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.framework.frame.single.objects.SingleFrameDefinition;
import org.apache.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 07:47
 */
public abstract class SingleFrameApplication<FRAME extends SingleApplicationFrame, DEFINITION extends SingleFrameDefinition> extends
                                                                                                                             Application
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
    protected final void validateBeanObject()
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

    @Override
    protected boolean isAskBeforeExit()
    {
        return applicationFrame.isAskBeforeExit();
    }

    @Override
    protected void setAskBeforeExit( boolean askBeforeExit )
    {
        applicationFrame.setAskBeforeExit( askBeforeExit );
    }
}

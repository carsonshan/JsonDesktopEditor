package ge.framework.application.core.application;

import ge.framework.application.core.frame.objects.FrameDefinition;
import ge.utils.spring.ApplicationContextAwareObject;
import org.apache.log4j.Logger;

import java.io.File;

import static org.springframework.util.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/07/13
 * Time: 16:19
 */
public abstract class Application extends ApplicationContextAwareObject
{
    private static Logger logger = Logger.getLogger( Application.class );

    private static File userDirectory = new File( System.getProperty( "user.home" ) );

    private boolean allowMultipleApplications;

    private FrameDefinition frameDefinition;

    @Override
    protected final void validateBeanObject()
    {
        notNull( frameDefinition );
    }

    public final boolean isAllowMultipleApplications()
    {
        return allowMultipleApplications;
    }

    public final void setAllowMultipleApplications( boolean allowMultipleApplications ) throws IllegalAccessException
    {
        testInitialised();
        this.allowMultipleApplications = allowMultipleApplications;
    }

    public final void setFrameDefinition( FrameDefinition frameDefinition ) throws IllegalAccessException
    {
        testInitialised();
        this.frameDefinition = frameDefinition;
    }

    public final FrameDefinition getFrameDefinition()
    {
        return frameDefinition;
    }

    public final void startup( String[] args )
    {
        initialiseApplication( args );

        startupApplication( this );
    }

    protected abstract void initialiseApplication( String[] args );

    protected abstract void startupApplication( Application application );
}

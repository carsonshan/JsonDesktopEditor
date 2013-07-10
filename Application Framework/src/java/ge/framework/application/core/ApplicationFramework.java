package ge.framework.application.core;

import com.jidesoft.plaf.LookAndFeelFactory;
import ge.framework.application.core.application.Application;
import ge.utils.VMInstance;
import ge.utils.os.OS;
import ge.utils.spring.context.ClasspathApplicationContext;
import org.apache.log4j.Logger;

import javax.swing.UIManager;
import java.awt.Color;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 08:23
 */
public class ApplicationFramework
{
    private static Logger logger = Logger.getLogger( ApplicationFramework.class );

    public static void start( String[] args )
    {
        start( null, args );
    }

    public static void start( String applicationBeanName, String[] args )
    {
        start( "application.xml", applicationBeanName, args );
    }

    public static void start( String applicationConfiguration,
                              String applicationBeanName, String[] args )
    {
        //TODO
//        ApplicationRestarter.initialiseRestart( args );
//
//        String pidString = System.getProperty( ApplicationRestarter.RESTART_PID );
//
//        if ( ( pidString != null ) && ( pidString.isEmpty() == false ) )
//        {
//            int pid = Integer.parseInt( pidString );
//
//            while ( VMInstance.isVmRunning( pid ) == true )
//            {
//                try
//                {
//                    Thread.sleep( 1000 );
//                }
//                catch ( InterruptedException e )
//                {
//                }
//            }
//        }

        ClasspathApplicationContext classpathApplicationContext = ClasspathApplicationContext.getInstance(
                applicationConfiguration );

        if ( ( applicationBeanName == null ) || ( applicationBeanName.isEmpty() == true ) )
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( Application.class );

            if ( ( beanNamesForType == null ) || ( beanNamesForType.length == 0 ) )
            {
                throw new IllegalStateException( "Failed to find a bean of type Application" );
            }
            else if ( beanNamesForType.length != 1 )
            {
                throw new IllegalStateException( "Found multiple beans of type Application" );
            }
            else
            {
                applicationBeanName = beanNamesForType[ 0 ];
            }
        }

        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();

        if ( OS.isWindows() == true )
        {
            LookAndFeelFactory.installJideExtension( LookAndFeelFactory.EXTENSION_STYLE_ECLIPSE3X );
        }

        UIManager.put( "DockableFrameTitlePane.showIcon", Boolean.TRUE );
        UIManager.put( "JideTabbedPane.showIconOnTab", Boolean.TRUE );
        UIManager.put( "DockableFrameTitlePane.titleBarComponent", Boolean.FALSE );
        UIManager.put( "SidePane.alwaysShowTabText", Boolean.TRUE );

        Color contentBackground = new Color( 240, 240, 240 );
        Color workspaceBackground = new Color( 220, 220, 220 );

        UIManager.put( "ContentContainer.background", contentBackground );

        UIManager.put( "Workspace.background", workspaceBackground );

        UIManager.put( "ButtonPanel.order", "ACO" );
        UIManager.put( "ButtonPanel.oppositeOrder", "H" );

        Application application;
        if ( ( applicationBeanName != null ) && ( applicationBeanName.isEmpty() == false ) )
        {
            application = classpathApplicationContext.getBean( applicationBeanName, Application.class );
        }
        else
        {
            String[] beanNamesForType = classpathApplicationContext.getBeanNamesForType( Application.class );

            if ( ( beanNamesForType == null ) || ( beanNamesForType.length == 0 ) )
            {
                throw new IllegalStateException( "Failed to find a bean of type Application" );
            }
            else if ( beanNamesForType.length != 1 )
            {
                throw new IllegalStateException( "Found multiple beans of type Application" );
            }
            else
            {
                application = classpathApplicationContext.getBean( beanNamesForType[ 0 ], Application.class );
            }
        }

        if ( application.isAllowMultipleApplications() == false )
        {
            if ( VMInstance.isVmUnique() == false )
            {
                logger.fatal( "Another instance of this app already exists." );
                System.exit( -1 );
            }
        }

        application.startup( args );
    }
}

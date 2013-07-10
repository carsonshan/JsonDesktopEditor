package ge.framework.application.core.frame;

import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.ContentContainer;
import ge.framework.application.core.frame.command.ApplicationCommandBarComponent;
import ge.framework.application.core.frame.command.menu.ApplicationCommandBarMenu;
import ge.framework.application.core.frame.document.ApplicationDocumentComponent;
import ge.framework.application.core.frame.document.menu.ApplicationDocumentComponentMenu;
import ge.framework.application.core.frame.manager.ApplicationDockableBarManager;
import ge.framework.application.core.frame.manager.ApplicationDockableFrame;
import ge.framework.application.core.frame.manager.ApplicationDockingManager;
import ge.framework.application.core.frame.manager.menu.ApplicationDockableFrameMenu;
import ge.framework.application.core.frame.manager.menu.ToggleToolButtonsMenuItem;
import ge.framework.application.core.frame.menu.ViewMenu;
import ge.framework.application.core.frame.objects.FrameDefinition;
import ge.framework.application.core.frame.persistence.ApplicationLayoutPersistenceManager;
import ge.framework.application.core.frame.status.ApplicationStatusBar;
import ge.framework.application.core.frame.status.enums.StatusBarConstraint;
import ge.framework.application.core.frame.status.menu.item.ToggleStatusBarMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import ge.utils.os.OS;
import ge.utils.text.StringArgumentMessageFormat;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 11:24
 */
public abstract class ApplicationFrame extends JFrame
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private static Logger logger = Logger.getLogger( ApplicationFrame.class );

    private FrameDefinition frameDefinition;

    private ApplicationFrameWindowAdapter applicationFrameWindowAdapter;

    private ApplicationDockableBarManager dockableBarManager;

    private ApplicationDockingManager dockingManager;

    private ApplicationLayoutPersistenceManager layoutPersistenceManager;

    private BreadcrumbBar breadcrumbBar;

    private ApplicationStatusBar statusBar;

    private ViewMenu viewMenu;

    private boolean manualClose;

    public void initialise( FrameDefinition frameDefinition )
    {
        if ( this.frameDefinition == null )
        {
            this.frameDefinition = frameDefinition;

            initialiseFrame();

            setTitle( null );
            if ( OS.isMac() == true )
            {
                setIconImage( frameDefinition.getMacImage() );
            }
            else
            {
                setIconImage( frameDefinition.getSmallImage() );
            }

            viewMenu = new ViewMenu( this );

            initialiseApplicationFrame();
        }
    }

    private void initialiseFrame()
    {
        logger.trace( "Initialising frame." );

        applicationFrameWindowAdapter = new ApplicationFrameWindowAdapter( this );

        addWindowListener( applicationFrameWindowAdapter );

        setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );

        ContentContainer localContentContainer = new ContentContainer();

        Container contentPane = getContentPane();

        contentPane.setLayout( new BorderLayout() );
        contentPane.add( localContentContainer, BorderLayout.CENTER );

        logger.trace( "Initialising dockable bar manager." );
        dockableBarManager = new ApplicationDockableBarManager( this, localContentContainer );
        dockableBarManager.setRearrangable( true );

        logger.trace( "Initialising docking manager." );
        dockingManager = new ApplicationDockingManager( this, dockableBarManager );

        breadcrumbBar = createBreadcrumbBar();

        if ( breadcrumbBar != null )
        {
            Container contentContainer = dockingManager.getContentContainer();
            JScrollPane scrollPane = new JScrollPane( breadcrumbBar, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                                                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
            scrollPane.setBorder( null );
            contentContainer.add( scrollPane, BorderLayout.BEFORE_FIRST_LINE );
        }

        logger.trace( "Initialising status bar." );
        statusBar = new ApplicationStatusBar();
        localContentContainer.add( statusBar, BorderLayout.AFTER_LAST_LINE );

        logger.trace( "Initialising layout persistence manager." );
        layoutPersistenceManager = new ApplicationLayoutPersistenceManager( frameDefinition.getBeanName() );
        layoutPersistenceManager.addLayoutPersistence( dockableBarManager );
        layoutPersistenceManager.addLayoutPersistence( dockingManager );
    }

    protected abstract BreadcrumbBar createBreadcrumbBar();

    public final void close()
    {
        manualClose = true;
        logger.info( "Firing close event." );
        WindowEvent windowEvent = new WindowEvent( this, WindowEvent.WINDOW_CLOSING );
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent( windowEvent );
    }

    protected abstract void processWindowClose();

    final void closing()
    {
        if ( confirmWindowClosing() == true )
        {
            logger.debug( "Closing window." );

            try
            {
                processWindowClose();
            }
            catch ( Exception e )
            {
                logger.warn( e.getMessage(), e );
            }

            if ( layoutPersistenceManager != null )
            {
                layoutPersistenceManager.clear();
                layoutPersistenceManager = null;
            }

            if ( dockableBarManager != null )
            {
                dockableBarManager.dispose();
                dockableBarManager = null;
            }

            if ( dockingManager != null )
            {
                dockingManager.dispose();
                dockingManager = null;
            }

            try
            {
                dispose();
            }
            catch ( Exception e )
            {
                logger.warn( e.getMessage(), e );
            }
        }
    }

    public abstract boolean confirmWindowClosing();

    final void closed()
    {
        logger.debug( "Window closed." );

        removeWindowListener( applicationFrameWindowAdapter );
        applicationFrameWindowAdapter.dispose();
        applicationFrameWindowAdapter = null;
    }

    public void addFrame( ApplicationDockableFrame dockableFrame )
    {
        dockingManager.addFrame( dockableFrame );
    }

    public void removeFrame( ApplicationDockableFrame dockableFrame )
    {
        dockingManager.removeFrame( dockableFrame );
    }

    public ApplicationDockableFrameMenu getFrameWindowMenu()
    {
        return dockingManager.getApplicationDockableFrameMenu();
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.openDocument( applicationDocumentComponent );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent,
                              boolean floating )
    {
        dockingManager.openDocument( applicationDocumentComponent, floating );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.closeDocument( applicationDocumentComponent );
    }

    public void closeAllDocuments()
    {
        dockingManager.closeAll();
    }

    public void closeAllButThis(
            ApplicationDocumentComponent applicationDocumentComponent )
    {
        dockingManager.closeAllButThis( applicationDocumentComponent );
    }

    public ApplicationDocumentComponentMenu getDocumentComponentMenu()
    {
        return dockingManager.getApplicationDocumentComponentMenu();
    }

    public final void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            logger.debug( "Adding dockable bar: " + dockableBar.getKey() );
            dockableBarManager.addDockableBar( dockableBar );
        }
    }

    public final void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        if ( dockableBar != null )
        {
            logger.debug( "Removing dockable bar: " + dockableBar.getKey() );
            dockableBarManager.removeDockableBar( dockableBar );
        }
    }

    public ApplicationCommandBarMenu getApplicationCommandBarMenu()
    {
        return dockableBarManager.getApplicationCommandBarMenu();
    }

    public void addStatusBarItem( StatusBarItem statusBarItem,
                                  StatusBarConstraint constraint )
    {
        statusBar.add( statusBarItem, constraint );
    }

    public void removeStatusBarItem( StatusBarItem statusBarItem )
    {
        statusBar.remove( statusBarItem );
    }

    @Override
    public void setTitle( String title )
    {
        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( ApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( ApplicationFrame.class, "frame", "exTitle" );
        }

        //TODO: Replace Application
//        Application application = ApplicationManager.getApplication();

        Map<String, Object> arguments = new HashMap<String, Object>();
//        arguments.put( "applicationName", application.getName() );
        arguments.put( "frameName", frameDefinition.getName() );
        arguments.put( "title", title );

//        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( "resourceString" );
    }

    public ViewMenu getViewMenu()
    {
        return viewMenu;
    }

    protected abstract void initialiseApplicationFrame();

    public void setLayoutDirectory( File metadataDirectory )
    {
        layoutPersistenceManager.setLayoutDirectory( metadataDirectory );
    }

    public void resetToDefault()
    {
        layoutPersistenceManager.resetToDefault();
    }

    public void saveLayoutData()
    {
        layoutPersistenceManager.saveLayoutData();
    }

    public void loadLayoutData()
    {
        layoutPersistenceManager.loadLayoutData();
    }

    public final FrameDefinition getFrameDefinition()
    {
        return frameDefinition;
    }

    public void setAutoHideAreaVisible( boolean visible )
    {
        dockingManager.setAutoHideAreaVisible( visible );
    }

    public boolean isAutoHideAreaVisible()
    {
        return dockingManager.isAutoHideAreaVisible();
    }

    public ToggleToolButtonsMenuItem getToggleToolButtonsMenuItem()
    {
        return dockingManager.getToggleToolButtonsMenuItem();
    }

    public void setStatusBarVisible( boolean visible )
    {
        statusBar.setVisible( visible );
    }

    public boolean isStatusBarVisible()
    {
        return statusBar.isVisible();
    }

    public ToggleStatusBarMenuItem getToggleStatusBarMenuItem()
    {
        return statusBar.getToggleStatusBarMenuItem();
    }

    public boolean isManualClose()
    {
        return manualClose;
    }

    /**
     * Created with IntelliJ IDEA.
     * User: evison_g
     * Date: 26/02/13
     * Time: 10:16
     */
    public static class ApplicationFrameWindowAdapter extends WindowAdapter
    {
        private ApplicationFrame applicationFrame;

        public ApplicationFrameWindowAdapter( ApplicationFrame applicationFrame )
        {
            this.applicationFrame = applicationFrame;
        }

        @Override
        public void windowClosing( WindowEvent e )
        {
            if ( applicationFrame.isManualClose() == true ) //||
//                    ( ApplicationManager.closeOrExit() == ApplicationManager.CloseOrExitEnum.CLOSE ) )
            {
                applicationFrame.closing();
            }
            else
            {
                //TODO: Replace
//                ApplicationManager.processExit();
            }
        }

        @Override
        public void windowClosed( WindowEvent e )
        {
            applicationFrame.closed();
        }

        public void dispose()
        {
            applicationFrame = null;
        }
    }
}

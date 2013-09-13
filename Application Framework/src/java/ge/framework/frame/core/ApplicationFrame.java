package ge.framework.frame.core;

import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.ContentContainer;
import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.command.menu.ApplicationCommandBarMenu;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.dockable.menu.ApplicationDockableFrameMenu;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.menu.ApplicationDocumentComponentMenu;
import ge.framework.frame.core.manager.ApplicationDockableBarManager;
import ge.framework.frame.core.manager.ApplicationDockingManager;
import ge.framework.frame.core.manager.menu.ToggleToolButtonsMenuItem;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.core.menu.ViewMenu;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.core.persistence.ApplicationLayoutPersistenceManager;
import ge.framework.frame.core.status.ApplicationStatusBar;
import ge.framework.frame.core.status.enums.StatusBarConstraint;
import ge.framework.frame.core.status.menu.item.ToggleStatusBarMenuItem;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import ge.utils.file.LockFile;
import ge.utils.os.OS;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 11:24
 */
@SuppressWarnings("unused")
public abstract class ApplicationFrame<APPLICATION extends Application, DEFINITION extends FrameDefinition> extends JFrame
{
    private static Logger logger = Logger.getLogger( ApplicationFrame.class );

    protected APPLICATION application;

    protected DEFINITION frameDefinition;

    private ApplicationFrameWindowAdapter applicationFrameWindowAdapter;

    private ApplicationDockableBarManager dockableBarManager;

    private ApplicationDockingManager dockingManager;

    private ApplicationLayoutPersistenceManager layoutPersistenceManager;

    private BreadcrumbBar breadcrumbBar;

    private ApplicationStatusBar statusBar;

    private FileMenu fileMenu;

    private ViewMenu viewMenu;

    private boolean manualClose;

    public ApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        this.application = application;
    }

    public void initialise( DEFINITION frameDefinition )
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

            fileMenu = createFileMenu();
            viewMenu = new ViewMenu( this );

            initialiseApplicationFrame();
        }
    }

    protected abstract void loadFrameData();

    protected abstract FileMenu createFileMenu();

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

    public FileMenu getFileMenu()
    {
        return fileMenu;
    }

    public ViewMenu getViewMenu()
    {
        return viewMenu;
    }

    protected abstract void initialiseApplicationFrame();

    public void saveFrame()
    {
        saveFrameData();

        saveLayoutData();

        saveApplicationFrame();
    }

    protected abstract void saveApplicationFrame();

    protected abstract void saveFrameData();

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

    public APPLICATION getApplication()
    {
        return application;
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
            Application application = applicationFrame.getApplication();

            if ( ( applicationFrame.isManualClose() == true ) ||
                    ( application.closeOrExit() == CloseOrExitEnum.CLOSE ) )
            {
                applicationFrame.closing();
            }
            else
            {
                application.processExit();
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

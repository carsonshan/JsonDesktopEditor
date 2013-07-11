package ge.framework.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.docking.AutoHideContainer;
import com.jidesoft.docking.DefaultDockingManager;
import com.jidesoft.docking.DockContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.Workspace;
import com.jidesoft.docking.event.DockableFrameAdapter;
import com.jidesoft.docking.event.DockableFrameEvent;
import com.jidesoft.swing.JideTabbedPane;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.ApplicationDocumentPane;
import ge.framework.frame.core.document.menu.ApplicationDocumentComponentMenu;
import ge.framework.frame.core.manager.menu.ApplicationDockableFrameMenu;
import ge.framework.frame.core.manager.menu.ToggleToolButtonsMenuItem;
import org.apache.log4j.Logger;

import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Container;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:26
 */
public class ApplicationDockingManager extends DefaultDockingManager
{
    private static final Logger logger = Logger.getLogger( ApplicationDockingManager.class );

    private final ApplicationDockableFrameMenu applicationDockableFrameMenu;

    private final ApplicationDocumentPane documentPane;

    private final ToggleToolButtonsMenuItem toggleToolButtonsMenuItem;

    public ApplicationDockingManager( ApplicationFrame applicationFrame,
                                      DefaultDockableBarManager dockableBarManager )
    {
        super( applicationFrame, dockableBarManager.getMainContainer() );

        applicationDockableFrameMenu = new ApplicationDockableFrameMenu( applicationFrame );

        addDockableFrameListener( new ApplicationDockingManagerAdapter( this ) );

        setTabbedPaneCustomizer( new ApplicationTabbedPaneCustomizer() );

        logger.trace( "Initialising workspace." );
        Workspace dockingManagerWorkspace = getWorkspace();
        dockingManagerWorkspace.setAdjustOpacityOnFly( true );

        logger.trace( "Initialising document pane." );
        documentPane = new ApplicationDocumentPane( null );
        dockingManagerWorkspace.add( documentPane, BorderLayout.CENTER );

        toggleToolButtonsMenuItem = new ToggleToolButtonsMenuItem( this );
    }

    public ApplicationDockableFrameMenu getApplicationDockableFrameMenu()
    {
        return applicationDockableFrameMenu;
    }

    public ToggleToolButtonsMenuItem getToggleToolButtonsMenuItem()
    {
        return toggleToolButtonsMenuItem;
    }

    public final void addFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            logger.debug( "Adding dockable frame: " + dockableFrame.getKey() );
            super.addFrame( dockableFrame );
        }
    }

    public final void removeFrame( ApplicationDockableFrame dockableFrame )
    {
        if ( dockableFrame != null )
        {
            logger.debug( "Removing dockable frame: " + dockableFrame.getKey() );
            super.removeFrame( dockableFrame.getKey() );
        }
    }

    final void added( ApplicationDockableFrame applicationDockableFrame )
    {
        applicationDockableFrameMenu.add( ( ApplicationFrameComponent ) applicationDockableFrame );
    }

    final void removed( ApplicationDockableFrame applicationDockableFrame )
    {
        applicationDockableFrameMenu.remove( ( ApplicationFrameComponent ) applicationDockableFrame );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        openDocument( applicationDocumentComponent, false );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating )
    {
        documentPane.openDocument( applicationDocumentComponent, floating );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeDocument( applicationDocumentComponent );
    }

    public void closeAll()
    {
        documentPane.closeAll();
    }

    public void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent )
    {
        documentPane.closeAllButThis( applicationDocumentComponent );
    }

    public ApplicationDocumentComponentMenu getApplicationDocumentComponentMenu()
    {
        return documentPane.getApplicationDocumentComponentMenu();
    }

    public void setAutoHideAreaVisible( boolean visible )
    {
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_NORTH, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_SOUTH, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_EAST, visible );
        setAutoHideAreaVisible( DockContext.DOCK_SIDE_WEST, visible );
    }

    private void setAutoHideAreaVisible( int side, boolean visible )
    {
        AutoHideContainer autoHideContainer = getAutoHideContainer( side );

        Container viewPort = autoHideContainer.getParent();
        Container simpleScrollPanel = viewPort.getParent();
        simpleScrollPanel.setVisible( visible );
        Container parent = simpleScrollPanel.getParent();
        parent.validate();
    }

    public boolean isAutoHideAreaVisible()
    {
        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_NORTH ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_SOUTH ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_EAST ) == true )
        {
            return true;
        }

        if ( isAutoHideAreaVisible( DockContext.DOCK_SIDE_WEST ) == true )
        {
            return true;
        }

        return false;
    }

    private boolean isAutoHideAreaVisible( int side )
    {
        AutoHideContainer autoHideContainer = getAutoHideContainer( side );

        if ( autoHideContainer != null )
        {
            Container viewPort = autoHideContainer.getParent();
            Container simpleScrollPanel = viewPort.getParent();
            return simpleScrollPanel.isVisible();
        }
        else
        {
            return false;
        }
    }

    public ApplicationFrame getApplicationFrame()
    {
        return ( ApplicationFrame ) getRootPaneContainer();
    }

    private class ApplicationTabbedPaneCustomizer implements TabbedPaneCustomizer
    {
        @Override
        public void customize( JideTabbedPane jideTabbedPane )
        {
            jideTabbedPane.setTabPlacement( SwingConstants.TOP );
        }
    }

    public static class ApplicationDockingManagerAdapter extends DockableFrameAdapter
    {
        private ApplicationDockingManager applicationDockingManager;

        public ApplicationDockingManagerAdapter( ApplicationDockingManager applicationDockingManager )
        {
            this.applicationDockingManager = applicationDockingManager;
        }

        @Override
        public void dockableFrameAdded( DockableFrameEvent dockableFrameEvent )
        {
            DockableFrame dockableFrame = dockableFrameEvent.getDockableFrame();

            if ( dockableFrame instanceof ApplicationDockableFrame )
            {
                applicationDockingManager.added( ( ApplicationDockableFrame ) dockableFrame );
            }
        }

        @Override
        public void dockableFrameRemoved( DockableFrameEvent dockableFrameEvent )
        {
            DockableFrame dockableFrame = dockableFrameEvent.getDockableFrame();

            if ( dockableFrame instanceof ApplicationDockableFrame )
            {
                applicationDockingManager.removed( ( ApplicationDockableFrame ) dockableFrame );
            }
        }
    }
}

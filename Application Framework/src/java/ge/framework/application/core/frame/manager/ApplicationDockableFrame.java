package ge.framework.application.core.frame.manager;

import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.event.DockableFrameAdapter;
import com.jidesoft.docking.event.DockableFrameEvent;
import ge.framework.application.core.frame.ApplicationFrame;
import ge.framework.application.core.frame.ApplicationFrameComponent;
import ge.framework.application.core.frame.manager.menu.ApplicationDockableFrameMenuItem;
import ge.framework.application.core.frame.menu.item.ApplicationFrameComponentMenuItem;
import org.jdom2.Element;

import javax.swing.Icon;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 19:05
 */
public abstract class ApplicationDockableFrame extends DockableFrame implements ApplicationFrameComponent
{
    private ApplicationDockableFrameMenuItem frameMenuItem;

    protected ApplicationDockableFrame()
    {
        super();
        initialise();
    }

    protected ApplicationDockableFrame( String key )
    {
        super( key );
        initialise();
    }

    private void initialise()
    {
        addDockableFrameListener( new ApplicationDockableFrameAdapter( this ) );

        frameMenuItem = new ApplicationDockableFrameMenuItem( this );
    }

    public ApplicationFrameComponentMenuItem getMenuItem()
    {
        return frameMenuItem;
    }

    @Override
    public Icon getMenuIcon()
    {
        return getFrameIcon();
    }

    @Override
    public String getMenuTitle()
    {
        return getTitle();
    }

    void added()
    {
        frameMenuItem.setVisible( true );
    }

    void removed()
    {
        frameMenuItem.setVisible( false );
    }

    public void showFrame()
    {
        ApplicationDockingManager dockingManager = ( ApplicationDockingManager ) getDockingManager();

        dockingManager.activateFrame( getKey() );
    }

    @Override
    public final ApplicationFrame getApplicationFrame()
    {
        ApplicationDockingManager dockingManager = ( ApplicationDockingManager ) getDockingManager();
        return dockingManager.getApplicationFrame();
    }

    public abstract Element saveLayoutData();

    public abstract void loadLayoutData( Element element );

    private class ApplicationDockableFrameAdapter extends DockableFrameAdapter
    {
        private final ApplicationDockableFrame applicationDockableFrame;

        public ApplicationDockableFrameAdapter(
                ApplicationDockableFrame applicationDockableFrame )
        {
            this.applicationDockableFrame = applicationDockableFrame;
        }

        @Override
        public void dockableFrameAdded( DockableFrameEvent dockableFrameEvent )
        {
            applicationDockableFrame.added();
        }

        @Override
        public void dockableFrameRemoved( DockableFrameEvent dockableFrameEvent )
        {
            applicationDockableFrame.removed();
        }
    }
}

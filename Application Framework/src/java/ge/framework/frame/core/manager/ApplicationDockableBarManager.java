package ge.framework.frame.core.manager;

import com.jidesoft.action.DefaultDockableBarManager;
import com.jidesoft.action.DockableBar;
import com.jidesoft.action.event.DockableBarAdapter;
import com.jidesoft.action.event.DockableBarEvent;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.command.menu.ApplicationCommandBarMenu;

import java.awt.Container;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:45
 */
public class ApplicationDockableBarManager extends DefaultDockableBarManager
{
    private ApplicationCommandBarMenu applicationCommandBarMenu;

    private ApplicationDockableBarAdapter applicationDockableBarAdapter = new ApplicationDockableBarAdapter( this );

    public ApplicationDockableBarManager( ApplicationFrame applicationFrame, Container container )
    {
        super( applicationFrame, container );

        initialise( applicationFrame );
    }

    private void initialise( ApplicationFrame applicationFrame )
    {
        applicationCommandBarMenu = new ApplicationCommandBarMenu( applicationFrame );
    }

    public void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        dockableBar.addDockableBarListener( applicationDockableBarAdapter );
        super.addDockableBar( ( DockableBar ) dockableBar );
    }

    public void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        super.removeDockableBar( dockableBar.getKey() );
    }

    public ApplicationCommandBarMenu getApplicationCommandBarMenu()
    {
        return applicationCommandBarMenu;
    }

    private void added( ApplicationCommandBarComponent dockableBar )
    {
        applicationCommandBarMenu.addDockableBar( dockableBar );
    }

    private void removed( ApplicationCommandBarComponent dockableBar )
    {
        applicationCommandBarMenu.removeDockableBar( dockableBar );
        dockableBar.removeDockableBarListener( applicationDockableBarAdapter );
    }

    private class ApplicationDockableBarAdapter extends DockableBarAdapter
    {
        private final ApplicationDockableBarManager applicationDockableBarManager;

        public ApplicationDockableBarAdapter( ApplicationDockableBarManager applicationDockableBarManager )
        {
            this.applicationDockableBarManager = applicationDockableBarManager;
        }

        @Override
        public void dockableBarAdded( DockableBarEvent dockableBarEvent )
        {
            DockableBar dockableBar = dockableBarEvent.getDockableBar();

            if ( dockableBar instanceof ApplicationCommandBarComponent )
            {
                applicationDockableBarManager.added( ( ApplicationCommandBarComponent ) dockableBar );
            }
        }

        @Override
        public void dockableBarRemoved( DockableBarEvent dockableBarEvent )
        {
            DockableBar dockableBar = dockableBarEvent.getDockableBar();

            if ( dockableBar instanceof ApplicationCommandBarComponent )
            {
                applicationDockableBarManager.removed( ( ApplicationCommandBarComponent ) dockableBar );
            }
        }
    }
}

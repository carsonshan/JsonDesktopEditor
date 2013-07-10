package ge.framework.application.core.frame.command.menu;

import com.jidesoft.action.DockableBarManager;
import ge.framework.application.core.frame.command.ApplicationCommandBarComponent;
import ge.framework.application.core.frame.status.menu.item.StatusBarEnabledCheckboxMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:33
 */
public class ToggleApplicationCommandBarMenuItem extends StatusBarEnabledCheckboxMenuItem implements ActionListener
{
    private final ApplicationCommandBarComponent applicationCommandBarComponent;

    public ToggleApplicationCommandBarMenuItem( ApplicationCommandBarComponent applicationCommandBarComponent )
    {
        this.applicationCommandBarComponent = applicationCommandBarComponent;

        addActionListener( this );
    }

    public void update()
    {
        setText( applicationCommandBarComponent.getTitle() );
        setSelected( !applicationCommandBarComponent.isHidden() );

        DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

        if ( ( dockableBarManager.isHidable() == true ) && ( applicationCommandBarComponent.isHidable() == true ) )
        {
            setEnabled( true );
        }
        else
        {
            setEnabled( false );
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

        if ( applicationCommandBarComponent.isHidden() == true )
        {
            dockableBarManager.showDockableBar( applicationCommandBarComponent.getKey() );
        }
        else
        {
            dockableBarManager.hideDockableBar( applicationCommandBarComponent.getKey() );
        }
    }
}

package ge.framework.frame.core.command.menu;

import com.jidesoft.action.DockableBarManager;
import ge.framework.frame.core.command.ApplicationCommandBarComponent;
import ge.framework.frame.core.command.dialog.CommandBarDialog;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:48
 */
public class OtherApplicationCommandBarComponentMenuItem extends StatusBarEnabledSpacerMenuItem implements
                                                                                                ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private List<ApplicationCommandBarComponent> applicationCommandBarComponents;

    public OtherApplicationCommandBarComponentMenuItem()
    {
        super();

        setText( resources.getResourceString( OtherApplicationCommandBarComponentMenuItem.class, "label" ) );

        addActionListener( this );
    }

    public void setApplicationCommandBarComponents(
            List<ApplicationCommandBarComponent> applicationCommandBarComponents )
    {
        this.applicationCommandBarComponents = applicationCommandBarComponents;

        boolean enable = false;

        for ( ApplicationCommandBarComponent applicationCommandBarComponent : applicationCommandBarComponents )
        {
            DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

            if ( ( dockableBarManager.isHidable() == true ) &&
                    ( applicationCommandBarComponent.isHidable() == true ) )
            {
                enable = true;
                break;
            }
        }

        setEnabled( enable );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        CommandBarDialog commandBarDialog = new CommandBarDialog( applicationCommandBarComponents );

        if ( commandBarDialog.doModal() == true )
        {
            List<ApplicationCommandBarComponent> selectedValues = commandBarDialog.getSelectedValues();

            for ( ApplicationCommandBarComponent applicationCommandBarComponent : applicationCommandBarComponents )
            {
                DockableBarManager dockableBarManager = applicationCommandBarComponent.getDockableBarManager();

                if ( selectedValues.contains( applicationCommandBarComponent ) == true )
                {
                    dockableBarManager.showDockableBar( applicationCommandBarComponent.getKey() );
                }
                else
                {
                    dockableBarManager.hideDockableBar( applicationCommandBarComponent.getKey() );
                }
            }
        }
    }
}

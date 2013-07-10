package ge.framework.application.core.frame.command.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.application.core.frame.ApplicationFrame;
import ge.framework.application.core.frame.command.ApplicationCommandBarComponent;
import ge.framework.application.core.frame.status.menu.StatusBarEnabledSpacerMenu;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:52
 */
public class ApplicationCommandBarMenu extends StatusBarEnabledSpacerMenu implements JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private List<ApplicationCommandBarComponent> dockableBars = new ArrayList<ApplicationCommandBarComponent>();

    private OtherApplicationCommandBarComponentMenuItem otherApplicationCommandBarComponentMenuItem;

    public ApplicationCommandBarMenu( ApplicationFrame applicationFrame )
    {
        setText( resources.getResourceString( ApplicationCommandBarMenu.class, "title" ) );
        setPopupMenuCustomizer( this );

        otherApplicationCommandBarComponentMenuItem =
                new OtherApplicationCommandBarComponentMenuItem();
    }

    public void addDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        dockableBars.add( dockableBar );
        setEnabled( true );
    }

    public void removeDockableBar( ApplicationCommandBarComponent dockableBar )
    {
        dockableBars.remove( dockableBar );

        if ( dockableBars.isEmpty() )
        {
            setEnabled( false );
        }
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            if ( dockableBars.isEmpty() == false )
            {
                List<ApplicationCommandBarComponent> onMenu;

                if ( dockableBars.size() < 15 )
                {
                    onMenu = dockableBars;
                }
                else
                {
                    onMenu = dockableBars.subList( 0, 10 );
                }

                for ( ApplicationCommandBarComponent applicationCommandBarComponent : onMenu )
                {
                    ToggleApplicationCommandBarMenuItem toggleApplicationCommandBarMenuItem =
                            applicationCommandBarComponent.getToggleApplicationCommandBarMenuItem();

                    toggleApplicationCommandBarMenuItem.update();

                    add( toggleApplicationCommandBarMenuItem );
                }

                if ( dockableBars.size() != onMenu.size() )
                {
                    otherApplicationCommandBarComponentMenuItem.setApplicationCommandBarComponents( dockableBars );

                    addSeparator();
                    add( otherApplicationCommandBarComponentMenuItem );
                }
            }
        }
    }
}

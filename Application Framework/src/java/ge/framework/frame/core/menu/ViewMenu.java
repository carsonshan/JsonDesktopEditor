package ge.framework.frame.core.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.command.menu.ApplicationCommandBarMenu;
import ge.framework.frame.core.dockable.menu.ApplicationDockableFrameMenu;
import ge.framework.frame.core.manager.menu.ToggleToolButtonsMenuItem;
import ge.framework.frame.core.menu.view.AdditionalViewMenu;
import ge.framework.frame.core.menu.view.AdditionalViewMenuComponent;
import ge.framework.frame.core.menu.view.AdditionalViewMenuItem;
import ge.framework.frame.core.menu.view.AdditionalViewMenuSeparator;
import ge.framework.frame.core.status.menu.StatusBarEnabledMenu;
import ge.framework.frame.core.status.menu.item.ToggleStatusBarMenuItem;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 13:22
 */
public class ViewMenu extends StatusBarEnabledMenu implements JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private final ApplicationFrame applicationFrame;

    private ToggleStatusBarMenuItem toggleStatusBarMenuItem;

    private ToggleToolButtonsMenuItem toggleToolButtonsMenuItem;

    private List<AdditionalViewMenuComponent> additionalMenuItems = new ArrayList<AdditionalViewMenuComponent>();

    public ViewMenu( ApplicationFrame applicationFrame )
    {
        super();
        this.applicationFrame = applicationFrame;

        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setText( resources.getResourceString( ViewMenu.class, "label" ) );
        setMnemonic( resources.getResourceCharacter( ViewMenu.class, "mnemonic" ) );
        setStatusBarText( resources.getResourceString( ViewMenu.class, "status" ) );
        setPopupMenuCustomizer( this );

        toggleToolButtonsMenuItem = applicationFrame.getToggleToolButtonsMenuItem();
        toggleStatusBarMenuItem = applicationFrame.getToggleStatusBarMenuItem();
    }

    public boolean addAdditionalViewMenuComponent(
            AdditionalViewMenuComponent additionalViewMenuComponent )
    {
        return additionalMenuItems.add( additionalViewMenuComponent );
    }

    public boolean removeAdditionalViewMenuComponent( AdditionalViewMenuComponent additionalViewMenuComponent )
    {
        return additionalMenuItems.remove( additionalViewMenuComponent );
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        removeAll();

        ApplicationDockableFrameMenu frameWindowMenu = applicationFrame.getFrameWindowMenu();

        if ( ( frameWindowMenu != null ) && ( frameWindowMenu.isEmpty() == false ) )
        {
            add( frameWindowMenu );
            addSeparator();
        }

        if ( additionalMenuItems.isEmpty() == false )
        {
            boolean lastItemSeparator = true;

            for ( AdditionalViewMenuComponent additionalMenuComponent : additionalMenuItems )
            {
                if ( ( additionalMenuComponent instanceof AdditionalViewMenuSeparator ) &&
                        ( lastItemSeparator == false ) )
                {
                    AdditionalViewMenuSeparator additionalViewMenuSeparator =
                            ( AdditionalViewMenuSeparator ) additionalMenuComponent;

                    if ( additionalViewMenuSeparator.isVisible() == true )
                    {
                        addSeparator();
                        lastItemSeparator = true;
                    }
                }
                else if ( additionalMenuComponent instanceof AdditionalViewMenuItem )
                {
                    AdditionalViewMenuItem additionalViewMenuItem = ( AdditionalViewMenuItem ) additionalMenuComponent;
                    add( additionalViewMenuItem );
                    lastItemSeparator = false;
                }
                else if ( additionalMenuComponent instanceof AdditionalViewMenu )
                {
                    AdditionalViewMenu additionalViewMenu = ( AdditionalViewMenu ) additionalMenuComponent;
                    add( additionalViewMenu );
                    lastItemSeparator = false;
                }
            }

            if ( lastItemSeparator == false )
            {
                addSeparator();
            }
        }

        ApplicationCommandBarMenu applicationCommandBarMenu = applicationFrame.getApplicationCommandBarMenu();

        if ( ( applicationCommandBarMenu != null ) && ( applicationCommandBarMenu.isEmpty() == false ) )
        {
            add( applicationCommandBarMenu );
        }

        toggleToolButtonsMenuItem.update();
        add( toggleToolButtonsMenuItem );

        toggleStatusBarMenuItem.update();
        add( toggleStatusBarMenuItem );
    }
}

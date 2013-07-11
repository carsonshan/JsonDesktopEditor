package ge.framework.frame.core.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.file.AdditionalFileMenu;
import ge.framework.frame.core.menu.file.AdditionalFileMenuComponent;
import ge.framework.frame.core.menu.file.AdditionalFileMenuItem;
import ge.framework.frame.core.menu.file.AdditionalFileMenuSeparator;
import ge.framework.frame.core.menu.item.ExitFrameMenuItem;
import ge.framework.frame.core.status.menu.StatusBarEnabledMenu;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/02/13
 * Time: 17:44
 */
public abstract class FileMenu extends StatusBarEnabledMenu implements JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private final ApplicationFrame applicationFrame;

    private ExitFrameMenuItem exitMenuItem;

    private List<AdditionalFileMenuComponent> additionalMenuItems = new ArrayList<AdditionalFileMenuComponent>();

    public FileMenu( ApplicationFrame applicationFrame )
    {
        super();

        this.applicationFrame = applicationFrame;

        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setText( resources.getResourceString( FileMenu.class, "label" ) );
        setMnemonic( resources.getResourceCharacter( FileMenu.class, "mnemonic" ) );
        setStatusBarText( resources.getResourceString( FileMenu.class, "status" ) );
        setPopupMenuCustomizer( this );

        exitMenuItem = new ExitFrameMenuItem(applicationFrame.getApplication());

        initialiseOtherMenuItems();
    }

    protected abstract void initialiseOtherMenuItems();

    public boolean addAdditionalFileMenuComponent(
            AdditionalFileMenuComponent additionalFileMenuComponent )
    {
        return additionalMenuItems.add( additionalFileMenuComponent );
    }

    public boolean removeAdditionalFileMenuComponent( AdditionalFileMenuComponent additionalFileMenuComponent )
    {
        return additionalMenuItems.remove( additionalFileMenuComponent );
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( this.getPopupMenu() == jPopupMenu )
        {
            removeAll();

            customizeOtherMenuItems();

            if ( additionalMenuItems.isEmpty() == false )
            {
                boolean lastItemSeparator = true;

                for ( AdditionalFileMenuComponent additionalMenuComponent : additionalMenuItems )
                {
                    if ( ( additionalMenuComponent instanceof AdditionalFileMenuSeparator ) &&
                            ( lastItemSeparator == false ) )
                    {
                        AdditionalFileMenuSeparator additionalFileMenuSeparator =
                                ( AdditionalFileMenuSeparator ) additionalMenuComponent;

                        if ( additionalFileMenuSeparator.isVisible() == true )
                        {
                            addSeparator();
                            lastItemSeparator = true;
                        }
                    }
                    else if ( additionalMenuComponent instanceof AdditionalFileMenuItem )
                    {
                        AdditionalFileMenuItem additionalFileMenuItem =
                                ( AdditionalFileMenuItem ) additionalMenuComponent;

                        additionalFileMenuItem.update();

                        add( additionalFileMenuItem );
                        lastItemSeparator = false;
                    }
                    else if ( additionalMenuComponent instanceof AdditionalFileMenu )
                    {
                        AdditionalFileMenu additionalFileMenu = ( AdditionalFileMenu ) additionalMenuComponent;
                        add( additionalFileMenu );
                        lastItemSeparator = false;
                    }
                }

                if ( lastItemSeparator == false )
                {
                    addSeparator();
                }
            }

            add( exitMenuItem );
        }
    }

    protected abstract void customizeOtherMenuItems();
}

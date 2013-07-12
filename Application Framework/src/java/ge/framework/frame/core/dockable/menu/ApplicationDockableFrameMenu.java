package ge.framework.frame.core.dockable.menu;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.ApplicationFrameComponentMenu;
import ge.framework.frame.core.menu.item.OtherApplicationFrameComponentMenuItem;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:20
 */
public class ApplicationDockableFrameMenu extends ApplicationFrameComponentMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    public ApplicationDockableFrameMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );

        setText( resources.getResourceString( ApplicationDockableFrameMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( ApplicationDockableFrameMenu.class, "status" ) );
    }

    @Override
    protected OtherApplicationFrameComponentMenuItem createOtherMenuItem( ApplicationFrame applicationFrame )
    {
        return new OtherFrameWindowMenuItem( applicationFrame );
    }
}

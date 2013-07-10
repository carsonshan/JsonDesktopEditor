package ge.framework.application.core.frame.manager.menu;

import ge.framework.application.core.frame.ApplicationFrame;
import ge.framework.application.core.frame.menu.ApplicationFrameComponentMenu;
import ge.framework.application.core.frame.menu.item.OtherApplicationFrameComponentMenuItem;
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
            Resources.getInstance( "ge.framework.application.resources" );

    public ApplicationDockableFrameMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );

        setText( resources.getResourceString( ApplicationDockableFrameMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( ApplicationDockableFrameMenu.class, "status" ) );
    }

    @Override
    protected OtherApplicationFrameComponentMenuItem createOtherMenuItem()
    {
        return new OtherFrameWindowMenuItem();
    }
}

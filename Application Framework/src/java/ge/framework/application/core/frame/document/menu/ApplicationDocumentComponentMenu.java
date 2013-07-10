package ge.framework.application.core.frame.document.menu;

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
public class ApplicationDocumentComponentMenu extends ApplicationFrameComponentMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    public ApplicationDocumentComponentMenu( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );

        setText( resources.getResourceString( ApplicationDocumentComponentMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( ApplicationDocumentComponentMenu.class, "status" ) );
    }

    @Override
    protected OtherApplicationFrameComponentMenuItem createOtherMenuItem()
    {
        return new OtherApplicationDocumentComponentMenuItem();
    }
}

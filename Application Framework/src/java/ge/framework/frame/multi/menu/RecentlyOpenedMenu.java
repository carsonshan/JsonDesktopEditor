package ge.framework.frame.multi.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.StatusBarEnabledSpacerMenu;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.menu.item.ClearRecentFrameMenuItem;
import ge.framework.frame.multi.menu.item.OtherRecentFrameMenuItem;
import ge.framework.frame.multi.menu.item.RecentlyOpenedMenuItem;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/2013
 * Time: 00:00
 */
public class RecentlyOpenedMenu extends StatusBarEnabledSpacerMenu implements JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private OtherRecentFrameMenuItem otherRecentMenuItem = null;

    private ClearRecentFrameMenuItem clearRecentMenuItem = null;

    private MultiFrameApplication application;

    public RecentlyOpenedMenu( MultiFrameApplication application )
    {
        super();
        this.application = application;

        setText( resources.getResourceString( RecentlyOpenedMenu.class, "label" ) );
        setStatusBarText( resources.getResourceString( RecentlyOpenedMenu.class, "status" ) );

        otherRecentMenuItem = new OtherRecentFrameMenuItem(application);
        clearRecentMenuItem = new ClearRecentFrameMenuItem(application);

        setPopupMenuCustomizer( this );
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            ArrayList<FrameInstanceDetailsObject> frameInstanceDetailsObjects = application.getRecentlyOpened();

            if ( frameInstanceDetailsObjects.isEmpty() == false )
            {
                List<FrameInstanceDetailsObject> onMenu;

                if ( frameInstanceDetailsObjects.size() < 15 )
                {
                    onMenu = frameInstanceDetailsObjects;
                }
                else
                {
                    onMenu = frameInstanceDetailsObjects.subList( 0, 10 );
                }

                for ( FrameInstanceDetailsObject frameInstanceDetailsObject : onMenu )
                {
                    add( new RecentlyOpenedMenuItem( frameInstanceDetailsObject,application ) );
                }

                if ( frameInstanceDetailsObjects.size() != onMenu.size() )
                {
                    addSeparator();
                    add( otherRecentMenuItem );
                }
            }

            addSeparator();
            add( clearRecentMenuItem );
        }
    }
}

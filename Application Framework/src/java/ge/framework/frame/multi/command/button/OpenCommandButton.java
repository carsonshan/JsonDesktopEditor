package ge.framework.frame.multi.command.button;

import com.jidesoft.swing.JideMenu;
import com.jidesoft.swing.JideSplitButton;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.multi.menu.item.ClearRecentFrameMenuItem;
import ge.framework.frame.multi.menu.item.OpenFrameMenuItem;
import ge.framework.frame.multi.menu.item.OtherRecentFrameMenuItem;
import ge.framework.frame.multi.menu.item.RecentlyOpenedMenuItem;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 13:07
 */
public class OpenCommandButton extends JideSplitButton implements ActionListener, JideMenu.PopupMenuCustomizer
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private OtherRecentFrameMenuItem otherRecentMenuItem = null;

    private ClearRecentFrameMenuItem clearRecentMenuItem = null;

    private OpenFrameMenuItem openMenuItem = null;

    private MultiFrameApplication application;

    public OpenCommandButton( MultiFrameApplication application )
    {
        this.application = application;
        setPopupMenuCustomizer( this );

        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setIcon( resources.getResourceIcon( OpenCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processOpen();
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            ArrayList<FrameInstanceDetailsObject> frameInstanceDetailsObjects =
                    application.getRecentlyOpened();

            if ( frameInstanceDetailsObjects.size() > 15 )
            {
                for ( int i = 0; i < 10; i++ )
                {
                    add( new RecentlyOpenedMenuItem( frameInstanceDetailsObjects.get( i ),application ) );
                }

                addSeparator();

                if ( otherRecentMenuItem == null )
                {
                    otherRecentMenuItem = new OtherRecentFrameMenuItem(application);
                }

                add( otherRecentMenuItem );
            }
            else
            {
                for ( FrameInstanceDetailsObject frameInstanceDetailsObject : frameInstanceDetailsObjects )
                {
                    add( new RecentlyOpenedMenuItem( frameInstanceDetailsObject,application ) );
                }
            }

            if ( frameInstanceDetailsObjects.isEmpty() == false )
            {
                addSeparator();

                if ( clearRecentMenuItem == null )
                {
                    clearRecentMenuItem = new ClearRecentFrameMenuItem(application);
                }

                add( clearRecentMenuItem );
            }
            else
            {
                if ( openMenuItem == null )
                {
                    openMenuItem = new OpenFrameMenuItem(application);
                }

                add( openMenuItem );
            }
        }
    }
}

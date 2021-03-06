package ge.framework.frame.core.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.menu.item.ApplicationFrameComponentMenuItem;
import ge.framework.frame.core.menu.item.OtherApplicationFrameComponentMenuItem;
import ge.framework.frame.core.status.menu.StatusBarEnabledSpacerMenu;

import javax.swing.JPopupMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:20
 */
public abstract class ApplicationFrameComponentMenu extends StatusBarEnabledSpacerMenu implements
                                                                                       JideMenu.PopupMenuCustomizer
{
    private List<ApplicationFrameComponent> applicationFrameComponents = new ArrayList<ApplicationFrameComponent>();

    private OtherApplicationFrameComponentMenuItem otherApplicationFrameComponentMenuItem = null;

    public ApplicationFrameComponentMenu( ApplicationFrame applicationFrame )
    {
        setPopupMenuCustomizer( this );

        update();

        otherApplicationFrameComponentMenuItem = createOtherMenuItem( applicationFrame );
        otherApplicationFrameComponentMenuItem.setApplicationFrame( applicationFrame );
    }

    protected abstract OtherApplicationFrameComponentMenuItem createOtherMenuItem( ApplicationFrame applicationFrame );

    public void add( ApplicationFrameComponent applicationFrameComponent )
    {
        applicationFrameComponents.add( applicationFrameComponent );

        update();
    }

    public void remove( ApplicationFrameComponent applicationFrameComponent )
    {
        applicationFrameComponents.remove( applicationFrameComponent );

        update();
    }

    private void update()
    {
        if ( applicationFrameComponents.isEmpty() == true )
        {
            setEnabled( false );
        }
        else
        {
            setEnabled( true );
        }
    }

    public boolean isEmpty()
    {
        return applicationFrameComponents.isEmpty();
    }

    @Override
    public void customize( JPopupMenu jPopupMenu )
    {
        if ( jPopupMenu == getPopupMenu() )
        {
            removeAll();

            if ( applicationFrameComponents.isEmpty() == false )
            {
                List<ApplicationFrameComponent> onMenu;

                if ( applicationFrameComponents.size() < 15 )
                {
                    onMenu = applicationFrameComponents;
                }
                else
                {
                    onMenu = applicationFrameComponents.subList( 0, 10 );
                }

                for ( ApplicationFrameComponent applicationFrameComponent : onMenu )
                {
                    ApplicationFrameComponentMenuItem applicationFrameComponentMenuItem =
                            applicationFrameComponent.getMenuItem();

                    applicationFrameComponentMenuItem.update();

                    add( applicationFrameComponentMenuItem );
                }

                if ( applicationFrameComponents.size() != onMenu.size() )
                {
                    otherApplicationFrameComponentMenuItem.setApplicationFrameComponents(
                            applicationFrameComponents );

                    addSeparator();
                    add( otherApplicationFrameComponentMenuItem );
                }
            }
        }
    }
}

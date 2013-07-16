package ge.framework.frame.multi.menu;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.menu.item.ApplicationPropertiesFrameMenuItem;
import ge.framework.frame.multi.menu.item.CloseFrameMenuItem;
import ge.framework.frame.multi.menu.item.NewMenuItem;
import ge.framework.frame.multi.menu.item.OpenFrameMenuItem;
import ge.framework.frame.multi.objects.MultiFrameDefinition;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/07/13
 * Time: 11:05
 */
public class MultiFrameFileMenu extends FileMenu<MultiApplicationFrame>
{
    private NewMenuItem newMenuItem;

    private NewMenu newMenu;

    private OpenFrameMenuItem openMenuItem;

    private RecentlyOpenedMenu recentlyOpenedMenu;

    private CloseFrameMenuItem closeMenuItem;

    private ApplicationPropertiesFrameMenuItem applicationPropertiesMenuItem;

    public MultiFrameFileMenu( MultiApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected void initialiseOtherMenuItems()
    {
        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();

        List<MultiFrameDefinition> availableFrameConfigurationNames = application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() == 1 )
        {
            newMenuItem =
                    new NewMenuItem( application,availableFrameConfigurationNames.get( 0 ),
                                     true );
        }
        else if ( availableFrameConfigurationNames.size() >= 1 )
        {
            newMenu = new NewMenu( application );
        }

        openMenuItem = new OpenFrameMenuItem( application );
        recentlyOpenedMenu = new RecentlyOpenedMenu( application );
        closeMenuItem = new CloseFrameMenuItem(application);

        applicationPropertiesMenuItem = new ApplicationPropertiesFrameMenuItem(application);
    }

    @Override
    protected void customizeOtherMenuItems()
    {
        if ( newMenuItem != null )
        {
            add( newMenuItem );
        }
        else if ( newMenu != null )
        {
            add( newMenu );
        }

        add( openMenuItem );
        add( recentlyOpenedMenu );
        add( closeMenuItem );
        addSeparator();
        add( applicationPropertiesMenuItem );
    }
}

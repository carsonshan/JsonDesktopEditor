package ge.framework.frame.multi.menu;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.core.status.menu.StatusBarEnabledSpacerMenu;
import ge.framework.frame.multi.menu.item.NewMenuItem;
import ge.framework.frame.multi.menu.item.OtherNewMenuItem;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.bundle.Resources;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/02/2013
 * Time: 19:45
 */
public class NewMenu extends StatusBarEnabledSpacerMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public NewMenu(MultiFrameApplication application)
    {
        super();
        this.application = application;

        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setText( resources.getResourceString( NewMenu.class, "label" ) );
        setMnemonic( resources.getResourceCharacter( NewMenu.class, "mnemonic" ) );

        List<MultiFrameDefinition> availableFrameConfigurationNames = application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() > 15 )
        {
            for ( int i = 0; i < 10; i++ )
            {
                add( new NewMenuItem( application,availableFrameConfigurationNames.get( i ),
                                      false ) );
            }

            addSeparator();

            add( new OtherNewMenuItem(application) );
        }
        else
        {
            for ( MultiFrameDefinition typeName : availableFrameConfigurationNames )
            {
                add( new NewMenuItem( application,typeName, false ) );
            }
        }
    }

}

package ge.framework.frame.multi.command.button;

import com.jidesoft.swing.JideSplitButton;
import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.multi.menu.item.NewMenuItem;
import ge.framework.frame.multi.menu.item.OtherNewMenuItem;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 13:07
 */
public class NewCommandButton extends JideSplitButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public NewCommandButton( MultiFrameApplication application )
    {
        this.application = application;
        initialiseMenu();
    }

    private void initialiseMenu()
    {
        setIcon( resources.getResourceIcon( NewCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );

        List<MultiFrameDefinition> availableFrameConfigurationNames =
                application.getFrameDefinitions();

        if ( availableFrameConfigurationNames.size() > 15 )
        {
            for ( int i = 0; i < 10; i++ )
            {
                add( new NewMenuItem( application, availableFrameConfigurationNames.get( i ),
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

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processNew();
    }
}

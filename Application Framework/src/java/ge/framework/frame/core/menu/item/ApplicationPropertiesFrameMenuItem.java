package ge.framework.frame.core.menu.item;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 17:53
 */
public class ApplicationPropertiesFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private Application application;

    public ApplicationPropertiesFrameMenuItem( Application application )
    {
        this.application = application;
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );
        setIcon( resources.getResourceIcon( this.getClass(), "icon" ) );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processApplicationProperties();
    }
}

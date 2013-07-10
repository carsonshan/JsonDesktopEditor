package ge.framework.application.core.frame.status.menu.item;

import ge.framework.application.core.frame.status.ApplicationStatusBar;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 13:34
 */
public class ToggleStatusBarMenuItem extends StatusBarEnabledCheckboxMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private final ApplicationStatusBar applicationStatusBar;

    public ToggleStatusBarMenuItem( ApplicationStatusBar applicationStatusBar )
    {
        this.applicationStatusBar = applicationStatusBar;

        setText( resources.getResourceString( ToggleStatusBarMenuItem.class, "label" ) );

        addActionListener( this );
    }

    public void update()
    {
        setSelected( applicationStatusBar.isVisible() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        applicationStatusBar.setVisible( !applicationStatusBar.isVisible() );
    }
}

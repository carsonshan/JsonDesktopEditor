package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/01/13
 * Time: 11:23
 */
public class ClearRecentFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public ClearRecentFrameMenuItem(MultiFrameApplication application)
    {
        this.application = application;
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.clearRecent();
    }
}

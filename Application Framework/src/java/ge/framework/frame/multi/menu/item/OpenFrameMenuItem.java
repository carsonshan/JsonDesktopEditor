package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import javax.swing.KeyStroke;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/01/13
 * Time: 11:23
 */
public class OpenFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public OpenFrameMenuItem(MultiFrameApplication application)
    {
        this.application = application;
        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );
        setIcon( resources.getResourceIcon( this.getClass(), "icon" ) );

        addActionListener( this );

        setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_O, Event.CTRL_MASK ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processOpen();
    }
}

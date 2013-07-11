package ge.framework.frame.core.menu.item;

import ge.framework.application.core.Application;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.os.OS;

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
public class ExitFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private Application application;

    public ExitFrameMenuItem(Application application)
    {
        this.application = application;

        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );

        addActionListener( this );

        if ( OS.isMac() == true )
        {
            setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.META_MASK ) );
        }
        else
        {
            setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_F4,
                                                    Event.ALT_MASK ) );
        }
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processExit();
    }
}

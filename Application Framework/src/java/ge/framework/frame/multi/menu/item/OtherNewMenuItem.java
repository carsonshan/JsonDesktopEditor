package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 14/02/13
 * Time: 18:05
 */
public class OtherNewMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public OtherNewMenuItem(MultiFrameApplication application)
    {
        this.application = application;
        addActionListener( this );

        setText( resources.getResourceString( OtherNewMenuItem.class, "label" ) );
        setStatusBarText( resources.getResourceString( OtherNewMenuItem.class, "status" ) );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processNew();
    }
}

package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.framework.frame.multi.dialog.RecentDialog;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 11:04
 */
public class OtherRecentFrameMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public OtherRecentFrameMenuItem( MultiFrameApplication application )
    {
        super();
        this.application = application;

        setText( resources.getResourceString( this.getClass(), "label" ) );
        setStatusBarText( resources.getResourceString( this.getClass(), "status" ) );
        setMnemonic( resources.getResourceCharacter( this.getClass(), "mnemonic" ) );

        addActionListener( this );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        RecentDialog recentDialog = new RecentDialog(application);

        if ( recentDialog.doModal() == true )
        {
            FrameInstanceDetailsObject frameInstanceDetailsObject = recentDialog.getFrameInstanceDetailsObject();

            application.openFrame( frameInstanceDetailsObject, false );
        }
    }
}

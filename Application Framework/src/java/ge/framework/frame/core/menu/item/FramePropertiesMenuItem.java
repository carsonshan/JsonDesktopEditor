package ge.framework.frame.core.menu.item;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dialog.FramePropertiesDialog;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/2013
 * Time: 21:11
 */
public class FramePropertiesMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private final ApplicationFrame applicationFrame;

    public FramePropertiesMenuItem( ApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;
        addActionListener( this );

        String label = resources.getResourceString( FramePropertiesMenuItem.class, "label" );

        FrameDefinition frameDefinition = applicationFrame.getFrameDefinition();

        Map<String, Object> arguments = new HashMap();
        arguments.put( "frameDefinition", frameDefinition.getName() );

        label = StringArgumentMessageFormat.format( label, arguments );

        setText( label );
        setIcon( resources.getResourceIcon( FramePropertiesMenuItem.class, "icon" ) );
        setMnemonic( resources.getResourceCharacter( FramePropertiesMenuItem.class, "mnemonic" ) );
        setStatusBarText( resources.getResourceString( FramePropertiesMenuItem.class, "status" ) );
    }

    @Override
    public void actionPerformed( ActionEvent actionEvent )
    {
        FramePropertiesDialog framePropertiesDialog = new FramePropertiesDialog( applicationFrame );

        framePropertiesDialog.doModal();

        applicationFrame.saveFrameConfiguration();
    }
}

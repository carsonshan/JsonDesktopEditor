package ge.framework.application.multi.dialog;

import ge.framework.application.multi.MultiFrameApplication;
import ge.utils.bundle.Resources;
import ge.utils.message.MessageDialog;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageType;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 13:29
 */
public class LockedLocationDialog extends MessageDialog
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    public LockedLocationDialog( String name, MultiFrameApplication application ) throws HeadlessException
    {
        super( application.discoverFocusedFrame(),
               formatMessage( name ),
               resources.getResourceString( LockedLocationDialog.class, "title" ),
               MessageType.OK,
               MessageLevel.ERROR );
    }

    private static String formatMessage( String name )
    {
        String message = resources.getResourceString( LockedLocationDialog.class, "message" );

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "name", name );

        return StringArgumentMessageFormat.format( message, arguments );
    }
}

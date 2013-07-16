package ge.framework.application.core.dialog;

import ge.framework.frame.core.ApplicationFrame;
import ge.utils.bundle.Resources;
import ge.utils.message.MessageDialog;
import ge.utils.message.enums.MessageLevel;
import ge.utils.message.enums.MessageResult;
import ge.utils.message.enums.MessageType;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 13:42
 */
public class ExitDialog extends MessageDialog
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    private static String alternateCheckBoxText = resources.getResourceString( ExitDialog.class, "alternate",
                                                                               "checkbox" );

    public ExitDialog( ApplicationFrame applicationFrame ) throws HeadlessException
    {
        super( applicationFrame,
               resources.getResourceString( ExitDialog.class, "message" ),
               resources.getResourceString( ExitDialog.class, "title" ),
               MessageType.OK_CANCEL,
               MessageLevel.QUESTION,
               true, alternateCheckBoxText, getAlternateButtonText() );
    }

    private static Map<MessageResult, String> getAlternateButtonText()
    {
        Map<MessageResult, String> alternateButtonText = new HashMap<MessageResult, String>();

        alternateButtonText.put( MessageResult.OK, resources.getResourceString( ExitDialog.class, "alternate", "ok" ) );

        return alternateButtonText;
    }
}

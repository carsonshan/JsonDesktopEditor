package ge.framework.application.multi.dialog.buttons;


import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.InitialDialog;

import java.awt.event.ActionEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/01/13
 * Time: 10:42
 */
public class NewFrameButton extends InitialDialogButton
{
    private InitialDialog initialDialog;

    public NewFrameButton( InitialDialog initialDialog )
    {
        super();
        this.initialDialog = initialDialog;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        MultiFrameApplication application = initialDialog.getApplication();
        if ( application.processNew( initialDialog ) == true )
        {
            initialDialog.dispose();
        }
    }
}

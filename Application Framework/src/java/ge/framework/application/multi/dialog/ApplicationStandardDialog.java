package ge.framework.application.multi.dialog;

import com.jidesoft.dialog.StandardDialog;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.utils.RootPaneDisposeActionListener;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.Dialog;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 07/03/13
 * Time: 18:09
 */
public abstract class ApplicationStandardDialog extends StandardDialog
{
    protected MultiFrameApplication application;

    protected ApplicationStandardDialog( MultiFrameApplication application ) throws HeadlessException
    {
        super( application.discoverFocusedFrame() );
        this.application = application;

        initialiseApplicationStandardDialog();
    }

    protected ApplicationStandardDialog( Dialog dialog, MultiFrameApplication application ) throws HeadlessException
    {
        super( dialog, false );
        this.application = application;

        initialiseApplicationStandardDialog();
    }

    private void initialiseApplicationStandardDialog()
    {
        setIconImage( application.getSmallImage() );
        setTitle( application.getName() );

        rootPane.registerKeyboardAction( new RootPaneDisposeActionListener(),
                                         KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ),
                                         JComponent.WHEN_IN_FOCUSED_WINDOW );

        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
    }

    public MultiFrameApplication getApplication()
    {
        return application;
    }
}

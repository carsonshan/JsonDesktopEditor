package ge.framework.frame.core.document.dialog;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.dialog.ApplicationFrameComponentDialog;
import ge.framework.frame.core.dialog.panel.ApplicationFrameComponentPanel;
import ge.framework.frame.core.document.dialog.panel.DocumentsPanel;
import ge.utils.bundle.Resources;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:13
 */
public class DocumentsDialog extends ApplicationFrameComponentDialog
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.frame.resources" );

    public DocumentsDialog( ApplicationFrame applicationFrame,
                            List<ApplicationFrameComponent> applicationFrameComponents )
    {
        super(applicationFrame, applicationFrameComponents );

        setTitle( resources.getResourceString( DocumentsDialog.class, "title" ) );
    }

    @Override
    protected ApplicationFrameComponentPanel createApplicationFrameComponentPanel()
    {
        return new DocumentsPanel();
    }
}

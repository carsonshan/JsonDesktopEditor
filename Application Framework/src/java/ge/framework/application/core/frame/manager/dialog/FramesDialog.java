package ge.framework.application.core.frame.manager.dialog;

import ge.framework.application.core.frame.ApplicationFrameComponent;
import ge.framework.application.core.frame.dialog.ApplicationFrameComponentDialog;
import ge.framework.application.core.frame.dialog.panel.ApplicationFrameComponentPanel;
import ge.framework.application.core.frame.manager.dialog.panel.FramesPanel;
import ge.utils.bundle.Resources;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:13
 */
public class FramesDialog extends ApplicationFrameComponentDialog
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.resources" );

    public FramesDialog( List<ApplicationFrameComponent> applicationFrameComponents )
    {
        super( applicationFrameComponents );

        setTitle( resources.getResourceString( FramesDialog.class, "title" ) );
    }

    @Override
    protected ApplicationFrameComponentPanel createApplicationFrameComponentPanel()
    {
        return new FramesPanel();
    }
}

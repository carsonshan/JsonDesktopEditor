package ge.framework.application.core.frame.manager.dialog.panel;

import ge.framework.application.core.frame.dialog.panel.ApplicationFrameComponentPanel;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:06
 */
public class FramesPanel extends ApplicationFrameComponentPanel
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.resources" );

    public FramesPanel()
    {
        super();
    }

    @Override
    protected String getTitleLabel()
    {
        return resources.getResourceString( FramesPanel.class, "label" );
    }
}

package ge.framework.application.core.frame.document.dialog.panel;

import ge.framework.application.core.frame.dialog.panel.ApplicationFrameComponentPanel;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:06
 */
public class DocumentsPanel extends ApplicationFrameComponentPanel
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.resources" );

    public DocumentsPanel()
    {
        super();
    }

    @Override
    protected String getTitleLabel()
    {
        return resources.getResourceString( DocumentsPanel.class, "label" );
    }
}

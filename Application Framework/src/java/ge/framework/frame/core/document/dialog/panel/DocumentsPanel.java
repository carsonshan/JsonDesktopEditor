package ge.framework.frame.core.document.dialog.panel;

import ge.framework.frame.core.dialog.panel.ApplicationFrameComponentPanel;
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
            "ge.framework.frame.resources" );

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

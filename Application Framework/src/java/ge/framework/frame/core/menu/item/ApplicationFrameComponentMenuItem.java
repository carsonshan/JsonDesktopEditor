package ge.framework.frame.core.menu.item;


import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 11:04
 */
public abstract class ApplicationFrameComponentMenuItem extends StatusBarEnabledSpacerMenuItem
{
    private final ApplicationFrameComponent applicationFrameComponent;

    public ApplicationFrameComponentMenuItem( ApplicationFrameComponent applicationFrameComponent )
    {
        this.applicationFrameComponent = applicationFrameComponent;
    }

    public void update()
    {
        setText( applicationFrameComponent.getMenuTitle() );
        setIcon( applicationFrameComponent.getMenuIcon() );
    }

    public ApplicationFrameComponent getApplicationFrameComponent()
    {
        return applicationFrameComponent;
    }
}

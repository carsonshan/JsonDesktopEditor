package ge.framework.frame.core.menu.item;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.utils.bundle.Resources;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:48
 */
public abstract class OtherApplicationFrameComponentMenuItem extends StatusBarEnabledSpacerMenuItem implements
                                                                                                    ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private ApplicationFrame applicationFrame;

    private List<ApplicationFrameComponent> applicationFrameComponents;

    public OtherApplicationFrameComponentMenuItem()
    {
        super();

        setText( resources.getResourceString( OtherApplicationFrameComponentMenuItem.class, "label" ) );

        addActionListener( this );
    }

    public void setApplicationFrameComponents(
            List<ApplicationFrameComponent> applicationFrameComponents )
    {
        this.applicationFrameComponents = applicationFrameComponents;
    }

    public ApplicationFrame getApplicationFrame()
    {
        return applicationFrame;
    }

    public List<ApplicationFrameComponent> getApplicationFrameComponents()
    {
        return applicationFrameComponents;
    }

    public void setApplicationFrame( ApplicationFrame applicationFrame )
    {
        this.applicationFrame = applicationFrame;
    }
}

package ge.framework.application.core.frame.menu.item;

import ge.framework.application.core.frame.ApplicationFrame;
import ge.framework.application.core.frame.ApplicationFrameComponent;
import ge.framework.application.core.frame.status.menu.item.StatusBarEnabledSpacerMenuItem;
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
            Resources.getInstance( "ge.framework.application.resources" );

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

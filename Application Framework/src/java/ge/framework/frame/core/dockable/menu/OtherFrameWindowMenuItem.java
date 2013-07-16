package ge.framework.frame.core.dockable.menu;

import com.jidesoft.docking.DockingManager;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.dockable.dialog.FramesDialog;
import ge.framework.frame.core.menu.item.OtherApplicationFrameComponentMenuItem;

import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:48
 */
public class OtherFrameWindowMenuItem extends OtherApplicationFrameComponentMenuItem
{
    private ApplicationFrame applicationFrame;

    public OtherFrameWindowMenuItem( ApplicationFrame applicationFrame )
    {
        super();
        this.applicationFrame = applicationFrame;
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        List<ApplicationFrameComponent> applicationFrameComponents =
                getApplicationFrameComponents();

        FramesDialog framesDialog =
                new FramesDialog( applicationFrame, applicationFrameComponents );

        if ( framesDialog.doModal() == true )
        {
            ApplicationDockableFrame frame =
                    ( ApplicationDockableFrame ) framesDialog.getApplicationFrameComponent();

            if ( frame.isAutohide() == true )
            {
                DockingManager dockingManager = frame.getDockingManager();

                dockingManager.toggleAutohideState( frame.getKey() );
            }

            frame.showFrame();
        }
    }
}

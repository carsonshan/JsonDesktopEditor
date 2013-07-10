package ge.framework.application.core.frame.manager.menu;

import com.jidesoft.docking.DockingManager;
import ge.framework.application.core.frame.ApplicationFrameComponent;
import ge.framework.application.core.frame.manager.ApplicationDockableFrame;
import ge.framework.application.core.frame.manager.dialog.FramesDialog;
import ge.framework.application.core.frame.menu.item.OtherApplicationFrameComponentMenuItem;

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
    public OtherFrameWindowMenuItem()
    {
        super();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        List<ApplicationFrameComponent> applicationFrameComponents =
                getApplicationFrameComponents();

        FramesDialog framesDialog =
                new FramesDialog( applicationFrameComponents );

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

package ge.framework.application.core.frame.manager.menu;

import ge.framework.application.core.frame.manager.ApplicationDockableFrame;
import ge.framework.application.core.frame.menu.item.ApplicationFrameComponentMenuItem;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:50
 */
public class ApplicationDockableFrameMenuItem extends ApplicationFrameComponentMenuItem implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.application.resources" );

    public ApplicationDockableFrameMenuItem( ApplicationDockableFrame applicationDockableFrame )
    {
        super( applicationDockableFrame );

        addActionListener( this );
    }

    @Override
    public void update()
    {
        super.update();

        String status = resources.getResourceString( ApplicationDockableFrameMenuItem.class, "status" );

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "toolName", getApplicationFrameComponent().getMenuTitle() );

        status = StringArgumentMessageFormat.format( status, arguments );

        setStatusBarText( status );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        ApplicationDockableFrame applicationDockableFrame =
                ( ApplicationDockableFrame ) getApplicationFrameComponent();

        applicationDockableFrame.showFrame();
    }
}

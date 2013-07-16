package ge.framework.frame.multi.command;

import com.jidesoft.action.DockableBarContext;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.command.ApplicationCommandBar;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.command.button.ApplicationPropertiesCommandButton;
import ge.framework.frame.multi.command.button.FramePropertiesCommandButton;
import ge.framework.frame.multi.command.button.NewCommandButton;
import ge.framework.frame.multi.command.button.OpenCommandButton;
import ge.utils.bundle.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 11:55
 */
public class FileCommandBar extends ApplicationCommandBar
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private NewCommandButton newCommandButton;

    private OpenCommandButton openCommandButton;

    private ApplicationPropertiesCommandButton applicationPropertiesCommandButton;

    private FramePropertiesCommandButton framePropertiesCommandButton;

    public FileCommandBar( MultiApplicationFrame applicationFrame )
    {
        super( "fileCommandBar" );

        MultiFrameApplication application = ( MultiFrameApplication ) applicationFrame.getApplication();
        newCommandButton = new NewCommandButton( application );
        openCommandButton = new OpenCommandButton( application );
        applicationPropertiesCommandButton = new ApplicationPropertiesCommandButton( application );
        framePropertiesCommandButton = new FramePropertiesCommandButton( applicationFrame );

        setTitle( resources.getResourceString( FileCommandBar.class, "label" ) );
        setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        setFloatable( true );
        setHidable( true );
        setInitIndex( 1 );
        setChevronAlwaysVisible( false );
        setStretch( false );

        add( newCommandButton );
        add( openCommandButton );
        addSeparator();
        add( applicationPropertiesCommandButton );
        add( framePropertiesCommandButton );
    }
}

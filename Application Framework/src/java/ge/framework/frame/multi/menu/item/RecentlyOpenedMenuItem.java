package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.utils.bundle.Resources;
import ge.utils.text.StringArgumentMessageFormat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 14/02/13
 * Time: 19:09
 */
public class RecentlyOpenedMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private final FrameInstanceDetailsObject frameInstanceDetailsObject;

    private MultiFrameApplication application;

    public RecentlyOpenedMenuItem( FrameInstanceDetailsObject frameInstanceDetailsObject,
                                   MultiFrameApplication application )
    {
        super();

        this.frameInstanceDetailsObject = frameInstanceDetailsObject;
        this.application = application;

        initialise();
    }

    private void initialise()
    {
        addActionListener( this );

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "instanceName", frameInstanceDetailsObject.getName() );

        String label;

        if ( frameInstanceDetailsObject.doesConfigurationFileExist() == false )
        {
            label = resources.getResourceString( getClass(), "missingLabel" );
        }
        else if ( frameInstanceDetailsObject.isConfigurationFileLocked() == true )
        {
            label = resources.getResourceString( getClass(), "lockedLabel" );
            setEnabled( false );
        }
        else
        {
            label = resources.getResourceString( getClass(), "label" );
        }

        String statusLabel = resources.getResourceString( getClass(), "status" );

        label = StringArgumentMessageFormat.format( label, arguments );
        statusLabel = StringArgumentMessageFormat.format( statusLabel, arguments );

        setText( label );
        setStatusBarText( statusLabel );

        FrameDefinition frameDefinition = frameInstanceDetailsObject.getFrameDefinition();

        setIcon( frameDefinition.getSmallIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.openFrame( frameInstanceDetailsObject, true );
    }
}

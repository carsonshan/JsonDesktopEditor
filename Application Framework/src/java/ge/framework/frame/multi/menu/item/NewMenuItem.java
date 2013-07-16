package ge.framework.frame.multi.menu.item;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
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
 * Time: 18:05
 */
public class NewMenuItem extends StatusBarEnabledSpacerMenuItem implements ActionListener
{
    protected static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    private final MultiFrameDefinition frameDefinition;

    public NewMenuItem( MultiFrameApplication application, MultiFrameDefinition frameDefinition, boolean topLevel )
    {
        super();
        this.application = application;
        this.frameDefinition = frameDefinition;

        addActionListener( this );

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "frameDefinition", frameDefinition.getName() );

        String label;

        if ( topLevel == true )
        {
            label = resources.getResourceString( getClass(), "menuLabel" );
        }
        else
        {
            label = resources.getResourceString( getClass(), "subMenuLabel" );
        }

        label = StringArgumentMessageFormat.format( label, arguments );

        String statusLabel = resources.getResourceString( getClass(), "status" );
        statusLabel = StringArgumentMessageFormat.format( statusLabel, arguments );

        setText( label );
        setStatusBarText( statusLabel );
        setIcon( frameDefinition.getSmallIcon() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processNew( frameDefinition );
    }
}

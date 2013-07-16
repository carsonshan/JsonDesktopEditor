package ge.framework.frame.multi.command.button;

import com.jidesoft.swing.JideButton;
import ge.framework.application.multi.MultiFrameApplication;
import ge.utils.bundle.Resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 25/02/13
 * Time: 14:36
 */
public class ApplicationPropertiesCommandButton extends JideButton implements ActionListener
{
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    private MultiFrameApplication application;

    public ApplicationPropertiesCommandButton( MultiFrameApplication application )
    {
        this.application = application;
        addActionListener( this );

        setIcon( resources.getResourceIcon( ApplicationPropertiesCommandButton.class, "icon" ) );

        setFocusable( false );
        setOpaque( false );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        application.processApplicationProperties();
    }
}

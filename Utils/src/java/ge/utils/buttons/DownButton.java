package ge.utils.buttons;

import ge.utils.bundle.Resources;

import javax.swing.JButton;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 20/06/13
 * Time: 10:48
 */
public class DownButton extends JButton
{
    private static final Resources resources = Resources.getInstance( "ge.utils.resources" );

    public DownButton()
    {
        setIcon( resources.getResourceIcon( DownButton.class, "icon" ) );
        setToolTipText( resources.getResourceString( DownButton.class, "tooltip" ) );
    }
}

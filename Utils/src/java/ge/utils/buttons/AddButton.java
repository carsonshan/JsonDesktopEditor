package ge.utils.buttons;

import ge.utils.bundle.Resources;

import javax.swing.JButton;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 20/06/13
 * Time: 10:48
 */
public class AddButton extends JButton
{
    private static final Resources resources = Resources.getInstance( "ge.utils.resources" );

    public AddButton()
    {
        setIcon( resources.getResourceIcon( AddButton.class, "icon" ) );
        setToolTipText( resources.getResourceString( AddButton.class, "tooltip" ) );
    }
}

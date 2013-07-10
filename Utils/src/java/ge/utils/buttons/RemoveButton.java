package ge.utils.buttons;

import ge.utils.bundle.Resources;

import javax.swing.JButton;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 20/06/13
 * Time: 10:48
 */
public class RemoveButton extends JButton
{
    private static final Resources resources = Resources.getInstance( "ge.utils.resources" );

    public RemoveButton()
    {
        setIcon( resources.getResourceIcon( RemoveButton.class, "icon" ) );
        setToolTipText( resources.getResourceString( RemoveButton.class, "tooltip" ) );
    }
}

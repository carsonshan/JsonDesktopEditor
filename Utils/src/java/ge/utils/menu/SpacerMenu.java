package ge.utils.menu;

import com.jidesoft.swing.JideMenu;
import ge.utils.bundle.Resources;
import ge.utils.os.OS;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class SpacerMenu extends JideMenu
{
    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    public SpacerMenu()
    {
        initialiseMenuItem();
    }

    public SpacerMenu( Action a )
    {
        super( a );
        initialiseMenuItem();
    }

    public SpacerMenu( String text )
    {
        super( text );
        initialiseMenuItem();
    }

    private void initialiseMenuItem()
    {
        Icon icon = getIcon();

        if ( ( icon == null ) && ( OS.isMac() == false ) )
        {
            super.setIcon( resources.getResourceIcon( SpacerMenu.class, "blank" ) );
        }
    }

    @Override
    public void setIcon( Icon icon )
    {
        if ( ( icon == null ) && ( OS.isMac() == false ) )
        {
            super.setIcon( resources.getResourceIcon( SpacerMenu.class, "blank" ) );
        }
        else
        {
            super.setIcon( icon );
        }
    }
}

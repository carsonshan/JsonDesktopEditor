package ge.utils.menu;

import ge.utils.bundle.Resources;
import ge.utils.os.OS;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class SpacerMenuItem extends JMenuItem
{
    private static final Resources resources =
            Resources.getInstance( "ge.utils.resources" );

    public SpacerMenuItem()
    {
        initialiseMenuItem();
    }

    public SpacerMenuItem( Action a )
    {
        super( a );
        initialiseMenuItem();
    }

    public SpacerMenuItem( Icon icon )
    {
        super( icon );
        initialiseMenuItem();
    }

    public SpacerMenuItem( String text )
    {
        super( text );
        initialiseMenuItem();
    }

    public SpacerMenuItem( String text, Icon icon )
    {
        super( text, icon );
        initialiseMenuItem();
    }

    public SpacerMenuItem( String text, int mnemonic )
    {
        super( text, mnemonic );
        initialiseMenuItem();
    }

    private void initialiseMenuItem()
    {
        Icon icon = getIcon();

        if ( ( icon == null ) && ( OS.isMac() == false ) )
        {
            super.setIcon( resources.getResourceIcon( SpacerMenuItem.class, "blank" ) );
        }
    }

    @Override
    public void setIcon( Icon icon )
    {
        if ( ( icon == null ) && ( OS.isMac() == false ) )
        {
            super.setIcon( resources.getResourceIcon( SpacerMenuItem.class, "blank" ) );
        }
        else
        {
            super.setIcon( icon );
        }
    }
}

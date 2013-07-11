package ge.framework.frame.core.command;

import com.jidesoft.action.CommandMenuBar;
import ge.framework.frame.core.command.menu.ToggleApplicationCommandBarMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:30
 */
public class ApplicationCommandMenuBar extends CommandMenuBar implements ApplicationCommandBarComponent
{
    private ToggleApplicationCommandBarMenuItem toggleApplicationCommandBarMenuItem;

    public ApplicationCommandMenuBar()
    {
        super();

        initialise();
    }

    public ApplicationCommandMenuBar( int i )
    {
        super( i );

        initialise();
    }

    public ApplicationCommandMenuBar( String s )
    {
        super( s );

        initialise();
    }

    public ApplicationCommandMenuBar( String s, String s1 )
    {
        super( s, s1 );

        initialise();
    }

    public ApplicationCommandMenuBar( String s, String s1, int i )
    {
        super( s, s1, i );

        initialise();
    }

    private void initialise()
    {
        toggleApplicationCommandBarMenuItem = new ToggleApplicationCommandBarMenuItem( this );
    }

    public ToggleApplicationCommandBarMenuItem getToggleApplicationCommandBarMenuItem()
    {
        return toggleApplicationCommandBarMenuItem;
    }
}

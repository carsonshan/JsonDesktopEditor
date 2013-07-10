package ge.framework.application.core.frame.command;

import com.jidesoft.action.CommandBar;
import ge.framework.application.core.frame.command.menu.ToggleApplicationCommandBarMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:30
 */
public class ApplicationCommandBar extends CommandBar implements ApplicationCommandBarComponent
{
    private ToggleApplicationCommandBarMenuItem toggleApplicationCommandBarMenuItem;

    public ApplicationCommandBar()
    {
        super();

        initialise();
    }

    public ApplicationCommandBar( int orientation )
    {
        super( orientation );

        initialise();
    }

    public ApplicationCommandBar( String key )
    {
        super( key );

        initialise();
    }

    public ApplicationCommandBar( String key, String title )
    {
        super( key, title );

        initialise();
    }

    public ApplicationCommandBar( String key, String title, int orientation )
    {
        super( key, title, orientation );

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

package ge.framework.frame.core.command;

import com.jidesoft.action.DockableBarManager;
import com.jidesoft.action.event.DockableBarListener;
import ge.framework.frame.core.command.menu.ToggleApplicationCommandBarMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 15:47
 */
public interface ApplicationCommandBarComponent
{
    public ToggleApplicationCommandBarMenuItem getToggleApplicationCommandBarMenuItem();

    public boolean isHidden();

    public boolean isHidable();

    public String getTitle();

    public String getKey();

    public DockableBarManager getDockableBarManager();

    public void addDockableBarListener( DockableBarListener dockableBarListener );

    public void removeDockableBarListener( DockableBarListener dockableBarListener );
}

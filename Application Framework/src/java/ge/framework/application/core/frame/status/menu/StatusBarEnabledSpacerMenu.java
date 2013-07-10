package ge.framework.application.core.frame.status.menu;

import ge.framework.application.core.frame.status.StatusBarEnabled;
import ge.utils.menu.SpacerMenu;

import javax.swing.Action;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 18:54
 */
public class StatusBarEnabledSpacerMenu extends SpacerMenu implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledSpacerMenu()
    {
    }

    public StatusBarEnabledSpacerMenu( Action a )
    {
        super( a );
    }

    public StatusBarEnabledSpacerMenu( String text )
    {
        super( text );
    }

    public String getStatusBarText()
    {
        return statusBarText;
    }

    public void setStatusBarText( String statusBarText )
    {
        this.statusBarText = statusBarText;
    }
}

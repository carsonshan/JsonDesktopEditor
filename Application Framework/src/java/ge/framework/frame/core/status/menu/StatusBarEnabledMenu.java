package ge.framework.frame.core.status.menu;

import com.jidesoft.swing.JideMenu;
import ge.framework.frame.core.status.StatusBarEnabled;

import javax.swing.Action;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class StatusBarEnabledMenu extends JideMenu implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledMenu()
    {
    }

    public StatusBarEnabledMenu( Action a )
    {
        super( a );
    }

    public StatusBarEnabledMenu( String text )
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

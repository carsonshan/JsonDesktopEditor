package ge.framework.application.core.frame.status.menu.item;


import ge.framework.application.core.frame.status.StatusBarEnabled;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class StatusBarEnabledCheckboxMenuItem extends JCheckBoxMenuItem implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledCheckboxMenuItem()
    {
    }

    public StatusBarEnabledCheckboxMenuItem( Action a )
    {
        super( a );
    }

    public StatusBarEnabledCheckboxMenuItem( Icon icon )
    {
        super( icon );
    }

    public StatusBarEnabledCheckboxMenuItem( String text )
    {
        super( text );
    }

    public StatusBarEnabledCheckboxMenuItem( String text, boolean b )
    {
        super( text, b );
    }

    public StatusBarEnabledCheckboxMenuItem( String text, Icon icon )
    {
        super( text, icon );
    }

    public StatusBarEnabledCheckboxMenuItem( String text, Icon icon, boolean b )
    {
        super( text, icon, b );
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

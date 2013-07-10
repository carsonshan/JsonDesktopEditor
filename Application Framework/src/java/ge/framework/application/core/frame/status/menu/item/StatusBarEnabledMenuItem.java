package ge.framework.application.core.frame.status.menu.item;


import ge.framework.application.core.frame.status.StatusBarEnabled;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/06/2012
 * Time: 07:34
 */
public class StatusBarEnabledMenuItem extends JMenuItem implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledMenuItem()
    {
    }

    public StatusBarEnabledMenuItem( Action a )
    {
        super( a );
    }

    public StatusBarEnabledMenuItem( Icon icon )
    {
        super( icon );
    }

    public StatusBarEnabledMenuItem( String text )
    {
        super( text );
    }

    public StatusBarEnabledMenuItem( String text, Icon icon )
    {
        super( text, icon );
    }

    public StatusBarEnabledMenuItem( String text, int mnemonic )
    {
        super( text, mnemonic );
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

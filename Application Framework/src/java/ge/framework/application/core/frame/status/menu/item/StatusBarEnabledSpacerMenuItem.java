package ge.framework.application.core.frame.status.menu.item;

import ge.framework.application.core.frame.status.StatusBarEnabled;
import ge.utils.menu.SpacerMenuItem;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 18:54
 */
public class StatusBarEnabledSpacerMenuItem extends SpacerMenuItem implements StatusBarEnabled
{
    private String statusBarText;

    public StatusBarEnabledSpacerMenuItem()
    {
    }

    public StatusBarEnabledSpacerMenuItem( Action a )
    {
        super( a );
    }

    public StatusBarEnabledSpacerMenuItem( Icon icon )
    {
        super( icon );
    }

    public StatusBarEnabledSpacerMenuItem( String text )
    {
        super( text );
    }

    public StatusBarEnabledSpacerMenuItem( String text, Icon icon )
    {
        super( text, icon );
    }

    public StatusBarEnabledSpacerMenuItem( String text, int mnemonic )
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

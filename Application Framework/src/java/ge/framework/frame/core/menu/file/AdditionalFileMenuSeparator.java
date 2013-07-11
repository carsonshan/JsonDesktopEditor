package ge.framework.frame.core.menu.file;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 11:37
 */
public final class AdditionalFileMenuSeparator implements AdditionalFileMenuComponent
{
    private boolean visible;

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible( boolean visible )
    {
        this.visible = visible;
    }
}

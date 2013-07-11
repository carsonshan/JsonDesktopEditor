package ge.framework.frame.core.menu.file;


import ge.framework.frame.core.status.menu.item.StatusBarEnabledSpacerMenuItem;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 11:38
 */
public abstract class AdditionalFileMenuItem extends StatusBarEnabledSpacerMenuItem implements
                                                                                    AdditionalFileMenuComponent
{
    public abstract void update();
}

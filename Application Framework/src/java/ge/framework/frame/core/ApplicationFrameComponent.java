package ge.framework.frame.core;


import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.item.ApplicationFrameComponentMenuItem;

import javax.swing.Icon;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 28/02/13
 * Time: 11:03
 */
public interface ApplicationFrameComponent
{
    public ApplicationFrame getApplicationFrame();

    public ApplicationFrameComponentMenuItem getMenuItem();

    public String getMenuTitle();

    public Icon getMenuIcon();
}

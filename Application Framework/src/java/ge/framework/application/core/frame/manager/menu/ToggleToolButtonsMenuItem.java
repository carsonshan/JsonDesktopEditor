package ge.framework.application.core.frame.manager.menu;


import ge.framework.application.core.frame.manager.ApplicationDockingManager;
import ge.framework.application.core.frame.status.menu.item.StatusBarEnabledCheckboxMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 01/03/13
 * Time: 13:34
 */
public class ToggleToolButtonsMenuItem extends StatusBarEnabledCheckboxMenuItem implements ActionListener
{
    private final ApplicationDockingManager applicationDockingManager;

    public ToggleToolButtonsMenuItem( ApplicationDockingManager applicationDockingManager )
    {
        this.applicationDockingManager = applicationDockingManager;

        setText( "Tool Buttons" );

        addActionListener( this );
    }

    public void update()
    {
        setSelected( applicationDockingManager.isAutoHideAreaVisible() );
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        applicationDockingManager.setAutoHideAreaVisible( !applicationDockingManager.isAutoHideAreaVisible() );
    }
}

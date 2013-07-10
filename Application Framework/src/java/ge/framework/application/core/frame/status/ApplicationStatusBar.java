package ge.framework.application.core.frame.status;

import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.status.StatusBar;
import com.jidesoft.status.StatusBarItem;
import com.jidesoft.swing.JideBoxLayout;
import ge.framework.application.core.frame.status.enums.StatusBarConstraint;
import ge.framework.application.core.frame.status.menu.item.ToggleStatusBarMenuItem;

import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 18:50
 */
public class ApplicationStatusBar extends StatusBar implements ChangeListener
{
    private LabelStatusBarItem defaultStatusBarItem;

    private ToggleStatusBarMenuItem toggleStatusBarMenuItem;

    public ApplicationStatusBar()
    {
        super();

        initialiseStatusBar();
    }

    private void initialiseStatusBar()
    {
        defaultStatusBarItem = new LabelStatusBarItem();

        super.add( defaultStatusBarItem, JideBoxLayout.FLEXIBLE );

        MenuSelectionManager menuSelectionManager = MenuSelectionManager.defaultManager();
        menuSelectionManager.addChangeListener( this );

        toggleStatusBarMenuItem = new ToggleStatusBarMenuItem( this );
    }

    public void add( StatusBarItem statusBarItem, StatusBarConstraint constraint )
    {
        super.add( statusBarItem, constraint.getConstraint() );
    }

    public void remove( StatusBarItem statusBarItem )
    {
        super.remove( statusBarItem );
    }

    @Override
    public void stateChanged( ChangeEvent event )
    {
        MenuSelectionManager menuSelectionManager = ( MenuSelectionManager ) event.getSource();

        MenuElement[] selectedPath = menuSelectionManager.getSelectedPath();

        if ( ( selectedPath != null ) && ( selectedPath.length != 0 ) )
        {
            Collections.reverse( Arrays.asList( selectedPath ) );

            boolean changed = false;

            for ( MenuElement menuElement : selectedPath )
            {
                if ( menuElement instanceof StatusBarEnabled )
                {
                    StatusBarEnabled statusBarEnabled = ( StatusBarEnabled ) menuElement;

                    if ( statusBarEnabled.getStatusBarText() != null )
                    {
                        defaultStatusBarItem.setText( statusBarEnabled.getStatusBarText() );
                        changed = true;
                    }

                    break;
                }
            }

            if ( changed == false )
            {
                defaultStatusBarItem.setText( "" );
            }
        }
        else
        {
            defaultStatusBarItem.setText( "" );
        }
    }

    public ToggleStatusBarMenuItem getToggleStatusBarMenuItem()
    {
        return toggleStatusBarMenuItem;
    }
}

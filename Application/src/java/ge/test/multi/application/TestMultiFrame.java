package ge.test.multi.application;

import com.jidesoft.action.DockableBarContext;
import com.jidesoft.status.MemoryStatusBarItem;
import com.jidesoft.status.ResizeStatusBarItem;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.command.ApplicationCommandMenuBar;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.core.menu.ViewMenu;
import ge.framework.frame.core.status.enums.StatusBarConstraint;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.utils.controls.breadcrumb.BreadcrumbBar;

import java.awt.HeadlessException;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/07/13
 * Time: 15:43
 */
public class TestMultiFrame extends MultiApplicationFrame
{
    private ApplicationCommandMenuBar commandMenuBar;

    public TestMultiFrame( MultiFrameApplication application )
            throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseMultiApplicationFrame()
    {
        initialiseCommandMenuBar();
        initialiseCommandBar();

        initialiseStatusBar();
    }

    private void initialiseStatusBar()
    {
        MemoryStatusBarItem memorystatusbaritem = new MemoryStatusBarItem();
        memorystatusbaritem.setPreferredWidth( 100 );
        addStatusBarItem( memorystatusbaritem, StatusBarConstraint.FIX );
        addStatusBarItem( new ResizeStatusBarItem(), StatusBarConstraint.FIX );
    }

    private void initialiseCommandMenuBar()
    {
        commandMenuBar = new ApplicationCommandMenuBar( "menuBar" );
        commandMenuBar.setTitle( "Menu Bar" );
        commandMenuBar.setInitSide( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setRearrangable( false );
        commandMenuBar.setFloatable( false );
        commandMenuBar.setHidable( false );
        commandMenuBar.setAllowedDockSides( DockableBarContext.DOCK_SIDE_NORTH );
        commandMenuBar.setInitIndex( 0 );
        commandMenuBar.setStretch( true );
        commandMenuBar.setPaintBackground( false );
        commandMenuBar.setChevronAlwaysVisible( false );

        FileMenu fileMenu = getFileMenu();

        ViewMenu viewMenu = getViewMenu();

        commandMenuBar.add( fileMenu );
        commandMenuBar.add( viewMenu );

        addDockableBar( commandMenuBar );
    }

    private void initialiseCommandBar()
    {
        addDockableBar( getFileCommandBar() );
    }

    @Override
    protected void loadFrameData()
    {

    }

    @Override
    public List<AbstractFramePropertiesPage> getFrameConfigurationPages()
    {
        return null;
    }

    @Override
    protected boolean isFrameConfigurationDialogAllow()
    {
        return false;
    }

    @Override
    protected BreadcrumbBar createBreadcrumbBar()
    {
        return null;
    }

    @Override
    public boolean confirmWindowClosing()
    {
        return true;
    }

    @Override
    protected void saveFrameData()
    {

    }
}

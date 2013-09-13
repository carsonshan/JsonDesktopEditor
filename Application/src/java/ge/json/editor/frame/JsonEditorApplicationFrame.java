package ge.json.editor.frame;

import com.jidesoft.action.DockableBarContext;
import ge.framework.frame.core.dockable.ApplicationDockableFrame;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.framework.frame.core.command.ApplicationCommandMenuBar;
import ge.framework.frame.core.menu.ViewMenu;
import ge.json.editor.application.JsonEditorApplication;
import ge.utils.bundle.Resources;
import ge.utils.controls.breadcrumb.BreadcrumbBar;
import org.jdom2.Element;

import java.awt.HeadlessException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 07:59
 */
public class JsonEditorApplicationFrame extends SingleApplicationFrame<JsonEditorApplication>
{
    private static final Resources resources = Resources.getInstance( "ge.json.editor.application.resources" );

    private ApplicationCommandMenuBar commandMenuBar;

    private static int index = 1;

    public JsonEditorApplicationFrame( JsonEditorApplication application )
            throws HeadlessException
    {
        super( application );
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

    @Override
    protected void loadFrameData()
    {

    }

    @Override
    protected void initialiseSingleApplicationFrame()
    {
        initialiseCommandMenuBar();

        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
        addFrame( createFrame() );
    }

    private ApplicationDockableFrame createFrame()
    {
        return new JApplicationDockableFrame(""+index++);
    }

    private void initialiseCommandMenuBar()
    {
        commandMenuBar = new ApplicationCommandMenuBar( "menuBar" );
        commandMenuBar.setTitle( resources.getResourceString( JsonEditorApplicationFrame.class, "menuBar", "title" ) );
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

    private class JApplicationDockableFrame extends ApplicationDockableFrame
    {
        private JApplicationDockableFrame( String key )
        {
            super( key );

            setTitle( key );
            setSideTitle( getTitle() );
            setTabTitle( getTitle() );
        }

        @Override
        public Element saveLayoutData()
        {
            return null;
        }

        @Override
        public void loadLayoutData( Element element )
        {

        }
    }
}

package ge.framework.frame.core.document;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentComponentAdapter;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.IDocumentPane;
import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.document.menu.ApplicationDocumentComponentMenuItem;
import ge.framework.frame.core.menu.item.ApplicationFrameComponentMenuItem;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:40
 */
public abstract class ApplicationDocumentComponent extends DocumentComponent implements ApplicationFrameComponent
{
    private ApplicationDocumentComponentMenuItem documentMenuItem;

    private boolean initialisePanel = false;

    public ApplicationDocumentComponent( String name )
    {
        super( new JPanel( new BorderLayout() ), name );
        initialise();
    }

    private void initialise()
    {
        addDocumentComponentListener( new ApplicationDocumentComponentAdapter( this ) );
        documentMenuItem = new ApplicationDocumentComponentMenuItem( this );
    }

    public ApplicationFrameComponentMenuItem getMenuItem()
    {
        return documentMenuItem;
    }

    @Override
    public String getMenuTitle()
    {
        return getDisplayTitle();
    }

    @Override
    public Icon getMenuIcon()
    {
        return getIcon();
    }

    public void showDocument()
    {
        IDocumentPane documentPane = getDocumentPane();

        documentPane.setActiveDocument( getName(), true );
    }

    public final void documentOpened()
    {
        if ( initialisePanel == false )
        {
            initialisePanel();
        }
        documentMenuItem.setVisible( true );
    }

    private void initialisePanel()
    {
        JComponent contentPanel = createContentPanel();

        if ( contentPanel != null )
        {
            JComponent component = getComponent();

            component.add( BorderLayout.CENTER, contentPanel );
        }
    }

    public abstract JComponent createContentPanel();

    public final void documentClosed()
    {
        documentMenuItem.setVisible( false );
    }

    private class ApplicationDocumentComponentAdapter extends DocumentComponentAdapter
    {
        private final ApplicationDocumentComponent applicationDocumentComponent;

        public ApplicationDocumentComponentAdapter(
                ApplicationDocumentComponent applicationDocumentComponent )
        {
            this.applicationDocumentComponent = applicationDocumentComponent;
        }

        @Override
        public void documentComponentOpened( DocumentComponentEvent documentComponentEvent )
        {
            applicationDocumentComponent.documentOpened();
        }

        @Override
        public void documentComponentClosed( DocumentComponentEvent documentComponentEvent )
        {
            applicationDocumentComponent.documentClosed();
        }
    }
}

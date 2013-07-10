package ge.framework.application.core.frame.document;

import com.jidesoft.document.DocumentComponent;
import com.jidesoft.document.DocumentComponentAdapter;
import com.jidesoft.document.DocumentComponentEvent;
import com.jidesoft.document.DocumentPane;
import ge.framework.application.core.frame.ApplicationFrame;
import ge.framework.application.core.frame.document.menu.ApplicationDocumentComponentMenu;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:28
 */
public class ApplicationDocumentPane extends DocumentPane
{
    private final ApplicationDocumentComponentMenu applicationDocumentComponentMenu;

    private ApplicationDocumentComponentAdapter applicationDocumentComponentAdapter;

    public ApplicationDocumentPane( ApplicationFrame applicationFrame )
    {
        applicationDocumentComponentAdapter = new ApplicationDocumentComponentAdapter( this );

        applicationDocumentComponentMenu = new ApplicationDocumentComponentMenu( applicationFrame );
    }

    public ApplicationDocumentComponentMenu getApplicationDocumentComponentMenu()
    {
        return applicationDocumentComponentMenu;
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        openDocument( applicationDocumentComponent, false );
    }

    public void openDocument( ApplicationDocumentComponent applicationDocumentComponent, boolean floating )
    {
        applicationDocumentComponent.addDocumentComponentListener( applicationDocumentComponentAdapter );
        super.openDocument( applicationDocumentComponent, floating );
    }

    public void closeDocument( ApplicationDocumentComponent applicationDocumentComponent )
    {
        super.closeSingleDocument( applicationDocumentComponent.getName() );
    }

    public void closeAllButThis( ApplicationDocumentComponent applicationDocumentComponent )
    {
        super.closeAllButThis( applicationDocumentComponent.getName() );
    }

    final void documentOpened( ApplicationDocumentComponent applicationDocumentComponent )
    {
        applicationDocumentComponentMenu.add( applicationDocumentComponent );
    }

    final void documentClosed( ApplicationDocumentComponent applicationDocumentComponent )
    {
        applicationDocumentComponentMenu.remove( applicationDocumentComponent );

        applicationDocumentComponent.removeDocumentComponentListener( applicationDocumentComponentAdapter );
    }

    private class ApplicationDocumentComponentAdapter extends DocumentComponentAdapter
    {
        private ApplicationDocumentPane applicationDocumentPane;

        public ApplicationDocumentComponentAdapter( ApplicationDocumentPane applicationDocumentPane )
        {
            this.applicationDocumentPane = applicationDocumentPane;
        }

        @Override
        public void documentComponentOpened( DocumentComponentEvent documentComponentEvent )
        {
            DocumentComponent documentComponent = documentComponentEvent.getDocumentComponent();

            if ( documentComponent instanceof ApplicationDocumentComponent )
            {
                applicationDocumentPane.documentOpened( ( ApplicationDocumentComponent ) documentComponent );
            }
        }

        @Override
        public void documentComponentClosed( DocumentComponentEvent documentComponentEvent )
        {
            DocumentComponent documentComponent = documentComponentEvent.getDocumentComponent();

            if ( documentComponent instanceof ApplicationDocumentComponent )
            {
                applicationDocumentPane.documentClosed( ( ApplicationDocumentComponent ) documentComponent );
            }
        }
    }
}

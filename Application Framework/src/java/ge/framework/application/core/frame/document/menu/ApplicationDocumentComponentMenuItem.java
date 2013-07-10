package ge.framework.application.core.frame.document.menu;


import ge.framework.application.core.frame.document.ApplicationDocumentComponent;
import ge.framework.application.core.frame.menu.item.ApplicationFrameComponentMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 14:25
 */
public class ApplicationDocumentComponentMenuItem extends ApplicationFrameComponentMenuItem implements ActionListener
{
    public ApplicationDocumentComponentMenuItem( ApplicationDocumentComponent applicationDocumentComponent )
    {
        super( applicationDocumentComponent );

        addActionListener( this );
    }

    public void actionPerformed( ActionEvent e )
    {
        ApplicationDocumentComponent applicationDocumentComponent =
                ( ApplicationDocumentComponent ) getApplicationFrameComponent();

        applicationDocumentComponent.showDocument();
    }
}

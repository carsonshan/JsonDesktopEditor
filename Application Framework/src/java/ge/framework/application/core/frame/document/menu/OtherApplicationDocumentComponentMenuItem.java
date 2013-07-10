package ge.framework.application.core.frame.document.menu;

import ge.framework.application.core.frame.ApplicationFrameComponent;
import ge.framework.application.core.frame.document.ApplicationDocumentComponent;
import ge.framework.application.core.frame.document.dialog.DocumentsDialog;
import ge.framework.application.core.frame.menu.item.OtherApplicationFrameComponentMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 11:48
 */
public class OtherApplicationDocumentComponentMenuItem extends OtherApplicationFrameComponentMenuItem implements
                                                                                                      ActionListener
{
    public OtherApplicationDocumentComponentMenuItem()
    {
        super();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {
        List<ApplicationFrameComponent> applicationFrameComponents =
                getApplicationFrameComponents();

        DocumentsDialog documentsDialog = new DocumentsDialog( applicationFrameComponents );

        if ( documentsDialog.doModal() == true )
        {
            ApplicationDocumentComponent documentComponent =
                    ( ApplicationDocumentComponent ) documentsDialog.getApplicationFrameComponent();

            documentComponent.showDocument();
        }
    }
}

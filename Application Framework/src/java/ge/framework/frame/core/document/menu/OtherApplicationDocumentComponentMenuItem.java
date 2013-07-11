package ge.framework.frame.core.document.menu;

import ge.framework.frame.core.ApplicationFrameComponent;
import ge.framework.frame.core.document.ApplicationDocumentComponent;
import ge.framework.frame.core.document.dialog.DocumentsDialog;
import ge.framework.frame.core.menu.item.OtherApplicationFrameComponentMenuItem;

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

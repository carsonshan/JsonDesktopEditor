package ge.utils.properties;

import com.jidesoft.dialog.ButtonPanel;

import javax.swing.JScrollPane;
import java.awt.Dimension;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/13
 * Time: 17:24
 */
public class ButtonPanelScrollPane extends JScrollPane
{
    public ButtonPanelScrollPane( ButtonPanel view )
    {
        super( view );
    }

    public Dimension getPreferredSize()
    {
        ButtonPanel view = ( ButtonPanel ) getViewport().getView();
        if ( ( view.getAlignment() == 1 ) || ( view.getAlignment() == 3 ) )
        {
            return new Dimension(
                    view.getPreferredSize().width + getVerticalScrollBar().getPreferredSize().width, 5 );
        }
        return new Dimension( 5, view.getPreferredSize().height +
                getHorizontalScrollBar().getPreferredSize().height );
    }

    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
}

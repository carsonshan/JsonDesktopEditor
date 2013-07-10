package ge.utils.renderer;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.Component;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 20/06/13
 * Time: 11:39
 */
public class BooleanListCellRenderer extends DefaultListCellRenderer
{
    private String trueLabel;

    private String falseLabel;

    public BooleanListCellRenderer( String trueLabel, String falseLabel )
    {
        this.trueLabel = trueLabel;
        this.falseLabel = falseLabel;
    }

    @Override
    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected,
                                                   boolean cellHasFocus )
    {
        if ( ( value != null ) && ( value instanceof Boolean ) )
        {
            Boolean booleanValue = ( Boolean ) value;
            if ( booleanValue == true )
            {
                value = trueLabel;
            }
            else
            {
                value = falseLabel;
            }
        }

        if ( value == null )
        {
            value = " ";
        }

        return super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
    }
}

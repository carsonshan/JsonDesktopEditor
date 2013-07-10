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
public class EnumListCellRenderer extends DefaultListCellRenderer
{
    private Map<Enum, String> enumLabels;

    public EnumListCellRenderer( Map<Enum, String> enumLabels )
    {
        this.enumLabels = enumLabels;
    }

    @Override
    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected,
                                                   boolean cellHasFocus )
    {
        if ( ( value != null ) && ( value instanceof Enum ) )
        {
            value = enumLabels.get( value );
        }

        if ( value == null )
        {
            value = " ";
        }

        return super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
    }
}

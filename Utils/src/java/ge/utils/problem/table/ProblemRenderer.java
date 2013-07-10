package ge.utils.problem.table;

import ge.utils.problem.object.Problem;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class ProblemRenderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent( JTable table,
                                                    Object value,
                                                    boolean isSelected,
                                                    boolean hasFocus,
                                                    int row,
                                                    int column )
    {
        if ( value instanceof Problem )
        {
            Problem problem = ( Problem ) value;

            setOpaque( false );

            if ( isSelected )
            {
                setBackground( table.getSelectionBackground() );
                setForeground( table.getSelectionForeground() );
            }
            else
            {
                setBackground( table.getBackground() );
                setForeground( table.getForeground() );
            }

            setFont( table.getFont() );

            if ( column == 0 )
            {
                setIcon( problem.getResourceIcon() );

                setText( problem.getResourceString() );
            }

            return this;
        }
        else
        {
            return super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
        }
    }

    @Override
    public void setEnabled( boolean enabled )
    {
    }
}

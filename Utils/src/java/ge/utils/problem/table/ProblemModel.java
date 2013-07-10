package ge.utils.problem.table;

import ge.utils.problem.object.Problem;

import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 09:21
 */
public class ProblemModel implements TableModel
{
    private EventListenerList eventListenerList = new EventListenerList();

    private List<Problem> problems = null;

    public ProblemModel( List<Problem> problems )
    {
        this.problems = problems;
    }

    @Override
    public int getRowCount()
    {
        return problems.size();
    }

    @Override
    public int getColumnCount()
    {
        return 1;
    }

    @Override
    public String getColumnName( int columnIndex )
    {
        return null;
    }

    @Override
    public Class<?> getColumnClass( int columnIndex )
    {
        return Problem.class;
    }

    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex )
    {
        return false;
    }

    @Override
    public Object getValueAt( int rowIndex, int columnIndex )
    {
        return problems.get( rowIndex );
    }

    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex )
    {
    }

    @Override
    public void addTableModelListener( TableModelListener l )
    {
        eventListenerList.add( TableModelListener.class, l );
    }

    @Override
    public void removeTableModelListener( TableModelListener l )
    {
        eventListenerList.remove( TableModelListener.class, l );
    }

    public void fireTableRowsInserted( int firstRow, int lastRow )
    {
        fireTableChanged( new TableModelEvent( this,
                                               firstRow,
                                               lastRow,
                                               TableModelEvent.ALL_COLUMNS,
                                               TableModelEvent.INSERT ) );
    }

    private void fireTableChanged( TableModelEvent tableModelEvent )
    {
        TableModelListener[] listeners = eventListenerList.getListeners( TableModelListener.class );

        for ( TableModelListener listener : listeners )
        {
            listener.tableChanged( tableModelEvent );
        }
    }
}

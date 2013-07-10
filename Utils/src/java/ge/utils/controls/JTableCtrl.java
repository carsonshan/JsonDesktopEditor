package ge.utils.controls;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.EventObject;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: evison_g
 * Date: Mar 19, 2010
 * Time: 11:56:17 AM
 */
public class JTableCtrl extends JScrollCtrl
{
    private JTable table = null;

    public JTableCtrl()
    {
        super();
        initialiseTable();
        initialiseScroll( table );
    }

    public JTableCtrl( int numRows, int numColumns )
    {
        super();
        initialiseTable( numRows, numColumns );
        initialiseScroll( table );
    }

    public JTableCtrl( Object[][] rowData, Object[] columnNames )
    {
        super();
        initialiseTable( rowData, columnNames );
        initialiseScroll( table );
    }

    public JTableCtrl( TableModel dm )
    {
        super();
        initialiseTable( dm );
        initialiseScroll( table );
    }

    public JTableCtrl( TableModel dm, TableColumnModel cm )
    {
        super();
        initialiseTable( dm, cm );
        initialiseScroll( table );
    }

    public JTableCtrl( TableModel dm, TableColumnModel cm, ListSelectionModel sm )
    {
        super();
        initialiseTable( dm, cm, sm );
        initialiseScroll( table );
    }

    public JTableCtrl( Vector rowData, Vector columnNames )
    {
        super();
        initialiseTable( rowData, columnNames );
        initialiseScroll( table );
    }

    private void initialiseTable()
    {
        table = new JTable();
    }

    private void initialiseTable( int numRows, int numColumns )
    {
        table = new JTable( numRows, numColumns );
    }

    private void initialiseTable( Object[][] rowData, Object[] columnNames )
    {
        table = new JTable( rowData, columnNames );
    }

    private void initialiseTable( TableModel dm )
    {
        table = new JTable( dm );
    }

    private void initialiseTable( TableModel dm, TableColumnModel cm )
    {
        table = new JTable( dm, cm );
    }

    private void initialiseTable( TableModel dm, TableColumnModel cm, ListSelectionModel sm )
    {
        table = new JTable( dm, cm, sm );
    }

    private void initialiseTable( Vector rowData, Vector columnNames )
    {
        table = new JTable( rowData, columnNames );
    }

    public JTable getTable()
    {
        return table;
    }

    public void addColumn( TableColumn aColumn )
    {
        table.addColumn( aColumn );
    }

    public void addColumnSelectionInterval( int index0, int index1 )
    {
        table.addColumnSelectionInterval( index0, index1 );
    }

    public void addRowSelectionInterval( int index0, int index1 )
    {
        table.addRowSelectionInterval( index0, index1 );
    }

    public void changeSelection( int rowIndex, int columnIndex, boolean toggle, boolean extend )
    {
        table.changeSelection( rowIndex, columnIndex, toggle, extend );
    }

    public void clearSelection()
    {
        table.clearSelection();
    }

    public int columnAtPoint( Point point )
    {
        return table.columnAtPoint( point );
    }

    public int convertColumnIndexToModel( int viewColumnIndex )
    {
        return table.convertColumnIndexToModel( viewColumnIndex );
    }

    public int convertColumnIndexToView( int modelColumnIndex )
    {
        return table.convertColumnIndexToView( modelColumnIndex );
    }

    public void createDefaultColumnsFromModel()
    {
        table.createDefaultColumnsFromModel();
    }

    public boolean editCellAt( int row, int column )
    {
        return table.editCellAt( row, column );
    }

    public boolean editCellAt( int row, int column, EventObject e )
    {
        return table.editCellAt( row, column, e );
    }

    public boolean getAutoCreateColumnsFromModel()
    {
        return table.getAutoCreateColumnsFromModel();
    }

    public int getAutoResizeMode()
    {
        return table.getAutoResizeMode();
    }

    public TableCellEditor getCellEditor()
    {
        return table.getCellEditor();
    }

    public TableCellEditor getCellEditor( int row, int column )
    {
        return table.getCellEditor( row, column );
    }

    public Rectangle getCellRect( int row, int column, boolean includeSpacing )
    {
        return table.getCellRect( row, column, includeSpacing );
    }

    public TableCellRenderer getCellRenderer( int row, int column )
    {
        return table.getCellRenderer( row, column );
    }

    public boolean getCellSelectionEnabled()
    {
        return table.getCellSelectionEnabled();
    }

    public TableColumn getColumn( Object identifier )
    {
        return table.getColumn( identifier );
    }

    public Class getColumnClass( int column )
    {
        return table.getColumnClass( column );
    }

    public int getColumnCount()
    {
        return table.getColumnCount();
    }

    public TableColumnModel getColumnModel()
    {
        return table.getColumnModel();
    }

    public String getColumnName( int column )
    {
        return table.getColumnName( column );
    }

    public boolean getColumnSelectionAllowed()
    {
        return table.getColumnSelectionAllowed();
    }

    public TableCellEditor getDefaultEditor( Class columnClass )
    {
        return table.getDefaultEditor( columnClass );
    }

    public TableCellRenderer getDefaultRenderer( Class columnClass )
    {
        return table.getDefaultRenderer( columnClass );
    }

    public boolean getDragEnabled()
    {
        return table.getDragEnabled();
    }

    public int getEditingColumn()
    {
        return table.getEditingColumn();
    }

    public int getEditingRow()
    {
        return table.getEditingRow();
    }

    public Component getEditorComponent()
    {
        return table.getEditorComponent();
    }

    public Color getGridColor()
    {
        return table.getGridColor();
    }

    public Dimension getIntercellSpacing()
    {
        return table.getIntercellSpacing();
    }

    public TableModel getModel()
    {
        return table.getModel();
    }

    public Dimension getPreferredScrollableViewportSize()
    {
        return table.getPreferredScrollableViewportSize();
    }

    public int getRowCount()
    {
        return table.getRowCount();
    }

    public int getRowHeight()
    {
        return table.getRowHeight();
    }

    public int getRowHeight( int row )
    {
        return table.getRowHeight( row );
    }

    public int getRowMargin()
    {
        return table.getRowMargin();
    }

    public boolean getRowSelectionAllowed()
    {
        return table.getRowSelectionAllowed();
    }

    public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction )
    {
        return table.getScrollableBlockIncrement( visibleRect, orientation, direction );
    }

    public boolean getScrollableTracksViewportHeight()
    {
        return table.getScrollableTracksViewportHeight();
    }

    public boolean getScrollableTracksViewportWidth()
    {
        return table.getScrollableTracksViewportWidth();
    }

    public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction )
    {
        return table.getScrollableUnitIncrement( visibleRect, orientation, direction );
    }

    public int getSelectedColumn()
    {
        return table.getSelectedColumn();
    }

    public int getSelectedColumnCount()
    {
        return table.getSelectedColumnCount();
    }

    public int[] getSelectedColumns()
    {
        return table.getSelectedColumns();
    }

    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    public int getSelectedRowCount()
    {
        return table.getSelectedRowCount();
    }

    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

    public Color getSelectionBackground()
    {
        return table.getSelectionBackground();
    }

    public Color getSelectionForeground()
    {
        return table.getSelectionForeground();
    }

    public ListSelectionModel getSelectionModel()
    {
        return table.getSelectionModel();
    }

    public boolean getShowHorizontalLines()
    {
        return table.getShowHorizontalLines();
    }

    public boolean getShowVerticalLines()
    {
        return table.getShowVerticalLines();
    }

    public boolean getSurrendersFocusOnKeystroke()
    {
        return table.getSurrendersFocusOnKeystroke();
    }

    public JTableHeader getTableHeader()
    {
        return table.getTableHeader();
    }

    public Object getValueAt( int row, int column )
    {
        return table.getValueAt( row, column );
    }

    public boolean isCellEditable( int row, int column )
    {
        return table.isCellEditable( row, column );
    }

    public boolean isCellSelected( int row, int column )
    {
        return table.isCellSelected( row, column );
    }

    public boolean isColumnSelected( int column )
    {
        return table.isColumnSelected( column );
    }

    public boolean isEditing()
    {
        return table.isEditing();
    }

    public boolean isRowSelected( int row )
    {
        return table.isRowSelected( row );
    }

    public void moveColumn( int column, int targetColumn )
    {
        table.moveColumn( column, targetColumn );
    }

    public Component prepareEditor( TableCellEditor editor, int row, int column )
    {
        return table.prepareEditor( editor, row, column );
    }

    public Component prepareRenderer( TableCellRenderer renderer, int row, int column )
    {
        return table.prepareRenderer( renderer, row, column );
    }

    public void removeColumn( TableColumn aColumn )
    {
        table.removeColumn( aColumn );
    }

    public void removeColumnSelectionInterval( int index0, int index1 )
    {
        table.removeColumnSelectionInterval( index0, index1 );
    }

    public void removeEditor()
    {
        table.removeEditor();
    }

    public void removeRowSelectionInterval( int index0, int index1 )
    {
        table.removeRowSelectionInterval( index0, index1 );
    }

    public int rowAtPoint( Point point )
    {
        return table.rowAtPoint( point );
    }

    public void selectAll()
    {
        table.selectAll();
    }

    public void setAutoCreateColumnsFromModel( boolean autoCreateColumnsFromModel )
    {
        table.setAutoCreateColumnsFromModel( autoCreateColumnsFromModel );
    }

    public void setAutoResizeMode( int mode )
    {
        table.setAutoResizeMode( mode );
    }

    public void setBackground( Color background )
    {
        super.setBackground( background );
        table.setBackground( background );
    }

    public void setCellEditor( TableCellEditor anEditor )
    {
        table.setCellEditor( anEditor );
    }

    public void setCellSelectionEnabled( boolean cellSelectionEnabled )
    {
        table.setCellSelectionEnabled( cellSelectionEnabled );
    }

    public void setColumnModel( TableColumnModel columnModel )
    {
        table.setColumnModel( columnModel );
    }

    public void setColumnSelectionAllowed( boolean columnSelectionAllowed )
    {
        table.setColumnSelectionAllowed( columnSelectionAllowed );
    }

    public void setColumnSelectionInterval( int index0, int index1 )
    {
        table.setColumnSelectionInterval( index0, index1 );
    }

    public void setDefaultEditor( Class columnClass, TableCellEditor editor )
    {
        table.setDefaultEditor( columnClass, editor );
    }

    public void setDefaultRenderer( Class columnClass, TableCellRenderer renderer )
    {
        table.setDefaultRenderer( columnClass, renderer );
    }

    public void setDragEnabled( boolean b )
    {
        table.setDragEnabled( b );
    }

    public void setEditingColumn( int aColumn )
    {
        table.setEditingColumn( aColumn );
    }

    public void setEditingRow( int aRow )
    {
        table.setEditingRow( aRow );
    }

    public void setEnabled( boolean enabled )
    {
        super.setEnabled( enabled );
        table.setEnabled( enabled );
    }

    public void setGridColor( Color gridColor )
    {
        table.setGridColor( gridColor );
    }

    public void setIntercellSpacing( Dimension intercellSpacing )
    {
        table.setIntercellSpacing( intercellSpacing );
    }

    public void setModel( TableModel dataModel )
    {
        table.setModel( dataModel );
    }

    public void setPreferredScrollableViewportSize( Dimension size )
    {
        table.setPreferredScrollableViewportSize( size );
    }

    public void setRowHeight( int rowHeight )
    {
        table.setRowHeight( rowHeight );
    }

    public void setRowHeight( int row, int rowHeight )
    {
        table.setRowHeight( row, rowHeight );
    }

    public void setRowMargin( int rowMargin )
    {
        table.setRowMargin( rowMargin );
    }

    public void setRowSelectionAllowed( boolean rowSelectionAllowed )
    {
        table.setRowSelectionAllowed( rowSelectionAllowed );
    }

    public void setRowSelectionInterval( int index0, int index1 )
    {
        table.setRowSelectionInterval( index0, index1 );
    }

    public void setSelectionBackground( Color selectionBackground )
    {
        table.setSelectionBackground( selectionBackground );
    }

    public void setSelectionForeground( Color selectionForeground )
    {
        table.setSelectionForeground( selectionForeground );
    }

    public void setSelectionMode( int selectionMode )
    {
        table.setSelectionMode( selectionMode );
    }

    public void setSelectionModel( ListSelectionModel newModel )
    {
        table.setSelectionModel( newModel );
    }

    public void setShowGrid( boolean showGrid )
    {
        table.setShowGrid( showGrid );
    }

    public void setShowHorizontalLines( boolean showHorizontalLines )
    {
        table.setShowHorizontalLines( showHorizontalLines );
    }

    public void setShowVerticalLines( boolean showVerticalLines )
    {
        table.setShowVerticalLines( showVerticalLines );
    }

    public void setSurrendersFocusOnKeystroke( boolean surrendersFocusOnKeystroke )
    {
        table.setSurrendersFocusOnKeystroke( surrendersFocusOnKeystroke );
    }

    public void setTableHeader( JTableHeader tableHeader )
    {
        table.setTableHeader( tableHeader );
    }

    public void setValueAt( Object aValue, int row, int column )
    {
        table.setValueAt( aValue, row, column );
    }

    public void sizeColumnsToFit( int resizingColumn )
    {
        table.sizeColumnsToFit( resizingColumn );
    }
}

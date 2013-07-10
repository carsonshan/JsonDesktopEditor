package ge.utils.controls;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: evison_g
 * Date: Mar 25, 2010
 * Time: 2:16:15 PM
 */
public class JListCtrl extends JScrollCtrl implements ListSelectionListener
{
    private JList list = null;

    public JListCtrl()
    {
        super();
        initialiseList();
        initialiseScroll( list );
    }

    public JListCtrl( ListModel dataModel )
    {
        super();
        initialiseList( dataModel );
        initialiseScroll( list );
    }

    public JListCtrl( Object[] listData )
    {
        super();
        initialiseList( listData );
        initialiseScroll( list );
    }

    public JListCtrl( Vector listData )
    {
        super();
        initialiseList( listData );
        initialiseScroll( list );
    }

    public JListCtrl( int vsbPolicy, int hsbPolicy )
    {
        super( vsbPolicy, hsbPolicy );
        initialiseList();
        initialiseScroll( list );
    }

    public JListCtrl( ListModel dataModel, int vsbPolicy, int hsbPolicy )
    {
        super( vsbPolicy, hsbPolicy );
        initialiseList( dataModel );
        initialiseScroll( list );
    }

    public JListCtrl( Object[] listData, int vsbPolicy, int hsbPolicy )
    {
        super( vsbPolicy, hsbPolicy );
        initialiseList( listData );
        initialiseScroll( list );
    }

    public JListCtrl( Vector listData, int vsbPolicy, int hsbPolicy )
    {
        super( vsbPolicy, hsbPolicy );
        initialiseList( listData );
        initialiseScroll( list );
    }

    private void initialiseList()
    {
        list = new JList();
        initialiseListeners();
    }

    private void initialiseList( ListModel dataModel )
    {
        list = new JList( dataModel );
        initialiseListeners();
    }

    private void initialiseList( Object[] listData )
    {
        list = new JList( listData );
        initialiseListeners();
    }

    private void initialiseList( Vector listData )
    {
        list = new JList( listData );
        initialiseListeners();
    }

    private void initialiseListeners()
    {
        list.addListSelectionListener( this );
    }

    public JList getList()
    {
        return list;
    }

    public void addListSelectionListener( ListSelectionListener listener )
    {
        listenerList.add( ListSelectionListener.class, listener );
    }

    public void addSelectionInterval( int anchor, int lead )
    {
        list.addSelectionInterval( anchor, lead );
    }

    public void clearSelection()
    {
        list.clearSelection();
    }

    public void ensureIndexIsVisible( int index )
    {
        list.ensureIndexIsVisible( index );
    }

    public int getAnchorSelectionIndex()
    {
        return list.getAnchorSelectionIndex();
    }

    public Rectangle getCellBounds( int index0, int index1 )
    {
        return list.getCellBounds( index0, index1 );
    }

    public ListCellRenderer getCellRenderer()
    {
        return list.getCellRenderer();
    }

    public boolean getDragEnabled()
    {
        return list.getDragEnabled();
    }

    public int getFirstVisibleIndex()
    {
        return list.getFirstVisibleIndex();
    }

    public int getFixedCellHeight()
    {
        return list.getFixedCellHeight();
    }

    public int getFixedCellWidth()
    {
        return list.getFixedCellWidth();
    }

    public int getLastVisibleIndex()
    {
        return list.getLastVisibleIndex();
    }

    public int getLayoutOrientation()
    {
        return list.getLayoutOrientation();
    }

    public int getLeadSelectionIndex()
    {
        return list.getLeadSelectionIndex();
    }

    public ListSelectionListener[] getListSelectionListeners()
    {
        return listenerList.getListeners( ListSelectionListener.class );
    }

    public int getMaxSelectionIndex()
    {
        return list.getMaxSelectionIndex();
    }

    public int getMinSelectionIndex()
    {
        return list.getMinSelectionIndex();
    }

    public ListModel getModel()
    {
        return list.getModel();
    }

    public int getNextMatch( String prefix, int startIndex, Position.Bias bias )
    {
        return list.getNextMatch( prefix, startIndex, bias );
    }

    public Dimension getPreferredScrollableViewportSize()
    {
        return list.getPreferredScrollableViewportSize();
    }

    public Object getPrototypeCellValue()
    {
        return list.getPrototypeCellValue();
    }

    public int getScrollableBlockIncrement( Rectangle visibleRect, int orientation, int direction )
    {
        return list.getScrollableBlockIncrement( visibleRect, orientation, direction );
    }

    public boolean getScrollableTracksViewportHeight()
    {
        return list.getScrollableTracksViewportHeight();
    }

    public boolean getScrollableTracksViewportWidth()
    {
        return list.getScrollableTracksViewportWidth();
    }

    public int getScrollableUnitIncrement( Rectangle visibleRect, int orientation, int direction )
    {
        return list.getScrollableUnitIncrement( visibleRect, orientation, direction );
    }

    public int getSelectedIndex()
    {
        return list.getSelectedIndex();
    }

    public int[] getSelectedIndices()
    {
        return list.getSelectedIndices();
    }

    public Object getSelectedValue()
    {
        return list.getSelectedValue();
    }

    public Object[] getSelectedValues()
    {
        return list.getSelectedValues();
    }

    public Color getSelectionBackground()
    {
        return list.getSelectionBackground();
    }

    public Color getSelectionForeground()
    {
        return list.getSelectionForeground();
    }

    public int getSelectionMode()
    {
        return list.getSelectionMode();
    }

    public ListSelectionModel getSelectionModel()
    {
        return list.getSelectionModel();
    }

    public boolean getValueIsAdjusting()
    {
        return list.getValueIsAdjusting();
    }

    public int getVisibleRowCount()
    {
        return list.getVisibleRowCount();
    }

    public Point indexToLocation( int index )
    {
        return list.indexToLocation( index );
    }

    public boolean isSelectedIndex( int index )
    {
        return list.isSelectedIndex( index );
    }

    public boolean isSelectionEmpty()
    {
        return list.isSelectionEmpty();
    }

    public int locationToIndex( Point location )
    {
        return list.locationToIndex( location );
    }

    public void removeListSelectionListener( ListSelectionListener listener )
    {
        listenerList.remove( ListSelectionListener.class, listener );
    }

    public void removeSelectionInterval( int index0, int index1 )
    {
        list.removeSelectionInterval( index0, index1 );
    }

    public void setCellRenderer( ListCellRenderer cellRenderer )
    {
        list.setCellRenderer( cellRenderer );
    }

    public void setDragEnabled( boolean b )
    {
        list.setDragEnabled( b );
    }

    public void setEnabled( boolean enabled )
    {
        super.setEnabled( enabled );
        list.setEnabled( enabled );
    }

    public void setFixedCellHeight( int height )
    {
        list.setFixedCellHeight( height );
    }

    public void setFixedCellWidth( int width )
    {
        list.setFixedCellWidth( width );
    }

    public void setLayoutOrientation( int layoutOrientation )
    {
        list.setLayoutOrientation( layoutOrientation );
    }

    public void setListData( Object[] listData )
    {
        list.setListData( listData );
    }

    public void setListData( Vector listData )
    {
        list.setListData( listData );
    }

    public void setModel( ListModel model )
    {
        list.setModel( model );
    }

    public void setPrototypeCellValue( Object prototypeCellValue )
    {
        list.setPrototypeCellValue( prototypeCellValue );
    }

    public void setSelectedIndex( int index )
    {
        list.setSelectedIndex( index );
    }

    public void setSelectedIndices( int[] indices )
    {
        list.setSelectedIndices( indices );
    }

    public void setSelectedValue( Object anObject, boolean shouldScroll )
    {
        list.setSelectedValue( anObject, shouldScroll );
    }

    public void setSelectionBackground( Color selectionBackground )
    {
        list.setSelectionBackground( selectionBackground );
    }

    public void setSelectionForeground( Color selectionForeground )
    {
        list.setSelectionForeground( selectionForeground );
    }

    public void setSelectionInterval( int anchor, int lead )
    {
        list.setSelectionInterval( anchor, lead );
    }

    public void setSelectionMode( int selectionMode )
    {
        list.setSelectionMode( selectionMode );
    }

    public void setSelectionModel( ListSelectionModel selectionModel )
    {
        list.setSelectionModel( selectionModel );
    }

    public void setValueIsAdjusting( boolean b )
    {
        list.setValueIsAdjusting( b );
    }

    public void setVisibleRowCount( int visibleRowCount )
    {
        list.setVisibleRowCount( visibleRowCount );
    }

    public void valueChanged( ListSelectionEvent e )
    {
        ListSelectionEvent event =
                new ListSelectionEvent( this, e.getFirstIndex(), e.getLastIndex(), e.getValueIsAdjusting() );
        ListSelectionListener[] listeners = getListSelectionListeners();
        for ( int i = 0; i < listeners.length; i++ )
        {
            listeners[ i ].valueChanged( event );
        }
    }
}


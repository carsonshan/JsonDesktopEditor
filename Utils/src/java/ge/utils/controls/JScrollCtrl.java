package ge.utils.controls;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Created by IntelliJ IDEA.
 * User: evison_g
 * Date: 17/05/11
 * Time: 11:34
 */
public class JScrollCtrl extends JComponent implements ScrollPaneConstants
{
    private int vsbPolicy = VERTICAL_SCROLLBAR_AS_NEEDED;

    private int hsbPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED;

    private JScrollPane scroll = null;

    public JScrollCtrl()
    {
        super();
        initialiseComponent();
    }

    public JScrollCtrl( int vsbPolicy, int hsbPolicy )
    {
        super();
        this.vsbPolicy = vsbPolicy;
        this.hsbPolicy = hsbPolicy;
        initialiseComponent();
    }

    private void initialiseComponent()
    {
        setLayout( new BorderLayout() );
    }

    protected void initialiseScroll( Component component )
    {
        scroll = new JScrollPane( component, vsbPolicy, hsbPolicy );
        add( scroll, BorderLayout.CENTER );
    }

    public JScrollPane getScrollPane()
    {
        return scroll;
    }

    public JScrollBar createHorizontalScrollBar()
    {
        return scroll.createHorizontalScrollBar();
    }

    public JScrollBar createVerticalScrollBar()
    {
        return scroll.createVerticalScrollBar();
    }

    public JViewport getColumnHeader()
    {
        return scroll.getColumnHeader();
    }

    public Component getCorner( String key )
    {
        return scroll.getCorner( key );
    }

    public JScrollBar getHorizontalScrollBar()
    {
        return scroll.getHorizontalScrollBar();
    }

    public int getHorizontalScrollBarPolicy()
    {
        return scroll.getHorizontalScrollBarPolicy();
    }

    public JViewport getRowHeader()
    {
        return scroll.getRowHeader();
    }

    public JScrollBar getVerticalScrollBar()
    {
        return scroll.getVerticalScrollBar();
    }

    public int getVerticalScrollBarPolicy()
    {
        return scroll.getVerticalScrollBarPolicy();
    }

    public JViewport getViewport()
    {
        return scroll.getViewport();
    }

    public Border getViewportBorder()
    {
        return scroll.getViewportBorder();
    }

    public Rectangle getViewportBorderBounds()
    {
        return scroll.getViewportBorderBounds();
    }

    public boolean isWheelScrollingEnabled()
    {
        return scroll.isWheelScrollingEnabled();
    }

    public void setColumnHeader( JViewport columnHeader )
    {
        scroll.setColumnHeader( columnHeader );
    }

    public void setColumnHeaderView( Component view )
    {
        scroll.setColumnHeaderView( view );
    }

    public void setCorner( String key, Component corner )
    {
        scroll.setCorner( key, corner );
    }

    public void setEnabled( boolean enabled )
    {
        super.setEnabled( enabled );

        scroll.setEnabled( enabled );

        JScrollBar horizontalScrollBar = scroll.getHorizontalScrollBar();
        if ( horizontalScrollBar != null )
        {
            horizontalScrollBar.setEnabled( enabled );
        }

        JScrollBar verticalScrollBar = scroll.getVerticalScrollBar();
        if ( verticalScrollBar != null )
        {
            verticalScrollBar.setEnabled( enabled );
        }
    }

    public void setHorizontalScrollBar( JScrollBar horizontalScrollBar )
    {
        scroll.setHorizontalScrollBar( horizontalScrollBar );
    }

    public void setHorizontalScrollBarPolicy( int policy )
    {
        scroll.setHorizontalScrollBarPolicy( policy );
    }

    public void setRowHeader( JViewport rowHeader )
    {
        scroll.setRowHeader( rowHeader );
    }

    public void setRowHeaderView( Component view )
    {
        scroll.setRowHeaderView( view );
    }

    public void setVerticalScrollBar( JScrollBar verticalScrollBar )
    {
        scroll.setVerticalScrollBar( verticalScrollBar );
    }

    public void setVerticalScrollBarPolicy( int policy )
    {
        scroll.setVerticalScrollBarPolicy( policy );
    }

    public void setViewport( JViewport viewport )
    {
        scroll.setViewport( viewport );
    }

    public void setViewportBorder( Border viewportBorder )
    {
        scroll.setViewportBorder( viewportBorder );
    }

    public void setViewportView( Component view )
    {
        scroll.setViewportView( view );
    }

    public void setWheelScrollingEnabled( boolean handleWheel )
    {
        scroll.setWheelScrollingEnabled( handleWheel );
    }

    public void setBackground( Color background )
    {
        scroll.getViewport().setBackground( background );
    }

    public boolean isBackgroundSet()
    {
        return scroll.getViewport().isBackgroundSet();
    }

    public Color getBackground()
    {
        return scroll.getViewport().getBackground();
    }

    public void ensureVisible( Rectangle bounds )
    {
        JViewport viewPort = getViewport();
        Rectangle viewRect = viewPort.getViewRect();
        Point viewPosition = viewPort.getViewPosition();
        int viewTop = viewRect.y;
        int viewBottom = viewRect.y + viewRect.height;
        int boundsTop = bounds.y;
        int boundsBottom = bounds.y + bounds.height;
        int viewLeft = viewRect.x;
        int viewRight = viewRect.x + viewRect.width;
        int boundsLeft = bounds.x;
        int boundsRight = bounds.x + bounds.width;
        if ( boundsTop < viewTop )
        {
            viewPosition.y = boundsTop;
        }
        else if ( boundsBottom > viewBottom )
        {
            viewPosition.y += ( boundsBottom - viewBottom );
        }
        if ( boundsLeft < viewLeft )
        {
            viewPosition.x = boundsLeft;
        }
        else if ( boundsRight > viewRight )
        {
            viewPosition.x += ( boundsRight - viewRight );
        }
        viewPort.setViewPosition( viewPosition );
    }
}

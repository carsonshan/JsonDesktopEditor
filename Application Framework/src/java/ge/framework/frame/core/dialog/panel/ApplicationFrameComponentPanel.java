package ge.framework.frame.core.dialog.panel;

import com.jidesoft.list.StyledListCellRenderer;
import com.jidesoft.navigation.NavigationList;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.framework.frame.core.ApplicationFrameComponent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 27/02/13
 * Time: 12:06
 */
public abstract class ApplicationFrameComponentPanel extends JPanel
{
    private NavigationList list;

    private DefaultListModel tableModel;

    public ApplicationFrameComponentPanel()
    {
        super( new BorderLayout() );
        initialise();
    }

    private void initialise()
    {
        tableModel = new DefaultListModel();

        list = new NavigationList( tableModel );
        list.setCellRenderer( new FramesListRenderer() );
        list.setSelectionMode( ListSelectionModel.SINGLE_INTERVAL_SELECTION );

        JideScrollPane scrollPane = new JideScrollPane( list );

        scrollPane.setBorder( new PartialLineBorder( Color.GRAY, 1 ) );

        StyledLabel titleLabel = new StyledLabel( getTitleLabel() );

        titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
        titleLabel.setVerticalAlignment( SwingConstants.CENTER );
        Font font = new Font( titleLabel.getFont().getFontName(), Font.BOLD, titleLabel.getFont().getSize() + 4 );
        titleLabel.setFont( font );
        titleLabel.setBackground( Color.LIGHT_GRAY );
        titleLabel.setOpaque( true );
        titleLabel.setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.VERTICAL | PartialSide.NORTH ) );

        add( BorderLayout.NORTH, titleLabel );
        add( BorderLayout.CENTER, scrollPane );

        setSize( new Dimension( 300, 200 ) );
        setMinimumSize( new Dimension( 300, 200 ) );
        setPreferredSize( new Dimension( 300, 200 ) );
        setBorder( new EmptyBorder( 10, 10, 10, 5 ) );
    }

    protected abstract String getTitleLabel();

    public void addListSelectionListener( ListSelectionListener listener )
    {
        list.addListSelectionListener( listener );
    }

    public void removeListSelectionListener( ListSelectionListener listener )
    {
        list.removeListSelectionListener( listener );
    }

    public void clearSelection()
    {
        list.clearSelection();
    }

    public ApplicationFrameComponent getSelectedValue()
    {
        return ( ApplicationFrameComponent ) list.getSelectedValue();
    }

    public NavigationList getList()
    {
        return list;
    }

    public void setApplicationFrameComponents(
            List<ApplicationFrameComponent> applicationFrameComponents )
    {
        tableModel.removeAllElements();

        for ( ApplicationFrameComponent applicationFrameComponent : applicationFrameComponents )
        {
            tableModel.addElement( applicationFrameComponent );
        }

    }

    private class FramesListRenderer extends StyledListCellRenderer
    {
        @Override
        protected void customizeStyledLabel( JList table, Object value, int index, boolean isSelected,
                                             boolean hasFocus )
        {
            if ( value instanceof ApplicationFrameComponent )
            {
                ApplicationFrameComponent document = ( ApplicationFrameComponent ) value;

                String label = document.getMenuTitle();

                super.customizeStyledLabel( table, label, index, isSelected, hasFocus );

                setIcon( document.getMenuIcon() );
            }
            else
            {
                super.customizeStyledLabel( table, value, index, isSelected, hasFocus );
            }
        }
    }
}

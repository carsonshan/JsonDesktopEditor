package ge.utils.properties;

import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.jidesoft.swing.StyledLabel;
import ge.utils.problem.object.Problem;
import ge.utils.properties.event.PropertyValueChangedEvent;
import ge.utils.properties.event.PropertyValueChangedListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 18/02/13
 * Time: 11:50
 */
public abstract class PropertiesDialogPage<PROPERTIES extends Object> extends JPanel
{
    private String id;

    private boolean setup = false;

    private EventListenerList eventListenerList = new EventListenerList();

    protected PropertiesDialogPage( String id )
    {
        this.id = id;
    }

    public abstract String getPageTitle();

    public abstract Icon getPageIcon();

    public final void setupPage( PROPERTIES properties )
    {
        if ( setup == false )
        {
            setLayout( new BorderLayout() );

            StyledLabel titleLabel = new StyledLabel( getPageTitle() );
            titleLabel.setHorizontalAlignment( SwingConstants.LEADING );
            titleLabel.setVerticalAlignment( SwingConstants.CENTER );
            Font font = new Font( titleLabel.getFont().getFontName(), Font.BOLD, titleLabel.getFont().getSize() + 4 );
            titleLabel.setFont( font );
            titleLabel.setBackground( Color.LIGHT_GRAY );
            titleLabel.setOpaque( true );
            titleLabel.setBorder( BorderFactory.createCompoundBorder(
                    new PartialLineBorder( Color.GRAY, 1, PartialSide.SOUTH ), new EmptyBorder( 0, 5, 0, 0 ) ) );

            setBorder( new PartialLineBorder( Color.GRAY, 1, PartialSide.ALL ) );

            JComponent contentPanel = createContentPanel();

            JScrollPane componentPanel = new JScrollPane( contentPanel );
            componentPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

            add( BorderLayout.NORTH, titleLabel );
            add( BorderLayout.CENTER, componentPanel );

            setup = true;

            setCurrentValues( properties );
        }
    }

    protected abstract JComponent createContentPanel();

    public abstract void setCurrentValues( PROPERTIES properties );

    public abstract List<Problem> validateProperties();

    public abstract void updateProperties( PROPERTIES properties );

    public String getId()
    {
        return id;
    }

    public boolean isSetup()
    {
        return setup;
    }

    public void addPropertyValueChangedListener( PropertyValueChangedListener listener )
    {
        eventListenerList.add( PropertyValueChangedListener.class, listener );
    }

    public void removePropertyValueChangedListener( PropertyValueChangedListener listener )
    {
        eventListenerList.remove( PropertyValueChangedListener.class, listener );
    }

    protected void firePropertyValueChangedEvent()
    {
        PropertyValueChangedListener[] listeners = eventListenerList.getListeners( PropertyValueChangedListener.class );

        for ( PropertyValueChangedListener listener : listeners )
        {
            PropertyValueChangedEvent event = new PropertyValueChangedEvent( this );
            listener.propertyChanged( event );
        }
    }
}

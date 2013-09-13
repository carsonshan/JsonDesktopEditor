package ge.framework.application.core.objects;

import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.application.multi.objects.events.ApplicationConfigurationChangeListener;
import ge.framework.application.multi.objects.events.ApplicationConfigurationChangedEvent;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.properties.object.PropertyDialogObject;

import javax.swing.event.EventListenerList;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 29/01/13
 * Time: 09:18
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class ApplicationConfiguration extends PropertyDialogObject
{
    private transient EventListenerList eventListenerList = new EventListenerList();

    private boolean askBeforeExit = true;

    public boolean isAskBeforeExit()
    {
        return askBeforeExit;
    }

    public void setAskBeforeExit( boolean askBeforeExit )
    {
        this.askBeforeExit = askBeforeExit;
    }

    public void addApplicationConfigurationChangeListener( ApplicationConfigurationChangeListener listener )
    {
        eventListenerList.add( ApplicationConfigurationChangeListener.class, listener );
    }

    public void removeApplicationConfigurationChangeListener( ApplicationConfigurationChangeListener listener )
    {
        eventListenerList.add( ApplicationConfigurationChangeListener.class, listener );
    }

    public void fireApplicationConfigurationChangedEvent( String propertyName )
    {
        ApplicationConfigurationChangeListener[] listeners =
                eventListenerList.getListeners( ApplicationConfigurationChangeListener.class );

        for ( ApplicationConfigurationChangeListener listener : listeners )
        {
            ApplicationConfigurationChangedEvent event = new ApplicationConfigurationChangedEvent( this, propertyName );
            listener.applicationConfigurationChanged( event );
        }
    }
}

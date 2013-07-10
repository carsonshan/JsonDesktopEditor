package ge.utils.properties.event;

import java.util.EventListener;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 15:13
 */
public interface PropertyValueChangedListener extends EventListener
{
    public void propertyChanged( PropertyValueChangedEvent event );
}

package ge.utils.properties.event;

import ge.utils.properties.PropertiesDialogPage;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 15:14
 */
public class PropertyValueChangedEvent
{
    private final String property;

    private final PropertiesDialogPage page;

    public PropertyValueChangedEvent()
    {
        page = null;
        property = null;
    }

    public PropertyValueChangedEvent( PropertiesDialogPage page )
    {
        this.page = page;
        property = null;
    }

    public PropertyValueChangedEvent( String property, PropertiesDialogPage page )
    {
        this.property = property;
        this.page = page;
    }

    public PropertiesDialogPage getPage()
    {
        return page;
    }

    public String getProperty()
    {
        return property;
    }
}

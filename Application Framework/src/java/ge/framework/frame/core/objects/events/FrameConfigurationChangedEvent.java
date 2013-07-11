package ge.framework.frame.core.objects.events;


import ge.framework.frame.core.objects.FrameConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 21/02/13
 * Time: 17:56
 */
public class FrameConfigurationChangedEvent
{
    private final FrameConfiguration frameConfiguration;

    private final String propertyName;

    public FrameConfigurationChangedEvent( FrameConfiguration frameConfiguration,
                                           String propertyName )
    {
        this.frameConfiguration = frameConfiguration;
        this.propertyName = propertyName;
    }

    public FrameConfiguration getFrameConfiguration()
    {
        return frameConfiguration;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

}

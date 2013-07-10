package ge.utils.xml.bind;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/03/13
 * Time: 17:41
 */
public interface UnmarshallerListener<TARGET_TYPE>
{
    public void afterUnmarshal( TARGET_TYPE target, Object parent );

    public void beforeUnmarshal( TARGET_TYPE target, Object parent );
}

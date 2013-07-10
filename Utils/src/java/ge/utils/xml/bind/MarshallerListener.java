package ge.utils.xml.bind;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/03/13
 * Time: 17:42
 */
public interface MarshallerListener<TYPE extends Object>
{
    public void afterMarshal( TYPE source );

    public void beforeMarshal( TYPE source );
}

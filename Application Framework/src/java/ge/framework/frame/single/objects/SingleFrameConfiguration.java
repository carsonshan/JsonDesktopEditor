package ge.framework.frame.single.objects;

import ge.framework.frame.core.objects.FrameConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 10:02
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class SingleFrameConfiguration extends FrameConfiguration
{
    private boolean askBeforeExit = true;

    public SingleFrameConfiguration()
    {
    }

    public SingleFrameConfiguration( String name )
    {
        super( name );
    }

    public void setAskBeforeExit( boolean askBeforeExit )
    {
        this.askBeforeExit = askBeforeExit;
    }

    public boolean isAskBeforeExit()
    {
        return askBeforeExit;
    }
}

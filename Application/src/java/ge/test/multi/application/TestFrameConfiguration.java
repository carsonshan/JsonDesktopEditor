package ge.test.multi.application;

import ge.framework.frame.multi.objects.MultiFrameConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/07/13
 * Time: 15:42
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class TestFrameConfiguration extends MultiFrameConfiguration
{
    public TestFrameConfiguration()
    {
    }

    public TestFrameConfiguration( String name )
    {
        super( name );
    }
}

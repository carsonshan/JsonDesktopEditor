package ge.framework.frame.single.objects;

import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.single.SingleApplicationFrame;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 09:59
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class SingleFrameDefinition<FRAME extends SingleApplicationFrame, CONFIG extends SingleFrameConfiguration> extends FrameDefinition<FRAME,CONFIG>
{
}

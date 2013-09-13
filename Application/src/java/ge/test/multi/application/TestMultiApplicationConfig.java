package ge.test.multi.application;

import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.multi.objects.MultiApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameDefinition;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/07/13
 * Time: 15:39
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class TestMultiApplicationConfig extends MultiApplicationConfiguration
{
}

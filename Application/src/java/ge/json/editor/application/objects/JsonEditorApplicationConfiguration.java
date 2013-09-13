package ge.json.editor.application.objects;

import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.single.objects.SingleApplicationConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/07/13
 * Time: 14:06
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class JsonEditorApplicationConfiguration extends SingleApplicationConfiguration
{
}

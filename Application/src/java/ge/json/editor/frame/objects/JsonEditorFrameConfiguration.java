package ge.json.editor.frame.objects;

import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.single.objects.SingleFrameConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 11/07/13
 * Time: 09:18
 */
@XmlRootElement
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public class JsonEditorFrameConfiguration extends SingleFrameConfiguration
{
    public JsonEditorFrameConfiguration()
    {
    }

    public JsonEditorFrameConfiguration( String name )
    {
        super( name );
    }
}

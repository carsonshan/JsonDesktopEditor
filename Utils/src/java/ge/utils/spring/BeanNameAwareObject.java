package ge.utils.spring;

import org.springframework.beans.factory.BeanNameAware;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import static org.springframework.util.StringUtils.hasLength;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class BeanNameAwareObject extends BeanObject implements BeanNameAware
{
    @XmlAttribute
    private String beanName;

    @Override
    public final void setBeanName( String beanName )
    {
        testInitialised();
        hasLength( beanName );
        this.beanName = beanName;
    }

    public final String getBeanName()
    {
        return beanName;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof BeanNameAwareObject ) )
        {
            return false;
        }

        BeanNameAwareObject that = ( BeanNameAwareObject ) o;

        if ( beanName != null ? !beanName.equals( that.beanName ) : that.beanName != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return beanName != null ? beanName.hashCode() : 0;
    }
}

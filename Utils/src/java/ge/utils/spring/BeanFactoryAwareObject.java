package ge.utils.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

import static org.springframework.util.Assert.notNull;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class BeanFactoryAwareObject extends BeanNameAwareObject implements BeanFactoryAware
{
    private transient BeanFactory beanFactory;

    @Override
    public final void setBeanFactory( BeanFactory beanFactory ) throws
                                                                BeansException
    {
        testInitialised();
        notNull( beanFactory );
        this.beanFactory = beanFactory;
    }

    public final BeanFactory getBeanFactory()
    {
        return beanFactory;
    }

    public final <TYPE extends BeanObject> TYPE getBean( String beanName, Class<TYPE> typeClass ) throws
                                                                                                  BeansException
    {
        return beanFactory.getBean( beanName, typeClass );
    }

    public final Object getBean( String beanName ) throws
                                                   BeansException
    {
        return beanFactory.getBean( beanName );
    }
}

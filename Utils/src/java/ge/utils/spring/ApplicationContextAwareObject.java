package ge.utils.spring;

import ge.utils.spring.context.ClasspathApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 14/03/13
 * Time: 14:49
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class ApplicationContextAwareObject extends BeanNameAwareObject implements ApplicationContextAware
{
    private transient ApplicationContext applicationContext;

    @XmlAttribute
    private String configLocation;

    public ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }

    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException
    {
        testInitialised();
        notNull( applicationContext );
        this.applicationContext = applicationContext;

        if ( applicationContext instanceof ClasspathApplicationContext )
        {
            ClasspathApplicationContext classpathApplicationContext =
                    ( ClasspathApplicationContext ) applicationContext;
            configLocation = classpathApplicationContext.getConfigLocation();
        }
    }

    public String getConfigLocation()
    {
        return configLocation;
    }

    public final <TYPE extends BeanObject> TYPE getBean( String beanName, Class<TYPE> typeClass ) throws
                                                                                                  BeansException
    {
        return applicationContext.getBean( beanName, typeClass );
    }

    public final Object getBean( String beanName ) throws
                                                   BeansException
    {
        return applicationContext.getBean( beanName );
    }
}

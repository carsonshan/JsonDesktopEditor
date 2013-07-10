package ge.utils.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ConfigurableClassPathXmlApplicationContext extends GenericXmlApplicationContext
{
    private final String configLocation;

    public ConfigurableClassPathXmlApplicationContext( String configLocation ) throws BeansException
    {
        super();
        this.configLocation = configLocation;

        initialise();
    }

    public ConfigurableClassPathXmlApplicationContext( String configLocation, ClassLoader classLoader )
            throws BeansException
    {
        super();

        this.configLocation = configLocation;

        setClassLoader( classLoader );

        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        beanFactory.setBeanClassLoader( classLoader );

        initialise();
    }

    private void initialise()
    {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        Map<Class<?>, Class<? extends PropertyEditor>> editors = createCustomEditors();

        if ( ( editors != null ) && ( editors.isEmpty() == false ) )
        {
            for ( Entry<Class<?>, Class<? extends PropertyEditor>> editorEntry : editors.entrySet() )
            {
                beanFactory.registerCustomEditor( editorEntry.getKey(), editorEntry.getValue() );
            }
        }

        load( configLocation );

        refresh();
    }

    public String getConfigLocation()
    {
        return configLocation;
    }

    public abstract Map<Class<?>, Class<? extends PropertyEditor>> createCustomEditors();
}

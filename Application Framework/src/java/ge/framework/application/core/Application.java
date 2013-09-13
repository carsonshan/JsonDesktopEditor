package ge.framework.application.core;

import ge.framework.application.core.dialog.ApplicationPropertiesDialog;
import ge.framework.application.core.dialog.ExitDialog;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.frame.core.ApplicationFrame;
import ge.utils.message.enums.MessageResult;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.spring.ApplicationContextAwareObject;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.Frame;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/07/13
 * Time: 16:19
 */
public abstract class Application<CONFIG extends ApplicationConfiguration> extends ApplicationContextAwareObject
{
    private static Logger logger = Logger.getLogger( Application.class );

    private static File userDirectory = new File( System.getProperty( "user.home" ) );

    protected Class<? extends ApplicationConfiguration> applicationConfigurationClass;

    protected String applicationMetaDataName;

    protected String applicationConfigurationName;

    protected CONFIG configuration;

    private TypedUnmarshallerListener unmarshallerListener;

    private TypedMarshallerListener marshallerListener;

    private Boolean allowMultipleApplications;

    private boolean resetting;

    private String name;

    public final Boolean isAllowMultipleApplications()
    {
        return allowMultipleApplications;
    }

    public final void setAllowMultipleApplications( Boolean allowMultipleApplications ) throws IllegalAccessException
    {
        testInitialised();
        this.allowMultipleApplications = allowMultipleApplications;
    }

    public final void startup( String[] args )
    {
        logger.debug( "Starting application" );

        unmarshallerListener = new TypedUnmarshallerListener();
        marshallerListener = new TypedMarshallerListener();

        initialiseApplication( args );

        loadApplicationConfiguration();

        initialiseApplicationConfiguration();

        startupApplication();
    }

    protected abstract void initialiseApplication( String[] args );

    protected abstract void initialiseApplicationConfiguration();

    protected abstract void startupApplication();

    public boolean processExit()
    {
        logger.debug( "Processing Exit" );
        if ( isExitProcessRequired() == true )
        {
            System.exit( 0 );
            return true;
        }
        else
        {
            if ( resetting == false )
            {
                if ( isAskBeforeExit() == true )
                {
                    ExitDialog exitDialog = new ExitDialog( getFocusedFrame() );

                    MessageResult messageResult = exitDialog.doModal();

                    setAskBeforeExit( !exitDialog.isCheckBoxSelected() );

                    if ( messageResult == MessageResult.CANCEL )
                    {
                        return false;
                    }
                }
            }

            logger.debug( "Closing frames." );

            closeAllFrames();

            updateApplication();

            return true;
        }
    }

    protected abstract void updateApplication();

    protected abstract ApplicationFrame getFocusedFrame();

    protected abstract void closeAllFrames();

    public boolean isAskBeforeExit()
    {
        return configuration.isAskBeforeExit();
    }

    public void setAskBeforeExit( boolean askBeforeExit )
    {
        configuration.setAskBeforeExit( askBeforeExit );
    }

    protected abstract boolean isExitProcessRequired();

    public abstract CloseOrExitEnum closeOrExit();

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    protected final void validateBeanObject()
    {
        notNull( applicationConfigurationClass );
        notNull( applicationConfigurationName );
        notNull( applicationMetaDataName );
        notNull( name );
        notNull( allowMultipleApplications );

        validateApplicationObject();
    }

    protected abstract void validateApplicationObject();

    private void loadApplicationConfiguration()
    {
        File metadataDirectory = new File( userDirectory, applicationMetaDataName );
        File configFile = new File( metadataDirectory, applicationConfigurationName );

        logger.trace( "Loading ApplicationConfiguration from: " + configFile.toString() );

        try
        {
            if ( configFile.exists() == false )
            {
                logger.trace( "Failed to find config file: " + configFile.toString() );

                Constructor<CONFIG> constructor =
                        ( Constructor<CONFIG> ) applicationConfigurationClass.getConstructor();

                configuration = constructor.newInstance();

                saveApplicationConfiguration();
            }
            else
            {
                JAXBContext requestContext = JAXBContext.newInstance( applicationConfigurationClass );

                Unmarshaller unmarshaller = requestContext.createUnmarshaller();
                unmarshaller.setListener( unmarshallerListener );

                logger.trace( "Found config file: " + configFile.toString() );
                configuration = ( CONFIG ) unmarshaller.unmarshal( configFile );
            }
        }
        catch ( JAXBException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( NoSuchMethodException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InvocationTargetException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( InstantiationException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( IllegalAccessException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected void saveApplicationConfiguration()
    {
        File metadataDirectory = new File( userDirectory, applicationMetaDataName );
        File configFile = new File( metadataDirectory, applicationConfigurationName );

        logger.trace( "Saving ApplicationConfiguration to: " + configFile.toString() );

        try
        {
            if ( configFile.exists() == false )
            {
                File parentFile = configFile.getParentFile();

                if ( ( parentFile.exists() == false ) || ( parentFile.isDirectory() == false ) )
                {
                    parentFile.mkdirs();
                }
            }

            JAXBContext requestContext = JAXBContext.newInstance( applicationConfigurationClass );

            Marshaller marshaller = requestContext.createMarshaller();
            marshaller.setListener( marshallerListener );

            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

            FileOutputStream fos = new FileOutputStream( configFile );

            marshaller.marshal( configuration, fos );
        }
        catch ( JAXBException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
        catch ( FileNotFoundException e )
        {
            logger.fatal( e.getMessage(), e );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    public abstract List<PropertiesDialogPage<? extends ApplicationConfiguration>> getApplicationConfigurationPages();

    public UnmarshallerListener getUnmarshallerListener( Class aClass )
    {
        return unmarshallerListener.getListener( aClass );
    }

    public void setUnmarshallerListener( Class aClass, UnmarshallerListener listener )
    {
        unmarshallerListener.setListener( aClass, listener );
    }

    public MarshallerListener getMarshallerListener( Class aClass )
    {
        return marshallerListener.getListener( aClass );
    }

    public void setMarshallerListener( Class aClass, MarshallerListener listener )
    {
        marshallerListener.setListener( aClass, listener );
    }

    public void processApplicationProperties()
    {
        ApplicationPropertiesDialog dialog = new ApplicationPropertiesDialog( this );

        dialog.doModal();

        saveApplicationConfiguration();
    }

    public ApplicationConfiguration getConfiguration()
    {
        return configuration;
    }

    public Class<? extends ApplicationConfiguration> getApplicationConfigurationClass()
    {
        return applicationConfigurationClass;
    }

    public void setApplicationConfigurationClass(
            Class<? extends ApplicationConfiguration> applicationConfigurationClass )
    {
        testInitialised();
        this.applicationConfigurationClass = applicationConfigurationClass;
    }

    public String getApplicationConfigurationName()
    {
        return applicationConfigurationName;
    }

    public void setApplicationConfigurationName( String applicationConfigurationName )
    {
        testInitialised();
        this.applicationConfigurationName = applicationConfigurationName;
    }

    public String getApplicationMetaDataName()
    {
        return applicationMetaDataName;
    }

    public void setApplicationMetaDataName( String applicationMetaDataName )
    {
        testInitialised();
        this.applicationMetaDataName = applicationMetaDataName;
    }

    public abstract Frame discoverFocusedFrame();

    public abstract Icon getLargeIcon();

    public abstract Image getSmallImage();
}

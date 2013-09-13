package ge.framework.frame.multi;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.menu.FileMenu;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.multi.command.FileCommandBar;
import ge.framework.frame.multi.menu.MultiFrameFileMenu;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.bundle.Resources;
import ge.utils.file.LockFile;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;
import ge.utils.xml.bind.MarshallerListener;
import ge.utils.xml.bind.TypedMarshallerListener;
import ge.utils.xml.bind.TypedUnmarshallerListener;
import ge.utils.xml.bind.UnmarshallerListener;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/07/13
 * Time: 16:00
 */
public abstract class MultiApplicationFrame<APPLICATION extends MultiFrameApplication,
        CONFIG extends FrameConfiguration> extends
                                                ApplicationFrame<APPLICATION,
                                                        MultiFrameDefinition>
{
    private static Logger logger = Logger.getLogger( MultiApplicationFrame.class );
    private static final Resources resources =
            Resources.getInstance( "ge.framework.frame.resources" );

    protected CONFIG frameConfiguration;

    private LockFile lockFile;

    private TypedMarshallerListener marshallerListener = new TypedMarshallerListener();

    private TypedUnmarshallerListener unmarshallerListener = new TypedUnmarshallerListener();

    private FrameInstanceDetailsObject frameInstanceDetailsObject;

    private FileCommandBar fileCommandBar;

    public MultiApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        super( application );
    }

    @Override
    protected void initialiseApplicationFrame()
    {
        fileCommandBar = new FileCommandBar( this );
        
        initialiseMultiApplicationFrame();
    }

    protected abstract void initialiseMultiApplicationFrame();

    public CONFIG getFrameConfiguration()
    {
        return frameConfiguration;
    }

    public Class<? extends CONFIG> getFrameConfigurationClass()
    {
        return frameDefinition.getConfigurationClass();
    }

    protected boolean loadFrameConfiguration( String name )
    {
        File configFile = getConfigurationFile();

        logger.trace( "Loading FrameConfiguration from: " + configFile.toString() );

        try
        {
            Class<? extends CONFIG> frameConfigurationClass = frameDefinition.getConfigurationClass();

            boolean retVal;
            if ( configFile.exists() == false )
            {
                logger.trace( "Failed to find config file: " + configFile.toString() );

                Constructor<? extends CONFIG> constructor = frameConfigurationClass.getConstructor( String.class );

                frameConfiguration = constructor.newInstance( name );

                saveFrameConfiguration();

                retVal = false;
            }
            else
            {
                JAXBContext requestContext = JAXBContext.newInstance( frameConfigurationClass );

                Unmarshaller unmarshaller = requestContext.createUnmarshaller();
                unmarshaller.setListener( unmarshallerListener );

                logger.trace( "Found config file: " + configFile.toString() );

                frameConfiguration = ( CONFIG ) unmarshaller.unmarshal( configFile );

                retVal = true;
            }

            lockFile = new LockFile( configFile );
            lockFile.lockFile();

            return retVal;
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
        catch ( IOException e )
        {
            logger.fatal( e.getMessage() );
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected File getConfigurationFile()
    {
        return frameInstanceDetailsObject.getConfigurationFile();
    }

    public final void saveFrameConfiguration()
    {
        File configFile = getConfigurationFile();

        logger.trace( "Saving FrameConfiguration to: " + configFile.toString() );

        Class<? extends CONFIG> frameConfigurationClass = frameDefinition.getConfigurationClass();

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

            JAXBContext requestContext = JAXBContext.newInstance( frameConfigurationClass );

            Marshaller marshaller = requestContext.createMarshaller();
            marshaller.setListener( marshallerListener );

            marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );

            FileOutputStream fos = new FileOutputStream( configFile );

            marshaller.marshal( frameConfiguration, fos );
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

    @SuppressWarnings( "unchecked" )
    public final boolean shouldCreateFrameConfigurationMenu()
    {
        if ( isFrameConfigurationDialogAllow() == true )
        {
            Class clazz = getFrameConfigurationClass();

            while ( clazz != FrameConfiguration.class )
            {
                Field[] fields = clazz.getDeclaredFields();

                if ( ( fields != null ) && ( fields.length != 0 ) )
                {
                    return true;
                }

                clazz = clazz.getSuperclass();
            }

            return false;
        }
        else
        {
            return false;
        }
    }

    protected abstract boolean isFrameConfigurationDialogAllow();

    @Override
    protected FileMenu createFileMenu()
    {
        return new MultiFrameFileMenu( this );
    }

    public void open( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        this.frameInstanceDetailsObject = frameInstanceDetailsObject;

        boolean newConfig = loadFrameConfiguration( frameInstanceDetailsObject.getName() );
        loadFrameData();

        setLayoutDirectory( frameInstanceDetailsObject.getMetadataDirectory() );

        if ( newConfig == false )
        {
            resetToDefault();
            saveLayoutData();
        }
        else
        {
            loadLayoutData();

            setStatusBarVisible( frameConfiguration.isStatusBarVisible() );
            setAutoHideAreaVisible( frameConfiguration.isToolButtonsVisible() );
        }
    }

    public FileCommandBar getFileCommandBar()
    {
        return fileCommandBar;
    }

    @Override
    public void setTitle( String title )
    {
        String resourceString;
        if ( title == null )
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "title" );
        }
        else
        {
            resourceString = resources.getResourceString( MultiApplicationFrame.class, "frame", "exTitle" );
        }

        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put( "applicationName", application.getName() );
        arguments.put( "frameName", frameDefinition.getName() );
        arguments.put( "title", title );

        resourceString = StringArgumentMessageFormat.format( resourceString, arguments );

        super.setTitle( resourceString );
    }

    @Override
    protected void saveApplicationFrame()
    {
        frameConfiguration.setStatusBarVisible( isStatusBarVisible() );
        frameConfiguration.setToolButtonsVisible( isAutoHideAreaVisible() );

        saveFrameConfiguration();
    }

    protected final void processWindowClose()
    {
        saveFrame();
        try
        {
            lockFile.release();
        }
        catch ( IOException ignored )
        {
        }
    }

    public abstract List<PropertiesDialogPage> getFrameConfigurationPages();

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
}

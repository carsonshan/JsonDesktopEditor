package ge.framework.application.multi;

import ge.framework.application.core.Application;
import ge.framework.application.core.enums.CloseOrExitEnum;
import ge.framework.application.core.dialog.ApplicationPropertiesDialog;
import ge.framework.application.multi.dialog.InitialDialog;
import ge.framework.application.multi.dialog.LockedLocationDialog;
import ge.framework.application.multi.dialog.MissingLocationDialog;
import ge.framework.application.multi.dialog.NewDialog;
import ge.framework.application.multi.dialog.OpenDialog;
import ge.framework.application.multi.dialog.OpenLocationDialog;
import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.core.dialog.properties.AbstractApplicationPropertiesPage;
import ge.framework.application.multi.dialog.properties.GeneralMultiApplicationPropertiesPage;
import ge.framework.application.multi.objects.MultiApplicationConfiguration;
import ge.framework.application.multi.objects.enums.OpenLocationEnum;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.multi.MultiApplicationFrame;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.utils.os.OS;
import ge.utils.properties.PropertiesDialogPage;
import org.apache.log4j.Logger;

import javax.swing.Icon;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/07/13
 * Time: 15:45
 */
public abstract class MultiFrameApplication<CONFIG extends MultiApplicationConfiguration> extends Application<CONFIG>
{
    private static Logger logger = Logger.getLogger( Application.class );

    private List<MultiFrameDefinition> frameDefinitions;

    private Map<String, MultiFrameDefinition> frameDefinitionsMap;

    private String description;

    private Image smallImage;

    private Icon smallIcon;

    private Image largeImage;

    private Icon largeIcon;

    private String macIcon;

    private String macImage;

    private ApplicationWindowAdapter applicationWindowAdapter = new ApplicationWindowAdapter( this );

    @Override
    protected void validateApplicationObject()
    {
        notEmpty( frameDefinitions );
        notNull( applicationConfigurationClass );
        notNull( applicationMetaDataName );
        notNull( applicationConfigurationName );
        notNull( smallImage );
        notNull( smallIcon );
        notNull( largeImage );
        notNull( largeIcon );

        if ( OS.isMac() == true )
        {
            notNull( macIcon );
            notNull( macImage );
        }

        frameDefinitionsMap = new HashMap<String, MultiFrameDefinition>();

        for ( MultiFrameDefinition frameDefinition : frameDefinitions )
        {
            frameDefinitionsMap.put( frameDefinition.getBeanName(), frameDefinition );
        }
    }

    private static Map<FrameInstanceDetailsObject, MultiApplicationFrame> frames =
            new HashMap<FrameInstanceDetailsObject, MultiApplicationFrame>();

    @Override
    protected void startupApplication()
    {
        List<FrameInstanceDetailsObject> open = configuration.getOpen();

        if ( ( configuration.isReOpenLast() == true ) && ( open != null ) )
        {
            for ( FrameInstanceDetailsObject frameInstanceDetailsObject : open )
            {
                openFrame( frameInstanceDetailsObject, true );
            }

            if ( frames.isEmpty() == true )
            {
                showInitialDialog();
            }
        }
        else
        {
            showInitialDialog();
        }
    }

    private void showInitialDialog()
    {
        InitialDialog initialDialog = new InitialDialog( this );

        initialDialog.doModal();

        saveApplicationConfiguration();
    }

    @Override
    protected void updateApplication()
    {
        saveApplicationConfiguration();
    }

    @Override
    protected ApplicationFrame getFocusedFrame()
    {
        ApplicationFrame applicationFrame = null;

        for ( ApplicationFrame frame : frames.values() )
        {
            if ( frame.isFocused() == true )
            {
                applicationFrame = frame;
                break;
            }
        }

        return applicationFrame;
    }

    @Override
    protected void closeAllFrames()
    {
        logger.debug( "Closing frames." );

        for ( ApplicationFrame frame : frames.values() )
        {
            frame.removeWindowListener( applicationWindowAdapter );
            frame.close();
        }
    }

    @Override
    public boolean isAskBeforeExit()
    {
        return configuration.isAskBeforeExit();
    }

    @Override
    public void setAskBeforeExit( boolean askBeforeExit )
    {
        configuration.setAskBeforeExit( askBeforeExit );
    }

    @Override
    protected boolean isExitProcessRequired()
    {
        return frames.isEmpty();
    }

    @Override
    public CloseOrExitEnum closeOrExit()
    {
        if ( ( frames.size() == 1 ) && ( configuration.isExitOnFinalWindowClose() == true ) )
        {
            return CloseOrExitEnum.EXIT;
        }
        else
        {
            return CloseOrExitEnum.CLOSE;
        }
    }

    protected final void initialiseApplicationConfiguration()
    {
        configuration.initialiseDetails( frameDefinitionsMap );

        initialiseMultiFrameApplicationConfiguration();
    }

    protected abstract void initialiseMultiFrameApplicationConfiguration();

    public final List<PropertiesDialogPage<? extends ApplicationConfiguration>> getApplicationConfigurationPages()
    {
        List<PropertiesDialogPage<? extends ApplicationConfiguration>> retVal = new ArrayList<PropertiesDialogPage<? extends ApplicationConfiguration>>(  );

        retVal.add( new GeneralMultiApplicationPropertiesPage() );

        List<AbstractApplicationPropertiesPage> multiApplicationConfigurationPages =
                getMultiApplicationConfigurationPages();

        if (( multiApplicationConfigurationPages != null ) && ( multiApplicationConfigurationPages.isEmpty() == false ))
        {
            retVal.addAll( multiApplicationConfigurationPages );
        }

        return retVal;
    }

    public abstract List<AbstractApplicationPropertiesPage> getMultiApplicationConfigurationPages();


    public boolean openFrame( FrameInstanceDetailsObject frameInstanceDetailsObject,
                              boolean requiresValidation )
    {
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        return openFrame( applicationFrame, frameInstanceDetailsObject, requiresValidation );
    }

    private boolean openFrame( ApplicationFrame applicationFrame,
                               FrameInstanceDetailsObject frameInstanceDetailsObject,
                               boolean requiresValidation )
    {
        if ( ( requiresValidation == true ) &&
                ( validateFrameInstanceDetails( frameInstanceDetailsObject ) == false ) )
        {
            return false;
        }

        OpenLocationEnum openLocation = configuration.getOpenLocation();

        if ( ( applicationFrame != null ) && ( openLocation == OpenLocationEnum.ASK ) )
        {
            OpenLocationDialog openLocationDialog = new OpenLocationDialog( this );

            openLocation = openLocationDialog.showMessage();

            if ( openLocation != null )
            {
                if ( openLocationDialog.isCheckBoxSelected() == true )
                {
                    configuration.setOpenLocation( openLocation );
                }
            }
            else
            {
                return false;
            }
        }

        if ( ( applicationFrame != null ) &&
                ( openLocation == OpenLocationEnum.EXISTING ) )
        {
            applicationFrame.removeWindowListener( applicationWindowAdapter );
            applicationFrame.close();

            removeFrame( applicationFrame );
        }

        try
        {
            MultiFrameDefinition frameDefinition = frameInstanceDetailsObject.getFrameDefinition();

            Class<? extends MultiApplicationFrame> frameClass = frameDefinition.getFrameClass();

            Constructor<? extends MultiApplicationFrame> constructor =
                    frameClass.getConstructor(MultiFrameApplication.class);

            MultiApplicationFrame newApplicationFrame = constructor.newInstance(this);

            newApplicationFrame.initialise( frameDefinition );

            newApplicationFrame.addWindowListener( applicationWindowAdapter );
            frames.put( frameInstanceDetailsObject, newApplicationFrame );

            newApplicationFrame.open( frameInstanceDetailsObject );

            FrameConfiguration frameConfiguration =
                    ( FrameConfiguration ) newApplicationFrame.getFrameConfiguration();

            frameInstanceDetailsObject.setName( frameConfiguration.getName() );

            configuration.addOpen( frameInstanceDetailsObject );
            configuration.addRecent( frameInstanceDetailsObject );

            saveApplicationConfiguration();

            return true;
        }
        catch ( Exception e )
        {
            logger.error( e.getMessage(), e );
            System.exit( -1 );
            return false;
        }
    }

    private void removeFrame( ApplicationFrame applicationFrame )
    {
        if ( frames.containsValue( applicationFrame ) == true )
        {
            Set<Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame>> entries = frames.entrySet();

            for ( Map.Entry<FrameInstanceDetailsObject, MultiApplicationFrame> entry : entries )
            {
                if ( entry.getValue() == applicationFrame )
                {
                    FrameInstanceDetailsObject key = entry.getKey();

                    configuration.removeOpen( key );
                    saveApplicationConfiguration();

                    frames.remove( key );
                    break;
                }
            }
        }
    }

    public boolean processNew()
    {
        return processNew( ( FrameDefinition ) null );
    }

    public boolean processNew( InitialDialog initialDialog )
    {
        NewDialog newDialog = new NewDialog( initialDialog, this );

        if ( newDialog.doModal() == true )
        {
            return openFrame( null, newDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processNew( FrameDefinition frameDefinition )
    {
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        NewDialog newDialog = new NewDialog( frameDefinition, this );

        if ( newDialog.doModal() == true )
        {
            return openFrame( applicationFrame, newDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processOpen( InitialDialog initialDialog )
    {
        OpenDialog openDialog = new OpenDialog( initialDialog, this );

        if ( openDialog.doModal() == true )
        {
            return openFrame( null, openDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public boolean processOpen()
    {
        ApplicationFrame applicationFrame = discoverFocusedFrame();

        OpenDialog openDialog = new OpenDialog( this );

        if ( openDialog.doModal() == true )
        {
            return openFrame( applicationFrame, openDialog.getFrameInstanceDetailsObject(), false );
        }

        return false;
    }

    public void processClose()
    {
        ApplicationFrame applicationFrame = discoverFocusedFrame();
        applicationFrame.close();
    }

    public void processApplicationProperties( InitialDialog initialDialog )
    {
        ApplicationPropertiesDialog dialog = new ApplicationPropertiesDialog( initialDialog, this );

        dialog.doModal();

        saveApplicationConfiguration();
    }

    private boolean validateFrameInstanceDetails( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {
        if ( frameInstanceDetailsObject.doesConfigurationFileExist() == false )
        {
            MissingLocationDialog missingLocationDialog =
                    new MissingLocationDialog( frameInstanceDetailsObject.getName(), this );

            return missingLocationDialog.showMessage();
        }
        else if ( frameInstanceDetailsObject.isConfigurationFileLocked() == true )
        {
            LockedLocationDialog lockedLocationDialog =
                    new LockedLocationDialog( frameInstanceDetailsObject.getName(), this );

            lockedLocationDialog.doModal();

            return false;
        }

        return true;
    }

    public String getMacImage()
    {
        return macImage;
    }

    public void setMacImage( String macImage )
    {
        testInitialised();
        this.macImage = macImage;
    }

    public String getMacIcon()
    {
        return macIcon;
    }

    public void setMacIcon( String macIcon )
    {
        testInitialised();
        this.macIcon = macIcon;
    }

    public Icon getLargeIcon()
    {
        return largeIcon;
    }

    public void setLargeIcon( Icon largeIcon )
    {
        testInitialised();
        this.largeIcon = largeIcon;
    }

    public Image getLargeImage()
    {
        return largeImage;
    }

    public void setLargeImage( Image largeImage )
    {
        testInitialised();
        this.largeImage = largeImage;
    }

    public Icon getSmallIcon()
    {
        return smallIcon;
    }

    public void setSmallIcon( Icon smallIcon )
    {
        testInitialised();
        this.smallIcon = smallIcon;
    }

    public Image getSmallImage()
    {
        return smallImage;
    }

    public void setSmallImage( Image smallImage )
    {
        testInitialised();
        this.smallImage = smallImage;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        testInitialised();
        this.description = description;
    }

    public List<MultiFrameDefinition> getFrameDefinitions()
    {
        return frameDefinitions;
    }

    public void setFrameDefinitions( List<MultiFrameDefinition> frameDefinitions )
    {
        testInitialised();
        this.frameDefinitions = frameDefinitions;
    }

    public final int getAllowedRecentlyOpenedCount()
    {
        return configuration.getAllowedRecentlyOpenedCount();
    }

    public ArrayList<FrameInstanceDetailsObject> getRecentlyOpened()
    {
        ArrayList<FrameInstanceDetailsObject> retVal = new ArrayList<FrameInstanceDetailsObject>();

        List<FrameInstanceDetailsObject> recent = configuration.getRecent();

        for ( FrameInstanceDetailsObject frameInstanceDetailsObject : recent )
        {
            if ( frames.containsKey( frameInstanceDetailsObject ) == false )
            {
                retVal.add( frameInstanceDetailsObject );
            }
        }

        return retVal;
    }

    public void clearRecent()
    {
        configuration.clearRecent();
        saveApplicationConfiguration();
    }

    public ApplicationFrame discoverFocusedFrame()
    {
        ApplicationFrame applicationFrame = null;

        for ( ApplicationFrame frame : frames.values() )
        {
            if ( frame.isFocused() == true )
            {
                applicationFrame = frame;
                break;
            }
        }

        return applicationFrame;
    }

    public static class ApplicationWindowAdapter extends WindowAdapter
    {
        MultiFrameApplication application;

        public ApplicationWindowAdapter( MultiFrameApplication application )
        {
            this.application = application;
        }

        @Override
        public final void windowClosed( WindowEvent e )
        {
            Object source = e.getSource();

            application.removeFrame( ( ApplicationFrame ) source );

            if ( frames.isEmpty() == true )
            {
                application.showInitialDialog();
            }
        }
    }
}

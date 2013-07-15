package ge.framework.application.multi.dialog;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.properties.AbstractApplicationPropertiesPage;
import ge.framework.application.multi.dialog.properties.GeneralApplicationPropertiesPage;
import ge.framework.application.multi.objects.ApplicationConfiguration;
import ge.utils.bundle.Resources;
import ge.utils.properties.PropertiesDialog;
import ge.utils.properties.PropertiesDialogPage;
import ge.utils.text.StringArgumentMessageFormat;

import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/02/13
 * Time: 18:26
 */
public class ApplicationPropertiesDialog extends PropertiesDialog<ApplicationConfiguration>
{
    private static Resources resources = Resources.getInstance(
            "ge.framework.application.resources" );

    private MultiFrameApplication application;

    public ApplicationPropertiesDialog( InitialDialog initialDialog, MultiFrameApplication application )
    {
        super( initialDialog, application.getConfiguration() );

        this.application = application;

        initializeDialog();
    }

    public ApplicationPropertiesDialog( MultiFrameApplication application )
    {
        super( application.discoverFocusedFrame(), application.getConfiguration() );

        this.application = application;

        initializeDialog();
    }

    @Override
    protected final String getBannerTitle()
    {
        String title = resources.getResourceString( ApplicationPropertiesDialog.class, "title" );

        Map<String, Object> arguments = new HashMap<String, Object>();

        arguments.put( "applicationName", application.getName() );

        title = StringArgumentMessageFormat.format( title, arguments );

        return title;
    }

    @Override
    protected final String getBannerSubTitle()
    {
        return null;
    }

    @Override
    protected final ImageIcon getBannerIcon()
    {
        return ( ImageIcon ) application.getLargeIcon();
    }

    private void initializeDialog()
    {
        setSize( 700, 500 );
        setMinimumSize( new Dimension( 700, 500 ) );

        setIconImage( application.getSmallImage() );
        setTitle( getBannerTitle() );
    }

    @Override
    protected final List<PropertiesDialogPage<ApplicationConfiguration>> getPages()
    {
        List<PropertiesDialogPage<ApplicationConfiguration>> retVal =
                new ArrayList<PropertiesDialogPage<ApplicationConfiguration>>();

        retVal.add( new GeneralApplicationPropertiesPage() );

        List<AbstractApplicationPropertiesPage> applicationConfigurationPages =
                application.getApplicationConfigurationPages();

        if ( ( applicationConfigurationPages != null ) && ( applicationConfigurationPages.isEmpty() == false ) )
        {
            retVal.addAll( applicationConfigurationPages );
        }

        return retVal;
    }
}

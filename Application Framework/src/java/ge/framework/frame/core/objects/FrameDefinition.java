package ge.framework.frame.core.objects;

import ge.framework.frame.core.ApplicationFrame;
import ge.utils.os.OS;
import ge.utils.spring.ApplicationContextAwareObject;

import javax.swing.Icon;
import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import java.awt.Image;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 07/03/13
 * Time: 11:18
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public abstract class FrameDefinition<FRAME extends ApplicationFrame, CONFIG extends FrameConfiguration> extends
                                                                                                         ApplicationContextAwareObject
{
    private transient String metaDataName;

    private transient String name;

    private transient Image smallImage;

    private transient Icon smallIcon;

    private transient Image largeImage;

    private transient Icon largeIcon;

    private transient Image macImage;

    private transient Icon macIcon;

    private transient Class<? extends FRAME> frameClass;

    private transient Class<? extends CONFIG> configurationClass;

    @Override
    protected void validateBeanObject()
    {
        if ( ( metaDataName == null ) || ( metaDataName.isEmpty() == true ) )
        {
            throw new IllegalStateException( "metaDataName cannot be null or empty." );
        }

        if ( ( name == null ) || ( name.isEmpty() == true ) )
        {
            throw new IllegalStateException( "name cannot be null or empty." );
        }

        if ( smallImage == null )
        {
            throw new IllegalStateException( "smallImage cannot be null" );
        }

        if ( smallIcon == null )
        {
            throw new IllegalStateException( "smallIcon cannot be null" );
        }

        if ( largeImage == null )
        {
            throw new IllegalStateException( "largeImage cannot be null" );
        }

        if ( largeIcon == null )
        {
            throw new IllegalStateException( "largeIcon cannot be null" );
        }

        if ( OS.isMac() == true )
        {
            if ( macImage == null )
            {
                throw new IllegalStateException( "macImage cannot be null" );
            }

            if ( macIcon == null )
            {
                throw new IllegalStateException( "macIcon cannot be null" );
            }
        }
    }

    public Icon getLargeIcon()
    {
        return largeIcon;
    }

    public void setLargeIcon( Icon largeIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.largeIcon = largeIcon;
    }

    public Image getLargeImage()
    {
        return largeImage;
    }

    public void setLargeImage( Image largeImage ) throws IllegalAccessException
    {
        testInitialised();
        this.largeImage = largeImage;
    }

    public Icon getMacIcon()
    {
        return macIcon;
    }

    public void setMacIcon( Icon macIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.macIcon = macIcon;
    }

    public Image getMacImage()
    {
        return macImage;
    }

    public void setMacImage( Image macImage ) throws IllegalAccessException
    {
        testInitialised();
        this.macImage = macImage;
    }

    public String getMetaDataName()
    {
        return metaDataName;
    }

    public void setMetaDataName( String metaDataName ) throws IllegalAccessException
    {
        testInitialised();
        this.metaDataName = metaDataName;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name ) throws IllegalAccessException
    {
        testInitialised();
        this.name = name;
    }

    public Icon getSmallIcon()
    {
        return smallIcon;
    }

    public void setSmallIcon( Icon smallIcon ) throws IllegalAccessException
    {
        testInitialised();
        this.smallIcon = smallIcon;
    }

    public Image getSmallImage()
    {
        return smallImage;
    }

    public void setSmallImage( Image smallImage ) throws IllegalAccessException
    {
        testInitialised();
        this.smallImage = smallImage;
    }

    public File getMetadataDirectory( File location )
    {
        return new File( location, metaDataName );
    }

    public Class<? extends FRAME> getFrameClass()
    {
        return frameClass;
    }

    public void setFrameClass( Class<? extends FRAME> frameClass )
    {
        this.frameClass = frameClass;
    }

    public Class<? extends CONFIG> getConfigurationClass()
    {
        return configurationClass;
    }

    public void setConfigurationClass( Class<? extends CONFIG> configurationClass )
    {
        this.configurationClass = configurationClass;
    }
}

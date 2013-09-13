package ge.framework.application.single.objects;

import ge.framework.application.core.objects.ApplicationConfiguration;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 17/07/13
 * Time: 15:13
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlAccessorOrder( XmlAccessOrder.ALPHABETICAL )
public abstract class SingleApplicationConfiguration extends ApplicationConfiguration
{
    private boolean statusBarVisible;

    private boolean toolButtonsVisible;

    public boolean isStatusBarVisible()
    {
        return statusBarVisible;
    }

    public void setStatusBarVisible( boolean statusBarVisible )
    {
        this.statusBarVisible = statusBarVisible;
    }

    public boolean isToolButtonsVisible()
    {
        return toolButtonsVisible;
    }

    public void setToolButtonsVisible( boolean toolButtonsVisible )
    {
        this.toolButtonsVisible = toolButtonsVisible;
    }
}

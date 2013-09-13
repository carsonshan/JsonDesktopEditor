package ge.json.editor.application;


import ge.framework.application.core.dialog.properties.AbstractApplicationPropertiesPage;
import ge.framework.application.core.objects.ApplicationConfiguration;
import ge.framework.application.single.SingleFrameApplication;
import ge.utils.properties.PropertiesDialogPage;

import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 10/07/13
 * Time: 11:05
 */
public class JsonEditorApplication extends SingleFrameApplication
{

    @Override
    protected void initialiseApplication( String[] args )
    {

    }

    @Override
    protected void initialiseSingleFrameApplicationConfiguration()
    {

    }

    @Override
    public List<AbstractApplicationPropertiesPage> getSingleApplicationConfigurationPages()
    {
        return null;
    }
}

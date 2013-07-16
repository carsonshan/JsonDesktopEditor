package ge.test.multi.application;

import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.multi.dialog.properties.AbstractApplicationPropertiesPage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/07/13
 * Time: 15:32
 */
public class TestMultiApplication extends MultiFrameApplication
{
    @Override
    protected void initialiseApplicationConfiguration()
    {

    }

    @Override
    public List<AbstractApplicationPropertiesPage> getApplicationConfigurationPages()
    {
        return null;
    }

    @Override
    protected void initialiseApplication( String[] args )
    {

    }
}

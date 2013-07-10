package ge.framework.application.core.frame.persistence;

import com.jidesoft.swing.LayoutPersistenceManager;
import ge.framework.application.core.frame.persistence.callback.LoadCallBack;
import ge.framework.application.core.frame.persistence.callback.SaveCallBack;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 26/02/13
 * Time: 18:31
 */
public class ApplicationLayoutPersistenceManager extends LayoutPersistenceManager
{
    public ApplicationLayoutPersistenceManager( String typeName )
    {
        super();

        setProfileKey( typeName );

        setLoadCallback( new LoadCallBack() );
        setSaveCallback( new SaveCallBack() );

        setUsePref( false );
        setXmlFormat( true );

        setUseFrameBounds( true );
        setUseFrameState( true );
    }

    public void setLayoutDirectory( File metadataDirectory )
    {
        super.setLayoutDirectory( metadataDirectory.getPath() );
    }
}

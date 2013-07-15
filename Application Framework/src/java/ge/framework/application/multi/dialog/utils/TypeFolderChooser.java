package ge.framework.application.multi.dialog.utils;

import com.jidesoft.swing.FolderChooser;
import ge.framework.frame.multi.objects.MultiFrameDefinition;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 04/02/13
 * Time: 11:37
 */
public class TypeFolderChooser extends FolderChooser
{
    private MultiFrameDefinition type;

    public TypeFolderChooser( MultiFrameDefinition type )
    {
        super( new TypeFileSystemView( type ) );
        this.type = type;

        setMultiSelectionEnabled( false );
    }

    @Override
    public void approveSelection()
    {
        if ( type.isDirectory( getSelectedFile() ) == true )
        {
            super.approveSelection();
        }
    }
}

package ge.framework.frame.single.dialog;

import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.dialog.FramePropertiesDialog;
import ge.framework.frame.core.dialog.properties.AbstractFramePropertiesPage;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.single.dialog.properties.GeneralSingleFramePropertiesPage;
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
 * Date: 18/02/2013
 * Time: 21:14
 */
public class SingleFramePropertiesDialog extends FramePropertiesDialog
{
    public SingleFramePropertiesDialog( ApplicationFrame applicationFrame )
    {
        super( applicationFrame );
    }

    @Override
    protected List<PropertiesDialogPage<FrameConfiguration>> getPages()
    {
        List<PropertiesDialogPage<FrameConfiguration>> retVal = super.getPages();

        if ( retVal == null )
        {
            retVal = new ArrayList<PropertiesDialogPage<FrameConfiguration>>(  );
        }

        retVal.add( 0, new GeneralSingleFramePropertiesPage() );

        return retVal;
    }
}

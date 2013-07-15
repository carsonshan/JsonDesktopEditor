package ge.framework.frame.multi;

import ge.framework.application.core.Application;
import ge.framework.application.multi.MultiFrameApplication;
import ge.framework.application.single.SingleFrameApplication;
import ge.framework.frame.core.ApplicationFrame;
import ge.framework.frame.core.objects.FrameConfiguration;
import ge.framework.frame.core.objects.FrameDefinition;
import ge.framework.frame.multi.objects.FrameInstanceDetailsObject;
import ge.framework.frame.multi.objects.MultiFrameConfiguration;
import ge.framework.frame.multi.objects.MultiFrameDefinition;
import ge.framework.frame.single.SingleApplicationFrame;
import ge.framework.frame.single.objects.SingleFrameConfiguration;
import ge.framework.frame.single.objects.SingleFrameDefinition;

import java.awt.HeadlessException;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 15/07/13
 * Time: 16:00
 */
public abstract class MultiApplicationFrame<APPLICATION extends MultiFrameApplication,
                                            CONFIG extends MultiFrameConfiguration> extends
                                                                                    ApplicationFrame<APPLICATION,
                                                                                            MultiFrameDefinition,
                                                                                            CONFIG>
{
    public MultiApplicationFrame( APPLICATION application ) throws HeadlessException
    {
        super( application );
    }

    public void open( FrameInstanceDetailsObject frameInstanceDetailsObject )
    {

    }
}

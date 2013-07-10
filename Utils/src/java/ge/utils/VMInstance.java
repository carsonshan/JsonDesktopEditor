package ge.utils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 16/01/13
 * Time: 18:52
 */
public class VMInstance
{
    public static boolean isVmUnique()
    {
        VMDetails thisVmDetails = VMDetails.getThisVmDetails();
        int thisPid = thisVmDetails.getPid();
        String thisVmName = thisVmDetails.getVmName();

        List<VMDetails> allVmDetails = VMDetails.getAllVmDetails();

        for ( VMDetails vmDetails : allVmDetails )
        {
            int pid = vmDetails.getPid();
            String vmName = vmDetails.getVmName();

            if ( ( vmName.equalsIgnoreCase( thisVmName ) == true ) && ( thisPid != pid ) )
            {
                return false;
            }
        }

        return true;
    }

    public static boolean isVmRunning( int pid )
    {
        List<VMDetails> allVmDetails = VMDetails.getAllVmDetails();

        for ( VMDetails allVmDetail : allVmDetails )
        {
            if ( allVmDetail.getPid() == pid )
            {
                return true;
            }
        }

        return false;
    }
}

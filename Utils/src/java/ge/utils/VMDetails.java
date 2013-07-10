package ge.utils;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: evison_g
 * Date: 19/03/13
 * Time: 14:38
 */
public class VMDetails
{
    private final int pid;

    private final String vmName;

    private VMDetails( int pid, String vmName )
    {
        this.pid = pid;
        this.vmName = vmName;
    }

    public int getPid()
    {
        return pid;
    }

    public String getVmName()
    {
        return vmName;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof VMDetails ) )
        {
            return false;
        }

        VMDetails vmDetails = ( VMDetails ) o;

        if ( pid != vmDetails.pid )
        {
            return false;
        }
        if ( vmName != null ? !vmName.equals( vmDetails.vmName ) : vmDetails.vmName != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = pid;
        result = 31 * result + ( vmName != null ? vmName.hashCode() : 0 );
        return result;
    }

    public static VMDetails getThisVmDetails()
    {
        try
        {
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            String name = runtimeMXBean.getName();

            int pid = Integer.parseInt( name.substring( 0, name.indexOf( "@" ) ) );

            MonitoredHost host = MonitoredHost.getMonitoredHost( new HostIdentifier( ( String ) null ) );

            MonitoredVm mvm = host.getMonitoredVm( new VmIdentifier( String.valueOf( pid ) ) );

            String vmName = MonitoredVmUtil.commandLine( mvm );

            mvm.detach();

            if ( vmName.lastIndexOf( "\\" ) >= 0 )
            {
                vmName = vmName.substring( vmName.lastIndexOf( "\\" ) + 1 );
            }

            return new VMDetails( pid, vmName );
        }
        catch ( MonitorException e )
        {
            throw new InternalError( e.getMessage() );
        }
        catch ( URISyntaxException e )
        {
            throw new InternalError( e.getMessage() );
        }
    }

    public static List<VMDetails> getAllVmDetails()
    {
        try
        {
            MonitoredHost host = MonitoredHost.getMonitoredHost( new HostIdentifier( ( String ) null ) );
            Set vms = host.activeVms();

            List<VMDetails> retVal = new ArrayList<VMDetails>();

            for ( Object vm : vms )
            {
                if ( vm instanceof Integer )
                {
                    Integer vmId = ( Integer ) vm;
                    int pid = vmId;

                    MonitoredVm mvm = host.getMonitoredVm( new VmIdentifier( vmId.toString() ) );

                    String vmName = MonitoredVmUtil.commandLine( mvm );

                    mvm.detach();

                    if ( vmName.lastIndexOf( "\\" ) >= 0 )
                    {
                        vmName = vmName.substring( vmName.lastIndexOf( "\\" ) + 1 );
                    }

                    retVal.add( new VMDetails( pid, vmName ) );
                }
            }

            return retVal;
        }
        catch ( MonitorException e )
        {
            throw new InternalError( e.getMessage() );
        }
        catch ( URISyntaxException e )
        {
            throw new InternalError( e.getMessage() );
        }
    }
}

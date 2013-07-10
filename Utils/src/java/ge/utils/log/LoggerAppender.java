package ge.utils.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.List;

public class LoggerAppender extends AppenderSkeleton
{
    private static EventListenerList listenerList = new EventListenerList();

    private static List<LoggingEvent> eventList = new ArrayList<LoggingEvent>();

    @Override
    protected void append( LoggingEvent loggingEvent )
    {
        eventList.add( loggingEvent );

        LoggerListener[] listeners = getLoggerListeners();
        for ( LoggerListener listener : listeners )
        {
            listener.newLoggingEvent();
        }
    }

    @Override
    public void close()
    {
    }

    @Override
    public boolean requiresLayout()
    {
        return false;
    }

    public static void addLoggerListener( LoggerListener loggerListener )
    {
        listenerList.add( LoggerListener.class, loggerListener );
    }

    public static void removeLoggerListener( LoggerListener loggerListener )
    {
        listenerList.remove( LoggerListener.class, loggerListener );
    }

    public static LoggerListener[] getLoggerListeners()
    {
        return listenerList.getListeners( LoggerListener.class );
    }

    public static LoggingEvent get( int index )
    {
        return eventList.get( index );
    }

    public static int size()
    {
        return eventList.size();
    }
}

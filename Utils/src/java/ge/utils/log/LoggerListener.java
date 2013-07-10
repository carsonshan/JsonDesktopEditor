package ge.utils.log;

import java.util.EventListener;

public interface LoggerListener extends EventListener
{
    public void newLoggingEvent();
}

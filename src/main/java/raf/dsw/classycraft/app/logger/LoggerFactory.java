package raf.dsw.classycraft.app.logger;

import raf.dsw.classycraft.app.core.Logger;
import raf.dsw.classycraft.app.core.MessageGenerator;

public class LoggerFactory
{
    public Logger getLogger(String loggerType, MessageGenerator messageGenerator)
    {
        if(loggerType == null)
            return null;

        if(loggerType.equalsIgnoreCase("ConsoleLogger"))
            return new ConsoleLogger(messageGenerator);

        else if(loggerType.equalsIgnoreCase("FileLogger"))
            return new FileLogger(messageGenerator);

        return null;
    }
}

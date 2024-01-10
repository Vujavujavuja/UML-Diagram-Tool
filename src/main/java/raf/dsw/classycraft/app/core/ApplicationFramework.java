package raf.dsw.classycraft.app.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ApplicationFramework
{
    protected Gui gui;
    protected ClassyRepository classyRepository;
    protected MessageGenerator messageGenerator;
    protected Logger consoleLogger;
    protected Logger fileLogger;
    private Serializer serializer;
    private Serializer patternSerializer;

    public abstract void start();

    public void initialise(Gui gui, ClassyRepository classyRepository, MessageGenerator messageGenerator, Serializer serializer, Serializer patternSerializer)
    {
        this.gui = gui;
        this.classyRepository = classyRepository;
        this.messageGenerator = messageGenerator;
        this.serializer = serializer;
        this.patternSerializer = patternSerializer;
    }
    {
        this.gui = gui;
        this.classyRepository = classyRepository;
        this.messageGenerator = messageGenerator;
        this.serializer = serializer;
        this.patternSerializer = serializer;
    }

    public void initialiseLogger(Logger consoleLogger, Logger fileLogger)
    {
        this.consoleLogger = consoleLogger;
        this.fileLogger = fileLogger;
    }
}

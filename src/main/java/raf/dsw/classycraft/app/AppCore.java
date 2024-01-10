package raf.dsw.classycraft.app;

import lombok.Getter;
import raf.dsw.classycraft.app.classyRepository.commands.CommandManager;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.core.*;
import raf.dsw.classycraft.app.gui.swing.SwingGui;
import raf.dsw.classycraft.app.logger.LoggerFactory;
import raf.dsw.classycraft.app.messageGenerator.MessageGeneratorClass;
import raf.dsw.classycraft.app.serializer.JacksonSerializer;
import raf.dsw.classycraft.app.serializer.PatternSerializer;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AppCore extends ApplicationFramework
{

    private static AppCore instance;
    private AppCore() {}
    @Override
    public void start()
    {
        this.gui.start();
    }
    public static AppCore getInstance()
    {
        if(instance == null)
            instance = new AppCore();

        return instance;
    }
    public static void main(String[] args)
    {
        ApplicationFramework appCore = AppCore.getInstance();
        Gui gui = new SwingGui();
        ClassyRepository classyRepository = new raf.dsw.classycraft.app.classyRepository.ClassyRepository();
        MessageGenerator messageGenerator = new MessageGeneratorClass();
        Serializer serializer = new JacksonSerializer(classyRepository.getProjectExplorer());
        Serializer patternSerializer = new PatternSerializer();

        LoggerFactory loggerFactory = new LoggerFactory();
        Logger consoleLogger = loggerFactory.getLogger("ConsoleLogger", messageGenerator);
        Logger fileLogger = loggerFactory.getLogger("FileLogger", messageGenerator);

        appCore.initialise(gui, classyRepository, messageGenerator, serializer, patternSerializer);
        appCore.initialiseLogger(consoleLogger, fileLogger);

        appCore.start();
    }
}
package raf.dsw.classycraft.app.logger;

import raf.dsw.classycraft.app.core.Logger;
import raf.dsw.classycraft.app.core.MessageGenerator;
import raf.dsw.classycraft.app.messageGenerator.Message;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger
{
    File file;
    private MessageGenerator messageGenerator;
    public FileLogger(MessageGenerator messageGenerator)
    {
        this.messageGenerator = messageGenerator;
        this.messageGenerator.addSubscriber(this); //Dakle referenca(promenjiva interfejsa) ce moci da zove metode koje su deklarisane u klasi koja implementira taj interface
        this.makeFile();
    }

    private void makeFile()
    {
        this.file = new File("src/main/resources/file/log.txt");

        try
        {
            this.file.createNewFile();
        }

        catch (IOException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public void msg(Message message)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("[ " + message.getType() + "] " + "[ " + message.getTimeStamp() + " ] " + "[ " + message.getContent() + " ]" + "\n"); // \n jer se ispisuje u fajl pa da bi poceli u novom redu sl string
            fileWriter.flush();
            fileWriter.close();
        }

        catch (IOException e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Object notification, Object typeOfUpdate)
    {
        this.msg((Message) notification);
    }
}

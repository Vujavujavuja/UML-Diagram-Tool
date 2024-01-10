package raf.dsw.classycraft.app.logger;

import raf.dsw.classycraft.app.core.Logger;
import raf.dsw.classycraft.app.core.MessageGenerator;
import raf.dsw.classycraft.app.messageGenerator.Message;
import raf.dsw.classycraft.app.messageGenerator.MessageGeneratorClass;

public class ConsoleLogger implements Logger
{
    private MessageGenerator messageGenerator;
    public ConsoleLogger(MessageGenerator messageGenerator)
    {
        this.messageGenerator = messageGenerator;
        this.messageGenerator.addSubscriber(this);  //Dakle referenca(promenjiva interfejsa) ce moci da zove metode koje su deklarisane u klasi koja implementira taj interface
    }

    @Override
    public void msg(Message message)
    {
        StringBuilder errorMsg = new StringBuilder();

        errorMsg.append("[ " + message.getType() + " ] ");
        errorMsg.append("[ " + message.getTimeStamp() + " ] ");
        errorMsg.append("[ " + message.getContent() + " ] ");

        System.out.println(errorMsg.toString());
    }

    @Override
    public void update(Object notification, Object typeOfUpdate)
    {
        this.msg((Message) notification);
    }
}

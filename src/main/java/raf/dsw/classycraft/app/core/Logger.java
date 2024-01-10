package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.messageGenerator.Message;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

public interface Logger extends ISubscriber
{
    void msg(Message message);
}

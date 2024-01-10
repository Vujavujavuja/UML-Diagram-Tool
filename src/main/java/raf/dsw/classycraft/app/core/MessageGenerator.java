package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.core.observer.IPublisher;

//MessageGenerator extenduje Publishera zbog njegovih metoda koje su smislene za ovaj tip "posla"
public interface MessageGenerator extends IPublisher
{
    void generate(Enum typeOfEvent); //Metoda za generisanje poruka u sistemu
}

package raf.dsw.classycraft.app.core.observer;

public interface IPublisher
{
    void addSubscriber(ISubscriber subscriber); //Dodajemo observera odnosno subscribera u listu

    void removeSubscriber(ISubscriber subscriber); //Brisemo observera odnosno subscribera iz liste

    void notifySubscriber(Object notification, Object typeOfUpdate); //Iteriramo kroz listu i zovemo update metodu nad observerima
}

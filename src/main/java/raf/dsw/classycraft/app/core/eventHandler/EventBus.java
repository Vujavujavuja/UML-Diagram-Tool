package raf.dsw.classycraft.app.core.eventHandler;

import raf.dsw.classycraft.app.core.observer.IPublisher;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus implements IPublisher{
    public static Object EventType;
    private static EventBus instance = null;
    private final Map<EventType, List<ISubscriber>> subscribers = new HashMap<>();

    private EventBus() {}

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void subscribe(EventType eventType, ISubscriber subscriber) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {

    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber(Object notification, Object eventType) {
        List<ISubscriber> eventSubscribers = subscribers.get(eventType);
        if (eventSubscribers != null) {
            for (ISubscriber subscriber : new ArrayList<>(eventSubscribers)) {
                subscriber.update(notification, eventType);
            }
        }
    }

}

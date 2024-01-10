package raf.dsw.classycraft.app.classyRepository.composite;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.core.observer.IPublisher;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class ClassyNode implements IPublisher
{
    protected transient ClassyNode parent;
    protected String name;
    protected String type;

    private transient List<ISubscriber> subscribers;

    public ClassyNode(ClassyNode parent, String name)
    {
        this.parent = parent;
        this.name = name;
        this.type = "ClassyNode";
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof ClassyNode)
        {
            ClassyNode otherObj = (ClassyNode) obj;
            return this.getName().equals(otherObj.getName());
        }

        return false;
    }

    public void addSubscriber(ISubscriber subscriber)
    {
        if(subscriber == null)
            return;

        if(this.subscribers == null)
            this.subscribers = new ArrayList<>();

        if(this.subscribers.contains(subscriber))
            return;

        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber)
    {
        if(subscriber == null || this.subscribers == null || !this.subscribers.contains(subscriber))
            return;

        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscriber(Object notification, Object typeOfUpdate)
    {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;

        for(ISubscriber subscriber : subscribers)
            subscriber.update(notification, typeOfUpdate);
    }

    public String getUniqueId() {
        return name + "_" + type + "_" + getParent();
    }
}

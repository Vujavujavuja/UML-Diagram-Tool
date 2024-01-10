package raf.dsw.classycraft.app.classyRepository.implementation.subElements;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.core.observer.IPublisher;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UmlSelectionModel implements IPublisher //Klasa sa listom u kojoj se cuvaju selektovani elementi
{
    private List<ISubscriber> subscribers;
    private List<DiagramElement> selected;
    public UmlSelectionModel()
    {
        this.selected = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void select(DiagramElement element)
    {
        if(selected.contains(element))
            return;

        selected.add(element);
        this.notifySubscriber(this, null);
    }

    public boolean isSelected(DiagramElement element)
    {
        if(selected.contains(element))
            return true;

        return false;
    }

    public List<DiagramElement> getSelected() {
        return selected;
    }

    public void unselect(DiagramElement element)
    {
        if(selected.contains(element))
        {
            selected.remove(element);
            this.notifySubscriber(this, null);
        }
    }

    public void clear()
    {
        selected.clear();
        this.notifySubscriber(this, null);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {this.subscribers.add(subscriber);}

    @Override
    public void removeSubscriber(ISubscriber subscriber) {this.subscribers.remove(subscriber);}

    @Override
    public void notifySubscriber(Object notification, Object typeOfUpdate)
    {
        for(ISubscriber subscriber : subscribers)
            subscriber.update(notification, typeOfUpdate);
    }
}

package raf.dsw.classycraft.app.messageGenerator;

import raf.dsw.classycraft.app.core.MessageGenerator;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.core.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class MessageGeneratorClass implements MessageGenerator
{
    private List<ISubscriber> subscribers;

    @Override
    public void generate(Enum typeOfEvent)
    {
        if(typeOfEvent == EventType.NODE_CANNOT_BE_DELETED)
            this.notifySubscriber(new Message("Node cannot be deleted", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NAME_CANNOT_BE_EMPTY)
            this.notifySubscriber(new Message("Name cannot be empty", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NAME_ALREADY_EXISTS)
            this.notifySubscriber(new Message("Name already exists", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANNOT_EDIT_PROJECT_EXPLORER)
            this.notifySubscriber(new Message("Editing the project explorer is not allowed", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NODE_IS_NOT_PROJECT)
            this.notifySubscriber(new Message("Packages can only be added within a project", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_ADD_PROJECT_IN_PROJECT)
            this.notifySubscriber(new Message("Adding a project inside another project is not allowed", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_ADD_DIAGRAM_IN_DIARGAM)
            this.notifySubscriber(new Message("Adding a diagram inside another diagram is not allowed", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_ADD_DIAGRAM_IN_PROJECT)
            this.notifySubscriber(new Message("Adding a diagram inside project is not allowed", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_ADD_PROJECT_IN_PACKAGE)
            this.notifySubscriber(new Message("Projects can only be added within project explorer", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_ADD_ANYTHING_IN_DIAGRAM)
            this.notifySubscriber(new Message("Cannot add elements inside this diagram", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NO_ITEM_TO_DELETE)
            this.notifySubscriber(new Message("No item selected for deletion", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NODE_IS_NOT_PROJECT_OR_PACKAGE)
            this.notifySubscriber(new Message("Diagrams can only be added within a project or package", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NODE_IS_NOT_SELECTED)
            this.notifySubscriber(new Message("Project explorer is not selected", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_DELETE_PROJECT_EXPLORER)
            this.notifySubscriber(new Message("Project Explorer cant be deleted", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_RENAME_AUTHOR)
            this.notifySubscriber(new Message("Authors name cant be changed", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CANT_EMPTY_STRING)
            this.notifySubscriber(new Message("String cannot be empty", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.MAX_ZOOM)
            this.notifySubscriber(new Message("Diagram is zoomed in at its maximum", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.MIN_ZOOM)
            this.notifySubscriber(new Message("Diagram is zoomed out at its maximum", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.FIELD_ALREADY_EXISTS)
            this.notifySubscriber(new Message("Field already exists!", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.SELECT_ITEM_TO_EDIT)
            this.notifySubscriber(new Message("Please select item to edit", "Information"), typeOfEvent);
        else if(typeOfEvent == EventType.FAULTY_CONNECTION)
            this.notifySubscriber(new Message("These elements can not be connected", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.PROJECT_NOT_CHANGED)
            this.notifySubscriber(new Message("Project is not changed", "Information"), typeOfEvent);
        else if(typeOfEvent == EventType.PROJECT_SAVED)
            this.notifySubscriber(new Message("Project saved", "Information"), typeOfEvent);
        else if(typeOfEvent == EventType.NOT_PROJECT)
            this.notifySubscriber(new Message("Project is not selected", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.NO_DIAGRAM_SELECTED)
            this.notifySubscriber(new Message("Diagram is not selected", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CODE_ALREADY_EXISTS)
            this.notifySubscriber(new Message("Code with that project name already exists", "Error"), typeOfEvent);
        else if(typeOfEvent == EventType.CODE_GENERATED)
            this.notifySubscriber(new Message("Code successfully generated", "Information"), typeOfEvent);
        else if(typeOfEvent == EventType.UNKNOWN)
            this.notifySubscriber(new Message("Unknown error", "Error"), typeOfEvent);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber)
    {
        if(subscriber == null)
            return;

        if(this.subscribers == null)
            this.subscribers = new ArrayList<ISubscriber>();

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
    public void notifySubscriber(Object notification, Object typeOfUpdate) //Iteriramo kroz listu i zovemo update metodu nad observerima
    {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;

        for(ISubscriber subscriber : subscribers)
            subscriber.update(notification, typeOfUpdate);
    }


}

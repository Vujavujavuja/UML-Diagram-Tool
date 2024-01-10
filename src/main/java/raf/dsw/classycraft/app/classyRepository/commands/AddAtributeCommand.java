package raf.dsw.classycraft.app.classyRepository.commands;

import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;

public class AddAtributeCommand implements Command{

    private ClassyNode parent;
    private ClassContent child;
    public AddAtributeCommand(ClassyNode parent, ClassContent child)
    {
        this.parent = parent;
        this.child = child;
    }
    @Override
    public void undoCommand() {
        if(parent instanceof Klasa) {
            for (ClassContent child :  ((Klasa) parent).getClassContents()) {
                if (child.getName().equals(this.child.getName())) {
                    ((Klasa) parent).getClassContents().remove(child);
                    MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
                    MainFrame.getInstance().getPackageView().panelOutsideRefresh();
                    break;
                }
            }
        }
        else if(parent instanceof Interfejs){
            for (ClassContent child :  ((Interfejs) parent).getMetods()) {
                if (child.getName().equals(this.child.getName())) {
                    ((Interfejs) parent).getMetods().remove(child);
                    MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
                    MainFrame.getInstance().getPackageView().panelOutsideRefresh();
                    break;
                }
            }
        }
        else if(parent instanceof UmlEnum){
            for (String child :  ((UmlEnum) parent).getConstants()) {
                if (child.equals(this.child.getName())) {
                    ((UmlEnum) parent).getConstants().remove(child);
                    MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
                    MainFrame.getInstance().getPackageView().panelOutsideRefresh();
                    break;
                }
            }
        }
    }

    @Override
    public void redoCommand() {
        if(parent instanceof Klasa) {
            ((Klasa) parent).getClassContents().add(child);
            MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
            MainFrame.getInstance().getPackageView().panelOutsideRefresh();
        }
        else if(parent instanceof Interfejs){
            ((Interfejs) parent).getMetods().add((Metod) child);
            MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
            MainFrame.getInstance().getPackageView().panelOutsideRefresh();
        }
        else if(parent instanceof UmlEnum){
            ((UmlEnum) parent).getConstants().add(child.getName());
            MainFrame.getInstance().getPackageView().setPanelPainters(new ArrayList<>());
            MainFrame.getInstance().getPackageView().panelOutsideRefresh();
        }
    }
}

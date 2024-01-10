package raf.dsw.classycraft.app.gui.swing.view.tabbedPane;

import lombok.Getter;
import lombok.Setter;
import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Pair;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.UmlSelectionModel;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.observer.ISubscriber;
import raf.dsw.classycraft.app.gui.swing.classyTree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.listner.ClassyMouse;
import raf.dsw.classycraft.app.gui.swing.state.StateManager;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PackageView extends CloseableTabbedPane implements ISubscriber {
    private StateManager stateManager = new StateManager();
    List<Diagram> openDiagrams;
    List<Component> tabs;
    public PackageView()
    {
        super();
        openDiagrams = new ArrayList<>();
        tabs = new ArrayList<>();
        ClassyMouse classyMouse;
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tabChanged();
            }
        });
        if(getCurrentDiagramPanel() != null) {
            classyMouse = new ClassyMouse(getAffineTransform());
        }
        else {
            classyMouse = new ClassyMouse(new AffineTransform());
        }
        EventBus.getInstance().subscribe(EventType.DIAGRAM_SELECTION, this);
        EventBus.getInstance().subscribe(EventType.DIAGRAM_DELETION, this);
        EventBus.getInstance().subscribe(EventType.DIAGRAM_RENAME, this);
        EventBus.getInstance().subscribe(EventType.DIAGRAM_CLOSE, this);
        EventBus.getInstance().subscribe(EventType.DIAGRAM_LIST_DELETION, this);
        EventBus.getInstance().subscribe(EventType.CLOSE_TABS, this);
        EventBus.getInstance().subscribe(EventType.ADD_CLASS, this);
        EventBus.getInstance().subscribe(EventType.ADD_INTERFACE, this);
        EventBus.getInstance().subscribe(EventType.ADD_ENUM, this);
        EventBus.getInstance().subscribe(EventType.ADD_METHOD, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_IN, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_OUT, this);
        EventBus.getInstance().subscribe(EventType.ADD_GENERALIZATION, this);
        EventBus.getInstance().subscribe(EventType.ADD_AGGREGATION, this);
        EventBus.getInstance().subscribe(EventType.ADD_COMPOSITION, this);
        EventBus.getInstance().subscribe(EventType.ADD_DEPENDENCY, this);
        EventBus.getInstance().subscribe(EventType.CONTENT_STATE, this);
        EventBus.getInstance().subscribe(EventType.SELECT_ELEMENT, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_IN_STATE, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_OUT_STATE, this);
        EventBus.getInstance().subscribe(EventType.REFRESH, this);
        EventBus.getInstance().subscribe(EventType.DRAG, this);
        EventBus.getInstance().subscribe(EventType.CLEAR_DRAG, this);
        EventBus.getInstance().subscribe(EventType.START_DRAG, this);
        EventBus.getInstance().subscribe(EventType.DRAG_DEL, this);
        EventBus.getInstance().subscribe(EventType.CLEAR_DRAG_DEL, this);
        EventBus.getInstance().subscribe(EventType.START_DRAG_DEL, this);
        EventBus.getInstance().subscribe(EventType.MOVE, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_TO_FIT_STATE, this);
        EventBus.getInstance().subscribe(EventType.ZOOM_TO_FIT, this);
        EventBus.getInstance().subscribe(EventType.DELETE, this);
        EventBus.getInstance().subscribe(EventType.ADD_CLASS_TO_TREE_S, this);
        EventBus.getInstance().subscribe(EventType.ADD_INTERFACE_TO_TREE_S, this);
        EventBus.getInstance().subscribe(EventType.ADD_ENUM_TO_TREE_S, this);
        EventBus.getInstance().subscribe(EventType.DEL_REL, this);
    }

    private void tabChanged() {
        if(getCurrentDiagramPanel() != null)
            EventBus.getInstance().notifySubscriber(getCurrentDiagramPanel().getDiagram(), EventType.SET_PANEL);
        updateUndoRedoStateForActiveDiagram();
    }
    @Override
    public void update(Object notification, Object typeOfUpdate)
    {
        if(EventType.CONTENT_STATE.equals(typeOfUpdate))
            this.startAddFieldState();
        else if(EventType.ADD_ENUM.equals(typeOfUpdate))
            this.startAddEnumState();
        else if(EventType.ADD_GENERALIZATION.equals(typeOfUpdate))
            this.startGeneralizationState();
        else if(EventType.ADD_AGGREGATION.equals(typeOfUpdate))
            this.startAgregationState();
        else if(EventType.ADD_COMPOSITION.equals(typeOfUpdate))
            this.startKompozicijaState();
        else if(EventType.ADD_DEPENDENCY.equals(typeOfUpdate))
            this.startDependencyState();
        else if(EventType.SELECT_ELEMENT.equals(typeOfUpdate))
            this.startSelectState();
        else if(EventType.ADD_CLASS.equals(typeOfUpdate))
            this.startAddClassState();
        else if(EventType.ADD_INTERFACE.equals(typeOfUpdate))
            this.startAddInterfaceState();
        else if(EventType.ZOOM_IN.equals(typeOfUpdate))
            this.startZoomInState();
        else if(EventType.ZOOM_OUT.equals(typeOfUpdate))
            this.startZoomOutState();
        else if(EventType.ZOOM_TO_FIT.equals(typeOfUpdate))
            this.startZoomToFitState();
        else if(EventType.ZOOM_TO_FIT_STATE.equals(typeOfUpdate)){
            this.getCurrentDiagramPanel().zoomToFit();
        }
        else if(EventType.ADD_CLASS_TO_TREE_S.equals(typeOfUpdate)){
            EventBus.getInstance().notifySubscriber(notification, EventType.ADD_CLASS_TO_TREE);
        }
        else if(EventType.ADD_INTERFACE_TO_TREE_S.equals(typeOfUpdate)){
            EventBus.getInstance().notifySubscriber(notification, EventType.ADD_INTERFACE_TO_TREE);
        }
        else if(EventType.ADD_ENUM_TO_TREE_S.equals(typeOfUpdate)){
            EventBus.getInstance().notifySubscriber(notification, EventType.ADD_ENUM_TO_TREE);
        }
        else if(EventType.DEL_REL.equals(typeOfUpdate)){
            getCurrentDiagramPanel().delRel(notification);
        }

        if(EventType.CLOSE_TABS.equals(typeOfUpdate))
        {
            updateUndoRedoStateForActiveDiagram();
            this.closeAllTabs();
            return;
        }
        if (notification instanceof ClassyTreeItem)
        {
            ClassyTreeItem item = (ClassyTreeItem) notification;

            if (EventType.DIAGRAM_DELETION.equals(typeOfUpdate))
            {
                updateUndoRedoStateForActiveDiagram();
                System.out.println("DELETE in tab");

                this.openDiagrams.remove((Diagram) ((ClassyTreeItem) notification).getClassyNode());
                String uniqueId = item.getClassyNode().getUniqueId();
                String name = "";
                for(char c: uniqueId.toCharArray()){
                    if(c == '_')
                        break;
                    name = name + c;
                }
                System.out.println(name);
                for (int i = 0; i < this.getTabCount(); i++)
                {
                    Component tabComponent = this.getComponentAt(i);
                    System.out.println(tabComponent.getName());
                    if (tabComponent.getName().equals(name))
                    {
                        this.removeTabAt(i);
                        break;
                    }
                }
            }
        }

        else if(notification instanceof ClassyNode)
        {
            if(EventType.DIAGRAM_DELETION.equals(typeOfUpdate))
            {
                this.openDiagrams.remove((Diagram) notification);
                String uniqueId = ((ClassyNode) notification).getUniqueId();

                for (int i = 0; i < this.getTabCount(); i++)
                {
                    Component tabComponent = this.getComponentAt(i);

                    if (tabComponent.getName().equals(uniqueId))
                    {
                        System.out.println("Deleting" + tabComponent.getName());
                        this.removeTabAt(i);
                        break;
                    }
                }
            }
            else if(EventType.DIAGRAM_SELECTION.equals(typeOfUpdate)){
                updateUndoRedoStateForActiveDiagram();
                Diagram selectedDiagram = (Diagram) notification;
                if(this.openDiagrams.contains((Diagram) notification))
                {
                    this.setSelectedIndex(this.indexOfTab(((ClassyNode) notification).getName()));
                    return;
                }

                this.openDiagrams.add((Diagram) notification);
                DiagramPanel diagramPanel = new DiagramPanel(selectedDiagram);
                ClassyMouse classyMouse = new ClassyMouse(diagramPanel.getAffineTransform());

                diagramPanel.addMouseListener(classyMouse);
                diagramPanel.addMouseMotionListener(classyMouse);
                String title = ((ClassyNode) notification).getName();
                this.addTab(title, diagramPanel);
                int index = this.indexOfTab(title);
                this.getComponentAt(index).setName(((ClassyNode) notification).getUniqueId());
                updateUndoRedoStateForActiveDiagram();

            }
        }
        else if(EventType.DIAGRAM_LIST_DELETION.equals(typeOfUpdate))
        {
            updateUndoRedoStateForActiveDiagram();
            System.out.println("LIST DELETE1");

            if(notification instanceof List)
            {
                System.out.println("LIST DELETE2");
                List<ClassyNode> list = (List<ClassyNode>) notification;

                for(ClassyNode node : list)
                {
                    if(node instanceof Diagram)
                    {
                        this.openDiagrams.remove((Diagram) node);
                        String uniqueId = node.getUniqueId();

                        for (int i = 0; i < this.getTabCount(); i++)
                        {
                            Component tabComponent = this.getComponentAt(i);

                            if (tabComponent.getName().equals(uniqueId))
                            {
                                System.out.println("Deleting" + tabComponent.getName());
                                this.removeTabAt(i);
                                break;
                            }
                        }
                    }
                }
            }
        }
        else if(notification instanceof Component)
        {
            if(EventType.DIAGRAM_CLOSE.equals(typeOfUpdate))
            {
                updateUndoRedoStateForActiveDiagram();
                JPanel temp = (JPanel) notification;
                String id = temp.getName();
                String name = "";

                for(char c: id.toCharArray())
                {
                    if(c == '_')
                        break;
                    name = name + c;
                }

                name.substring(0, name.length() - 1);
                for(Diagram d : openDiagrams){
                    System.out.println(d.getName() + " - " + name);
                    if(d.getName().equals(name))
                    {
                        //System.out.println("FOUND");
                        openDiagrams.remove(d);

                        for (int i = 0; i < this.getTabCount(); i++)
                        {
                            Component tabComponent = this.getComponentAt(i);
                            String tname = "";
                            for(char c: tabComponent.getName().toCharArray()){
                                if(c == '_')
                                    break;
                                tname = tname + c;
                            }

                            tname.substring(0, tname.length() - 1);
                            //System.out.println(tabComponent.getName() + " - " + name);
                            if (tname.equals(name))
                            {
                                //System.out.println("Deleting" + tabComponent.getName());
                                this.removeTabAt(i);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        if(EventType.DIAGRAM_RENAME.equals(typeOfUpdate))
        {
            updateUndoRedoStateForActiveDiagram();
            System.out.println("RENAME");
            System.out.println(notification);
            String oldName = "";
            String noti = (String) notification;
            String split[] = noti.split("/");
            String newName = split[1];
            oldName = split[0];
            oldName.substring(0, oldName.length() - 1);

            for (int i = 0; i < this.getTabCount(); i++)
            {
                Component tabComponent = this.getComponentAt(i);
                String tname = "";
                for(char c: tabComponent.getName().toCharArray())
                {
                    if(c == '_')
                        break;
                    tname = tname + c;
                }

                System.out.println(tname);
                System.out.println(oldName);
                System.out.println(tname.equals(oldName));

                if (tname.equals(oldName))
                {
                    System.out.println("Renaming" + tabComponent.getName());
                    System.out.println(newName);
                    tabComponent.setName(newName);
                    this.setTitleAt(i, newName);
                    Component tempTab = this.getComponentAt(i);
                    this.remove(i);
                    this.add(tempTab);
                    this.revalidate();
                    this.repaint();
                    break;
                }
            }
        }
        if(EventType.MOVE.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.treverse(notification);
        }
        else if(EventType.ZOOM_IN_STATE.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.zoomInS(notification);
        }
        else if(EventType.ZOOM_OUT_STATE.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.zoomOutS(notification);
        }
        else if(EventType.REFRESH.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.refresh(notification);
        }
        else if(EventType.DRAG.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.drag(notification);
        }
        else if(EventType.CLEAR_DRAG.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.clearDrag(notification);
        }
        else if(EventType.START_DRAG.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.startDrag(notification);
        }
        else if(EventType.START_DRAG_DEL.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.startDragD(notification);
        }
        else if(EventType.DRAG_DEL.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.dragD(notification);
        }
        else if(EventType.CLEAR_DRAG_DEL.equals(typeOfUpdate)){
            DiagramPanel currentPanel = this.getCurrentDiagramPanel();
            currentPanel.clearDragD(notification);
        }
        else if(EventType.DELETE.equals(typeOfUpdate)){
            Pair pair = new Pair(null, null);
            pair.setFirst(getCurrentDiagramPanel());
            pair.setSecond(notification);
            EventBus.getInstance().notifySubscriber(pair, EventType.DELETE_ELEMENTS);
        }

    }
    private void closeAllTabs() {
        while (this.getTabCount() > 0) {
            this.removeTabAt(0);
        }
        this.openDiagrams.clear();
    }

    public void handleMousePressed(MouseEvent e, boolean rightMouseButtonPressed)
    {
        if(rightMouseButtonPressed)
        {
            this.stateManager.getCurrentState().stateRightMousePressed(e.getX(), e.getY(), this);
        }
        else
        {
            this.stateManager.getCurrentState().stateMousePressed(e.getX(), e.getY(), this);
        }
    }
    public void handleMouseReleased(MouseEvent e, boolean rightMouseButtonPressed)
    {
        if(rightMouseButtonPressed)
        {
            this.stateManager.getCurrentState().stateRightMouseReleased(e.getX(), e.getY(), this);
        }
        else
        {
            this.stateManager.getCurrentState().stateMouseReleased(e.getX(), e.getY(), this);
        }
    }

    public void handleMouseDragged(MouseEvent e, boolean rightButtonPressed) {
        DiagramPanel currentPanel = this.getCurrentDiagramPanel();
        if (rightButtonPressed) {
            System.out.println("Right drag cords: " + e.getX() + " " + e.getY());
            this.stateManager.getCurrentState().stateRightMouseDragged(e.getX(), e.getY(), this);
        } else {
            this.stateManager.getCurrentState().stateMouseDragged(e.getX(), e.getY(), this);
        }
    }

   public DiagramPanel getSelectedDiagramPanel()
   {
        return (DiagramPanel) this.getSelectedComponent();
   }
   public DiagramPanel getCurrentDiagramPanel(){
            if(this.getSelectedIndex() == -1)
                return null;
        return (DiagramPanel) this.getComponentAt(this.getSelectedIndex());
   }

    public void startAddClassState()
    {
        this.stateManager.setAddClassState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startAddInterfaceState()
    {
        this.stateManager.setAddInterfaceState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startAddEnumState()
    {
        this.stateManager.setAddEnumState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startSelectState()
    {
        this.stateManager.setSelectState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startGeneralizationState()
    {
        this.stateManager.setGeneralizationState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startAgregationState()
    {
        this.stateManager.setAgregationState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startKompozicijaState()
    {
        this.stateManager.setCompositionState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startDependencyState()
    {
        this.stateManager.setAddDependancyState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void startZoomInState() {this.stateManager.setZoomInState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();}
    public void startZoomOutState() {this.stateManager.setZoomOutState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();}
    public void startAddFieldState() {this.stateManager.setAddContentState();
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();}
    public void startMoveElementState() {
            this.stateManager.setMoveElementState();

        }
    public void startCopyState(){
            this.stateManager.setCopyState();
    }
    public void startZoomToFitState() {
            this.stateManager.setZoomToFitState();
            this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
            this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
            this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
            this.getCurrentDiagramPanel().repaint();
        }
    public void startDeleteElementState() {
            this.stateManager.setDeleteState();
            this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
            this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
            this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
            this.getCurrentDiagramPanel().repaint();
        }


    //Metode za kontrolisanje komunikacije sa dijagramom
    public void setPanelPainters(ArrayList<ElementPainter> painters)
    {
        this.getCurrentDiagramPanel().setPainters(painters);
    }
    public void panelRepaint()
    {
        this.getCurrentDiagramPanel().repaint();
    }
    public void panelOutsideRefresh()
    {
        this.getCurrentDiagramPanel().outsideRefresh();
    }
    public void addDiagramElement(Pair pair, DiagramElement diagram)
    {
        this.getCurrentDiagramPanel().getDiagram().addDiagramElement(pair, diagram);
    }
    public void setPanelCursor(Cursor cursor)
    {
        this.getCurrentDiagramPanel().setCursor(cursor);
    }
    public List<DiagramElement> currentDiagramElements()
    {
        return this.getCurrentDiagramPanel().getDiagram().getDiagramElements();
    }
    public Diagram getDiagram()
    {
        return this.getCurrentDiagramPanel().getDiagram();
    }
    public void addPainter(ElementPainter painter)
    {
        this.getCurrentDiagramPanel().getPainters().add(painter);
    }
    public void removePainter(ElementPainter painter)
    {
        this.getCurrentDiagramPanel().getPainters().remove(painter);
    }
    public void setPanelSelectionPainters(ArrayList<ElementPainter> painters)
    {
        this.getCurrentDiagramPanel().setSelectedPainters(painters);
    }
    public UmlSelectionModel getSelectionModel()
    {
        return this.getCurrentDiagramPanel().getSelectionModel();
    }
    public void setSelectionModel(UmlSelectionModel selectionModel)
    {
        this.getCurrentDiagramPanel().setSelectionModel(selectionModel);
    }
    public void deleteElements(ArrayList<DiagramElement> elements)
    {
        System.out.println("Brisem elemente prvi je:" + elements.get(0).getName());
        this.getCurrentDiagramPanel().getDiagram().getDiagramElements().removeAll(elements);
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public void clearSelectedElements(){

            this.getCurrentDiagramPanel().getSelectedElements().clear();
    }
    public ArrayList<DiagramElement> getSelectedElementsDel(){
            return this.getCurrentDiagramPanel().getSelectedElementsD();
    }
    public void clearSelectedAll (){
        this.getCurrentDiagramPanel().setSelectedPainters(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElements(new ArrayList<>());
        this.getCurrentDiagramPanel().setSelectedElementsD(new ArrayList<>());
        this.getCurrentDiagramPanel().repaint();
    }
    public AffineTransform getAffineTransform(){
        return this.getCurrentDiagramPanel().getAffineTransform();
    }
    public void raiseErrorFC(){
        AppCore.getInstance().getMessageGenerator().generate(EventType.FAULTY_CONNECTION);
    }
    public void raiseErrorNE(){
        AppCore.getInstance().getMessageGenerator().generate(EventType.NAME_CANNOT_BE_EMPTY);
    }
    public void raiseErrorNAE(){
        AppCore.getInstance().getMessageGenerator().generate(EventType.NAME_ALREADY_EXISTS);
    }
    public void diagramPattern(List<DiagramElement> elements){
        for(DiagramElement element : elements){
            for(DiagramElement diagramElement : this.getCurrentDiagramPanel().getDiagram().getDiagramElements()){
                if(element.getName().equals(diagramElement.getName()))
                    element.setName(element.getName() + "-Pattern");
            }
            this.getCurrentDiagramPanel().getDiagram().addDiagramElement(null,element);
        }
        this.panelOutsideRefresh();
    }
    private void updateUndoRedoStateForActiveDiagram() {
        if(this.getCurrentDiagramPanel() != null) {
            Diagram currentDiagram = getCurrentDiagramPanel().getDiagram();
            if (currentDiagram != null) {
                currentDiagram.updateUndoRedoStateForActiveDiagram();
            }
        }
    }

}

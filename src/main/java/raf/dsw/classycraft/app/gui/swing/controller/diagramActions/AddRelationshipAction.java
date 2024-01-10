package raf.dsw.classycraft.app.gui.swing.controller.diagramActions;

import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRelationshipAction extends AbstractClassyAction
{
    public AddRelationshipAction()
    {
        this.putValue(SMALL_ICON, loadIcon("/images/Relationship.png"));
        this.putValue(SHORT_DESCRIPTION, "Add Relationship");
        this.putValue(NAME, "Add Relationship");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAggregation = new JButton("Add Aggregation");
        JButton btnComposition = new JButton("Add Composition");
        JButton btnDependency = new JButton("Add Dependency");
        JButton btnGeneralization = new JButton("Add Generalization");

        btnAggregation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Aggregation button pressed");
                EventBus.getInstance().notifySubscriber(this, EventType.ADD_AGGREGATION);
            }
        });

        btnComposition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Composition button pressed");
                EventBus.getInstance().notifySubscriber(this, EventType.ADD_COMPOSITION);
            }
        });

        btnDependency.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Dependency button pressed");
                EventBus.getInstance().notifySubscriber(this, EventType.ADD_DEPENDENCY);
            }
        });

        btnGeneralization.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Generalization button pressed");
                EventBus.getInstance().notifySubscriber(this, EventType.ADD_GENERALIZATION);
            }
        });

        toolBar.add(btnAggregation);
        toolBar.add(btnComposition);
        toolBar.add(btnDependency);
        toolBar.add(btnGeneralization);

        JOptionPane.showMessageDialog(MainFrame.getInstance(), toolBar, "Add Relationship", JOptionPane.PLAIN_MESSAGE);
    }
}

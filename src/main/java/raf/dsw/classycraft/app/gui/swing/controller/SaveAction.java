package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveAction extends AbstractClassyAction{
public SaveAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/save.png"));
        this.putValue(SHORT_DESCRIPTION, "Save");
        this.putValue(NAME, "Save");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save action");

        if (!(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Project)) {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NOT_PROJECT);
            System.out.println("Not a project");
            return;
        }

        Project project = (Project) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();

        if(!project.isChanged()) {
            AppCore.getInstance().getMessageGenerator().generate(EventType.PROJECT_NOT_CHANGED);
            return;
        }

        String defaultPath = "src/main/resources/saved_projects/";
        project.setPath(defaultPath + project.getName() + ".json");
        if(project.getPath().equals(defaultPath + project.getName() + ".json")) {
            File file = new File(project.getPath());
            if(file.exists()) {
                JDialog dialog = new JDialog(MainFrame.getInstance(), "Save", true);
                dialog.setLayout(null);
                dialog.setSize(450, 150);
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(false);
                JLabel label = new JLabel("Project with this name already exists. Do you want to overwrite it?");
                JLabel label2 = new JLabel("If you don't want to overwrite it, please use Save As option or rename it.");
                JButton button = new JButton("Yes");
                JButton button2 = new JButton("No");
                //bounds for labels and buttons
                label.setBounds(10, 10, 450, 30);
                label2.setBounds(10, 40, 450, 30);
                button.setBounds(100, 80, 100, 30);
                button2.setBounds(250, 80, 100, 30);
                button2.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                button.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            System.out.println("Saving project" + project.getName());
                            AppCore.getInstance().getSerializer()
                                    .saveProject(project.getPath(), project);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        dialog.dispose();
                    }
                });
                dialog.add(label);
                dialog.add(label2);
                dialog.add(button);
                dialog.add(button2);
                dialog.setVisible(true);
                return;
            }
        }

        if (!project.isChanged()) {
            return;
        }


        try {
            System.out.println("Saving project" + project.getName());
            AppCore.getInstance().getSerializer()
                    .saveProject(project.getPath(), project);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}

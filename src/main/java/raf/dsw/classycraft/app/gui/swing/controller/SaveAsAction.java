package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SaveAsAction extends AbstractClassyAction {
    public SaveAsAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/saveas.png"));
        this.putValue(SHORT_DESCRIPTION, "Save As");
        this.putValue(NAME, "Save As");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Save action");
        JFileChooser jfc = new JFileChooser();

        if (!(MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode() instanceof Project)) {
            System.out.println("Not a project");
            return;
        }

        Project project = (Project) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
        File projectFile = null;

        if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            projectFile = jfc.getSelectedFile();
            project.setPath(projectFile.getPath() + ".json");
        } else {
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
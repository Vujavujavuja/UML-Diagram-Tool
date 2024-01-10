package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class OpenAction extends AbstractClassyAction{
    public OpenAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/open.png"));
        this.putValue(SHORT_DESCRIPTION, "Open");
        this.putValue(NAME, "Open");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Open action");
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open project");


        //default project path
        jfc.setCurrentDirectory(new java.io.File("src/main/resources/saved_projects/"));
        int chosen = jfc.showOpenDialog(null);
        if(chosen == JFileChooser.APPROVE_OPTION){
            System.out.println("Opening project" + jfc.getSelectedFile().getAbsolutePath());
            try {
                AppCore.getInstance().getSerializer().openProject(jfc.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                System.out.println("Error while opening project");
                throw new RuntimeException(ex);
            }
        }
    }
}

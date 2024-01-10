package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.serializer.ImageExporter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class ImageAction extends AbstractClassyAction {
    public ImageAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/image.png"));
        this.putValue(SHORT_DESCRIPTION, "Image");
        this.putValue(NAME, "Image");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getPackageView().getCurrentDiagramPanel() == null) {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NO_DIAGRAM_SELECTED);
            return;
        }
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Where to save image?");
        jfc.setCurrentDirectory(new java.io.File("src/main/resources/saved_images/"));
        String path = "";
        int chosen = jfc.showOpenDialog(null);
        if(chosen == JFileChooser.APPROVE_OPTION){
            System.out.println("Opening project" + jfc.getSelectedFile().getAbsolutePath());
            path = jfc.getSelectedFile().getAbsolutePath();
        }
        ImageExporter imageExporter = new ImageExporter();
        if(MainFrame.getInstance().getPackageView().getCurrentDiagramPanel() == null)
            AppCore.getInstance().getMessageGenerator().generate(EventType.NO_DIAGRAM_SELECTED);
        imageExporter.exportPanelToImage(MainFrame.getInstance().getPackageView().getCurrentDiagramPanel(), path + ".png");
    }
}

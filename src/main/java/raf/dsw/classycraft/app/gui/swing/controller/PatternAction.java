package raf.dsw.classycraft.app.gui.swing.controller;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PatternAction extends AbstractClassyAction{
    public PatternAction() {
        this.putValue(SMALL_ICON, loadIcon("/images/pattern.png"));
        this.putValue(SHORT_DESCRIPTION, "Pattern");
        this.putValue(NAME, "Pattern");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Pattern action");
        JDialog dialog = new JDialog();
        dialog.setTitle("Diagram pattern options");
        dialog.setLayout(new FlowLayout());
        dialog.setSize(300, 105);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null);

        JButton saveButton = new JButton("Save pattern");
        JButton loadButton = new JButton("Load pattern");
        JButton cancelButton = new JButton("Cancel");

        dialog.add(saveButton);
        dialog.add(loadButton);
        dialog.add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                System.out.println("Load pattern");

                JFrame frame = new JFrame();
                frame.setTitle("Pattern library");
                frame.setLayout(new BorderLayout());
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                JList<String> list = new JList<>(listModel);

                File patternsDir = new File("src/main/resources/saved_patterns/");
                if (patternsDir.exists() && patternsDir.isDirectory()) {
                    File[] files = patternsDir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            listModel.addElement(file.getName());
                        }
                    }
                } else {
                    System.err.println("Directory 'saved_patterns' not found.");
                }

                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");

                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String selected = list.getSelectedValue();
                        if (selected == null) {
                            return;
                        }
                        try {
                            Diagram d = (Diagram) AppCore.getInstance().getPatternSerializer().openProject("src/main/resources/saved_patterns/" + selected);
                            MainFrame.getInstance().getPackageView().diagramPattern(d.getDiagramElements());

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        frame.dispose();
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });

                frame.add(new JScrollPane(list), BorderLayout.CENTER);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(addButton);
                buttonPanel.add(cancelButton);
                frame.add(buttonPanel, BorderLayout.SOUTH);

                frame.setVisible(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                final String[] name = new String[1];
                JDialog nameDialog = new JDialog();
                nameDialog.setTitle("Save pattern");
                nameDialog.setLayout(new FlowLayout());
                nameDialog.setSize(300, 105);
                nameDialog.setModal(true);
                nameDialog.setLocationRelativeTo(null);
                JTextField nameField = new JTextField();
                nameField.setPreferredSize(new Dimension(200, 30));
                JButton save2Button = new JButton("Save");
                JButton cancel2Button = new JButton("Cancel");
                nameDialog.add(nameField);

                cancel2Button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        nameDialog.dispose();
                    }
                });
                save2Button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Save pattern");
                        nameDialog.dispose();
                        name[0] = nameField.getText();
                        nameDialog.dispose();
                        dialog.dispose();
                        System.out.println("Save pattern");
                        String defaultPath = "src/main/resources/saved_patterns/" + name[0] + ".json";
                        try {
                            AppCore.getInstance().getPatternSerializer().saveProject(defaultPath, MainFrame.getInstance().getPackageView().getDiagram());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                nameDialog.add(save2Button);
                nameDialog.add(cancel2Button);
                nameDialog.setVisible(true);
            }
        });

        dialog.setVisible(true);


    }
}

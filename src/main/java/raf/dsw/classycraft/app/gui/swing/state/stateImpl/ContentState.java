package raf.dsw.classycraft.app.gui.swing.state.stateImpl;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.commands.AddAtributeCommand;
import raf.dsw.classycraft.app.classyRepository.commands.AddSubElementCommand;
import raf.dsw.classycraft.app.classyRepository.commands.Command;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.controller.addAction.AddType;
import raf.dsw.classycraft.app.gui.swing.state.State;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.tabbedPane.PackageView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class ContentState implements State
{
    private int startX;
    private int startY;
    private Klasa klasa;
    private Interfejs interfejs;
    private UmlEnum umlEnum;
    private Connection connection;
    private AddType addType;
    private List<String> classAtributes = new ArrayList<>();
    private boolean hit = false;

    @Override
    public void execute(int x, int y, PackageView packageView) {

        packageView.setPanelCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private boolean isHit(InterClass interClass, int x, int y) {
        int startX = interClass.getPosition().getFirst(); //Gornja leva x koordinata
        int startY = interClass.getPosition().getSecond(); //Gornja leva y koordinata
        int endX = startX + interClass.getSize().getFirst(); //Donja desna x koordinata
        int endY = startY + interClass.getSize().getSecond(); //Donja desna y koordinata

        if(x >= startX && x <= endX && y >= startY && y <= endY){
            return true;
        }
        return false;

    }
    private boolean isLineHit(Connection connection, int x, int y){
        int startX = connection.getStart().getFirst();
        int startY = connection.getStart().getSecond();
        int endX = connection.getEnd().getFirst();
        int endY = connection.getEnd().getSecond();

        Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);

        float lineWidth = 10.0f;
        BasicStroke stroke = new BasicStroke(lineWidth);

        Shape wideLine = stroke.createStrokedShape(line);

        System.out.println(wideLine.intersects(x, y, 1, 1));
        return wideLine.intersects(x, y, 1, 1);
    }

    @Override
    public void stateMousePressed(int x, int y, PackageView packageView)
    {
        addType = AddType.PROJECT_ADD; //da ne baca null exceptiopn
        System.out.println("Add Field state");
        this.startX = x;
        this.startY = y;


        List<InterClass> selectableElements = new ArrayList<>();
        List<Connection> connections = new ArrayList<>();
        for (DiagramElement elem : packageView.currentDiagramElements())
        {
            if (elem instanceof InterClass)
            {
                InterClass interClass = (InterClass) elem;
                selectableElements.add(interClass);
            }
            else if(elem instanceof Connection){
                Connection connection = (Connection) elem;
                connections.add(connection);
            }
        }

        for(InterClass ic : selectableElements)
        {
            if(isHit(ic, x, y))
            {
                hit = true;
                System.out.println("Hit: " + ic.getName());

                if(ic.getType() == "Klasa") {
                    this.klasa = (Klasa) ic;
                    this.interfejs = null;
                    this.umlEnum = null;
                    popup();

                }

                else if(ic.getType() == "Interfejs") {
                    this.interfejs = (Interfejs) ic;
                    this.klasa = null;
                    this.umlEnum = null;
                    popupReduced();

                }
                else{
                    this.umlEnum = (UmlEnum) ic;
                    this.klasa = null;
                    this.interfejs = null;

                }

                break;
            }
        }
        for(Connection c : connections){
            System.out.println("Connection: " + c.getName());
            if(isLineHit(c, x, y)){
                hit = true;
                this.umlEnum = null;
                this.klasa = null;
                this.interfejs = null;
                this.connection = c;
                System.out.println("Hit: " + c.getName());
                break;
            }
        }

        if(!hit) {
            System.out.println(hit);
        }

        if(hit && this.connection != null){
            if(connection instanceof Agregacija) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Add Connection Dialog");
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    JTextField textField = new JTextField(connection.getName(), 20);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 2;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    frame.add(textField, gbc);

                    String[] fromOptions = {"0", "1"};
                    JComboBox<String> firstDropdown = new JComboBox<>(fromOptions);
                    firstDropdown.setSelectedItem(((Agregacija) connection).getFromMultiplicity());
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    frame.add(firstDropdown, gbc);

                    JLabel arrowLabel = new JLabel("--->");
                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    frame.add(arrowLabel, gbc);

                    String[] toOptions = {"1", "*"};
                    JComboBox<String> secondDropdown = new JComboBox<>(toOptions);
                    secondDropdown.setSelectedItem(((Agregacija) connection).getToMultiplicity());
                    gbc.gridx = 2;
                    gbc.gridy = 1;
                    frame.add(secondDropdown, gbc);




                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JButton button = new JButton("Confirm");
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ((Agregacija) connection).setFromMultiplicity((String) firstDropdown.getSelectedItem());
                            ((Agregacija) connection).setToMultiplicity((String) secondDropdown.getSelectedItem());
                            connection.setName(textField.getText());
                            packageView.setPanelPainters(new ArrayList<>());
                            packageView.setPanelSelectionPainters(new ArrayList<>());
                            packageView.panelRepaint();
                            packageView.panelOutsideRefresh();
                            frame.dispose();
                        }
                    });
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 3;
                    frame.add(button, gbc);
                });

            }
            else if(connection instanceof Kompozicija){
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Add Connection Dialog");
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    JTextField textField = new JTextField(connection.getName(), 20);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 2;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    frame.add(textField, gbc);

                    String[] fromOptions = {"0", "1"};
                    JComboBox<String> firstDropdown = new JComboBox<>(fromOptions);
                    firstDropdown.setSelectedItem(((Kompozicija) connection).getFromMultiplicity());
                    gbc.gridx = 0;
                    gbc.gridy = 1;
                    gbc.gridwidth = 1;
                    frame.add(firstDropdown, gbc);

                    JLabel arrowLabel = new JLabel("--->");
                    gbc.gridx = 1;
                    gbc.gridy = 1;
                    frame.add(arrowLabel, gbc);


                    String[] toOptions = {"1", "*"};
                    JComboBox<String> secondDropdown = new JComboBox<>(toOptions);
                    secondDropdown.setSelectedItem(((Kompozicija) connection).getToMultiplicity());
                    gbc.gridx = 2;
                    gbc.gridy = 1;
                    frame.add(secondDropdown, gbc);




                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JButton button = new JButton("Confirm");
                    button.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ((Kompozicija) connection).setFromMultiplicity((String) firstDropdown.getSelectedItem());
                            ((Kompozicija) connection).setToMultiplicity((String) secondDropdown.getSelectedItem());
                            connection.setName(textField.getText());
                            packageView.setPanelPainters(new ArrayList<>());
                            packageView.setPanelSelectionPainters(new ArrayList<>());
                            packageView.panelRepaint();
                            packageView.panelOutsideRefresh();
                            frame.dispose();
                        }
                    });
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 3;
                    frame.add(button, gbc);
                });
            }
            else if(connection instanceof Generalizacija){
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Add Connection Dialog");
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();

                    JTextField textField = new JTextField(connection.getName(), 20);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 2;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    frame.add(textField, gbc);



                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JButton button = new JButton("Confirm");
                    button.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            connection.setName(textField.getText());
                            packageView.setPanelPainters(new ArrayList<>());
                            packageView.setPanelSelectionPainters(new ArrayList<>());
                            packageView.panelRepaint();
                            packageView.panelOutsideRefresh();
                            frame.dispose();
                        }
                    });
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 3;
                    frame.add(button, gbc);
                });
            }
            else if (connection instanceof Zavisnost) {
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = new JFrame("Add Connection Dialog");
                    frame.setLayout(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();


                    JTextField textField = new JTextField(connection.getName(), 20);
                    gbc.gridx = 0;
                    gbc.gridy = 0;
                    gbc.gridwidth = 2;
                    gbc.insets = new Insets(10, 10, 10, 10);
                    frame.add(textField, gbc);


                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(400, 200);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    JButton button = new JButton("Confirm");
                    button.setAction(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            connection.setName(textField.getText());
                            packageView.setPanelPainters(new ArrayList<>());
                            packageView.setPanelSelectionPainters(new ArrayList<>());
                            packageView.panelRepaint();
                            packageView.panelOutsideRefresh();
                            frame.dispose();
                        }
                    });
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 3;
                    frame.add(button, gbc);
                });
            }
        }
        else if(hit && this.klasa != null && addType == AddType.ATRIBUTE_ADD){
            System.out.println(hit);

            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Add Field Dialog");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout());

                JPanel leftPanel = new JPanel(new GridBagLayout());
                leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel fieldNameLabel = new JLabel("Field name:");
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(150, 25));

                JLabel accessLevelLabel = new JLabel("Access Level:");
                String[] accessLevels = {"Private", "Protected", "Public", "Package private"};
                JComboBox<String> accessLevelChoiceBox = new JComboBox<>(accessLevels);
                accessLevelChoiceBox.setSelectedIndex(0);

                JLabel dataTypeLabel = new JLabel("Data Type:");
                // ChoiceBox for data types
                String[] dataTypes = {"int", "double", "String", "boolean", "char", "float", "long", "short", "byte"};
                JComboBox<String> dataTypeChoiceBox = new JComboBox<>(dataTypes);
                dataTypeChoiceBox.setSelectedIndex(0);

                JLabel staticLabel = new JLabel("Static:");
                String[] staticOptions = {"Non-Static", "Static"};
                JComboBox<String> staticChoiceBox = new JComboBox<>(staticOptions);
                staticChoiceBox.setSelectedIndex(0);

                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;

                leftPanel.add(fieldNameLabel, gbc);
                gbc.gridy++;
                leftPanel.add(textField, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(accessLevelLabel, gbc);
                gbc.gridy++;
                leftPanel.add(accessLevelChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(dataTypeLabel, gbc);
                gbc.gridy++;
                leftPanel.add(dataTypeChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(staticLabel, gbc);
                gbc.gridy++;
                leftPanel.add(staticChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(20, 0, 0, 0);
                leftPanel.add(addButton, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                leftPanel.add(cancelButton, gbc);

                JPanel rightPanel = new JPanel(new BorderLayout());

                JList<String> classFieldsList = new JList<>();
                classFieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                classFieldsList.setLayoutOrientation(JList.VERTICAL);
                classFieldsList.setVisibleRowCount(-1);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                classFieldsList.setModel(listModel);

                rightPanel.add(new JScrollPane(classFieldsList), BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel();
                JButton updateButton = new JButton("Update");
                JButton removeButton = new JButton("Remove");
                buttonsPanel.add(updateButton);
                buttonsPanel.add(removeButton);
                rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

                mainPanel.add(leftPanel, BorderLayout.WEST);
                mainPanel.add(rightPanel, BorderLayout.CENTER);

                frame.add(mainPanel);

                frame.setVisible(true);

                if(!this.klasa.getClassContents().isEmpty())
                {
                    for(ClassContent cc : this.klasa.getClassContents())
                    {
                        if(cc instanceof Atribut)
                        {
                            Atribut atribut = (Atribut) cc;
                            StringBuilder sb = new StringBuilder();

                            if(atribut.getVisibility() == Visibility.PRIVATE)
                                sb.append("- ");

                            else if(atribut.getVisibility() == Visibility.PROTECTED)
                                sb.append("# ");

                            else if(atribut.getVisibility() == Visibility.PUBLIC)
                                sb.append("+ ");

                            else if(atribut.getVisibility() == Visibility.PACKAGE_PRIVATE)
                                sb.append("~ ");

                            sb.append(atribut.getName() + ": ");

                            if(atribut.isStatic())
                                sb.append("static ");

                            sb.append(atribut.getDataType());

                            listModel.addElement(sb.toString());
                        }
                    }
                }

                cancelButton.addActionListener(e -> {
                    System.out.println("Cancel button clicked");
                    frame.dispose();
                });

                addButton.addActionListener(e -> {
                    StringBuilder sb = new StringBuilder(); //string builder za listModel

                    //parametri za konstruktor atributa
                    String nameOfField = textField.getText();
                    Visibility visibility = Visibility.PACKAGE_PRIVATE; //defaultna vrednost
                    Boolean isStatic = false;
                    String dataType = "int"; //defaultna vrednost

                    if(accessLevelChoiceBox.getSelectedItem().equals("Private"))
                    {
                        visibility = Visibility.PRIVATE;
                        sb.append("- ");
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Protected"))
                    {
                        sb.append("# ");
                        visibility = Visibility.PROTECTED;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Public"))
                    {
                        sb.append("+ ");
                        visibility = Visibility.PUBLIC;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Package private"))
                    {
                        sb.append("~ ");
                        visibility = Visibility.PACKAGE_PRIVATE;
                    }

                    sb.append(textField.getText() + ": ");

                    if(staticChoiceBox.getSelectedItem().equals("Static"))
                    {
                        isStatic = true;
                        sb.append("static ");
                    }

                    if(dataTypeChoiceBox.getSelectedItem().equals("int"))
                    {
                        dataType = "int";
                        sb.append("int");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("double"))
                    {
                        dataType = "double";
                        sb.append("double");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("String"))
                    {
                        dataType = "String";
                        sb.append("String");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("boolean"))
                    {
                        dataType = "boolean";
                        sb.append("boolean");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("char"))
                    {
                        dataType = "char";
                        sb.append("char");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("float"))
                    {
                        dataType = "float";
                        sb.append("float");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("long"))
                    {
                        dataType = "long";
                        sb.append("long");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("short"))
                    {
                        dataType = "short";
                        sb.append("short");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("byte"))
                    {
                        dataType = "byte";
                        sb.append("byte");
                    }

                    ClassContent atribut = new Atribut(nameOfField, visibility, isStatic, dataType);

                    if(this.klasa.getClassContents().isEmpty())
                    {
                        this.klasa.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(klasa, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (if)");
                    }

                    else if(this.klasa.getClassContents().contains(atribut))
                        AppCore.getInstance().getMessageGenerator().generate(EventType.FIELD_ALREADY_EXISTS);


                    else
                    {
                        this.klasa.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(klasa, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (else)");
                    }
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    System.out.println("Dodat novi atribut" + sb.toString());
                });

                classFieldsList.getSelectionModel().addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = classFieldsList.getSelectedIndex();

                        if (selectedIndex != -1)
                        {
                            String selectedData = listModel.getElementAt(selectedIndex);
                            System.out.println(selectedData);

                            String[] parts = selectedData.split("(): ");

                            if (parts.length == 2)
                            {
                                String[] atributeDetails1 = parts[0].split(" ");
                                String visibility = atributeDetails1[0];
                                String nameOfAtribute = atributeDetails1[1];

                                String[] atributeDetails2 = parts[1].split(" ");
                                String dataType = atributeDetails2[0];
                                boolean isStatic = false;

                                //atributeDetails2 ce imati duzinu = 2 kada je atribut static
                                if(atributeDetails2.length == 2)
                                {
                                    isStatic = true;
                                    dataType = atributeDetails2[1]; //ako je static, onda je drugi element u nizu dataType
                                }

                                //Podesavamo vrednost visibility choice box-a
                                if(visibility.equals("-"))
                                    accessLevelChoiceBox.setSelectedItem("Private");

                                else if(visibility.equals("#"))
                                    accessLevelChoiceBox.setSelectedItem("Protected");

                                else if(visibility.equals("+"))
                                    accessLevelChoiceBox.setSelectedItem("Public");

                                else if(visibility.equals("~"))
                                    accessLevelChoiceBox.setSelectedItem("Package private");

                                //Podesavamo vrednost dataType choice box-a
                                if(dataType.equals("int"))
                                    dataTypeChoiceBox.setSelectedItem("int");

                                else if(dataType.equals("double"))
                                    dataTypeChoiceBox.setSelectedItem("double");

                                else if(dataType.equals("String"))
                                    dataTypeChoiceBox.setSelectedItem("String");

                                else if(dataType.equals("boolean"))
                                    dataTypeChoiceBox.setSelectedItem("boolean");

                                else if(dataType.equals("char"))
                                    dataTypeChoiceBox.setSelectedItem("char");

                                else if(dataType.equals("float"))
                                    dataTypeChoiceBox.setSelectedItem("float");

                                else if(dataType.equals("long"))
                                    dataTypeChoiceBox.setSelectedItem("long");

                                else if(dataType.equals("short"))
                                    dataTypeChoiceBox.setSelectedItem("short");

                                else if(dataType.equals("byte"))
                                    dataTypeChoiceBox.setSelectedItem("byte");

                                //Podesavamo vrednost textField-a
                                textField.setText(nameOfAtribute);

                                //Podesavamo vrednost static choice box-a
                                if(isStatic)
                                    staticChoiceBox.setSelectedItem("Static");

                                else
                                    staticChoiceBox.setSelectedItem("Non-Static");
                            }
                        }
                    }
                });

                updateButton.addActionListener(e -> {
                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1) {
                        // Uzmi selektovani element iz liste
                        String selectedData = listModel.getElementAt(selectedIndex);

                        // Uzmi nove vrednosti iz komponenti
                        String newName = textField.getText();
                        String newVisibility = (String) accessLevelChoiceBox.getSelectedItem();
                        Visibility newVisibility1 = Visibility.PACKAGE_PRIVATE; //visibility koji cu proslediti konstruktoru atributa
                        String newDataType = (String) dataTypeChoiceBox.getSelectedItem();
                        boolean newIsStatic = staticChoiceBox.getSelectedItem().equals("Static");

                        if(newVisibility.equals("Private"))
                        {
                            newVisibility = "-";
                            newVisibility1 = Visibility.PRIVATE;
                        }

                        else if(newVisibility.equals("Protected"))
                        {
                            newVisibility = "#";
                            newVisibility1 = Visibility.PROTECTED;
                        }

                        else if(newVisibility.equals("Public"))
                        {
                            newVisibility = "+";
                            newVisibility1 = Visibility.PUBLIC;
                        }

                        else if(newVisibility.equals("Package private"))
                        {
                            newVisibility = "~";
                            newVisibility1 = Visibility.PACKAGE_PRIVATE;
                        }

                        //Pravimo novi string koji ce biti ubacen u listu
                        StringBuilder updatedString = new StringBuilder();
                        updatedString.append(newVisibility).append(" ");
                        updatedString.append(newName).append(": ");

                        if (newIsStatic)
                            updatedString.append("static ");

                        updatedString.append(newDataType);

                        //Updejtujemo listu
                        listModel.setElementAt(updatedString.toString(), selectedIndex);

                        //Updejtujemo listu classContents klase
                        this.klasa.updateClassContent(this.klasa.getClassContents().get(selectedIndex), new Atribut(newName, newVisibility1, newIsStatic, newDataType));
                    }

                    System.out.println("Update button clicked");
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                });

                removeButton.addActionListener(e -> {

                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1)
                    {
                        listModel.remove(selectedIndex);
                        this.klasa.deleteClassContent(this.klasa.getClassContents().get(selectedIndex));
                    }
                });
            });



        }
        else if(hit && this.klasa != null && addType.equals(AddType.METHOD_ADD)){
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Add Method Dialog");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout());

                JPanel leftPanel = new JPanel(new GridBagLayout());
                leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel fieldNameLabel = new JLabel("Method name:");
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(150, 25));

                JLabel accessLevelLabel = new JLabel("Access Level:");
                String[] accessLevels = {"Private", "Protected", "Public", "Package private"};
                JComboBox<String> accessLevelChoiceBox = new JComboBox<>(accessLevels);
                accessLevelChoiceBox.setSelectedIndex(0);

                JLabel dataTypeLabel = new JLabel("Return Type:");
                // ChoiceBox for data types
                String[] dataTypes = {"int", "double", "String", "boolean", "char", "float", "long", "short", "byte"};
                JComboBox<String> dataTypeChoiceBox = new JComboBox<>(dataTypes);
                dataTypeChoiceBox.setSelectedIndex(0);

                JLabel staticLabel = new JLabel("Static:");
                String[] staticOptions = {"Non-Static", "Static"};
                JComboBox<String> staticChoiceBox = new JComboBox<>(staticOptions);
                staticChoiceBox.setSelectedIndex(0);

                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;

                leftPanel.add(fieldNameLabel, gbc);
                gbc.gridy++;
                leftPanel.add(textField, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(accessLevelLabel, gbc);
                gbc.gridy++;
                leftPanel.add(accessLevelChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(dataTypeLabel, gbc);
                gbc.gridy++;
                leftPanel.add(dataTypeChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(staticLabel, gbc);
                gbc.gridy++;
                leftPanel.add(staticChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(20, 0, 0, 0);
                leftPanel.add(addButton, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                leftPanel.add(cancelButton, gbc);

                JPanel rightPanel = new JPanel(new BorderLayout());

                JList<String> classFieldsList = new JList<>();
                classFieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                classFieldsList.setLayoutOrientation(JList.VERTICAL);
                classFieldsList.setVisibleRowCount(-1);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                classFieldsList.setModel(listModel);

                rightPanel.add(new JScrollPane(classFieldsList), BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel();
                JButton updateButton = new JButton("Update");
                JButton removeButton = new JButton("Remove");
                buttonsPanel.add(updateButton);
                buttonsPanel.add(removeButton);
                rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

                mainPanel.add(leftPanel, BorderLayout.WEST);
                mainPanel.add(rightPanel, BorderLayout.CENTER);

                frame.add(mainPanel);

                frame.setVisible(true);

                if(!this.klasa.getClassContents().isEmpty())
                {
                    for(ClassContent cc : this.klasa.getClassContents())
                    {
                        if(cc instanceof Metod)
                        {
                            Metod metod = (Metod) cc;
                            StringBuilder sb = new StringBuilder();

                            if(metod.getVisibility() == Visibility.PRIVATE)
                                sb.append("- ");

                            else if(metod.getVisibility() == Visibility.PROTECTED)
                                sb.append("# ");

                            else if(metod.getVisibility() == Visibility.PUBLIC)
                                sb.append("+ ");

                            else if(metod.getVisibility() == Visibility.PACKAGE_PRIVATE)
                                sb.append("~ ");

                            sb.append(metod.getName() + "(): ");

                            if(metod.isStatic())
                                sb.append("static ");

                            sb.append(metod.getReturnType());

                            listModel.addElement(sb.toString());
                        }
                    }
                }

                cancelButton.addActionListener(e -> {
                    System.out.println("Cancel button clicked");
                    frame.dispose();
                });

                addButton.addActionListener(e -> {
                    StringBuilder sb = new StringBuilder(); //string builder za listModel

                    //parametri za konstruktor atributa
                    String nameOfField = textField.getText();
                    Visibility visibility = Visibility.PACKAGE_PRIVATE; //defaultna vrednost
                    Boolean isStatic = false;
                    String dataType = "int"; //defaultna vrednost

                    if(accessLevelChoiceBox.getSelectedItem().equals("Private"))
                    {
                        visibility = Visibility.PRIVATE;
                        sb.append("- ");
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Protected"))
                    {
                        sb.append("# ");
                        visibility = Visibility.PROTECTED;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Public"))
                    {
                        sb.append("+ ");
                        visibility = Visibility.PUBLIC;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Package private"))
                    {
                        sb.append("~ ");
                        visibility = Visibility.PACKAGE_PRIVATE;
                    }

                    sb.append(textField.getText() + "(): ");

                    if(staticChoiceBox.getSelectedItem().equals("Static"))
                    {
                        isStatic = true;
                        sb.append("static ");
                    }

                    if(dataTypeChoiceBox.getSelectedItem().equals("int"))
                    {
                        dataType = "int";
                        sb.append("int");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("double"))
                    {
                        dataType = "double";
                        sb.append("double");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("String"))
                    {
                        dataType = "String";
                        sb.append("String");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("boolean"))
                    {
                        dataType = "boolean";
                        sb.append("boolean");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("char"))
                    {
                        dataType = "char";
                        sb.append("char");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("float"))
                    {
                        dataType = "float";
                        sb.append("float");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("long"))
                    {
                        dataType = "long";
                        sb.append("long");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("short"))
                    {
                        dataType = "short";
                        sb.append("short");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("byte"))
                    {
                        dataType = "byte";
                        sb.append("byte");
                    }

                    ClassContent atribut = new Metod(nameOfField, visibility, isStatic, dataType, null);

                    if(this.klasa.getClassContents().isEmpty())
                    {
                        this.klasa.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(klasa, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (if)");
                    }

                    else if(this.klasa.getClassContents().contains(atribut))
                        AppCore.getInstance().getMessageGenerator().generate(EventType.FIELD_ALREADY_EXISTS);


                    else
                    {
                        this.klasa.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(klasa, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (else)");
                    }
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    System.out.println("Dodat novi atribut" + sb.toString());
                });

                classFieldsList.getSelectionModel().addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = classFieldsList.getSelectedIndex();

                        if (selectedIndex != -1)
                        {
                            String selectedData = listModel.getElementAt(selectedIndex);
                            System.out.println(selectedData);

                            String[] parts = selectedData.split("(): ");

                            if (parts.length == 2)
                            {
                                String[] atributeDetails1 = parts[0].split(" ");
                                String visibility = atributeDetails1[0];
                                String nameOfAtribute = atributeDetails1[1];

                                String[] atributeDetails2 = parts[1].split(" ");
                                String dataType = atributeDetails2[0];
                                boolean isStatic = false;

                                //atributeDetails2 ce imati duzinu = 2 kada je atribut static
                                if(atributeDetails2.length == 2)
                                {
                                    isStatic = true;
                                    dataType = atributeDetails2[1]; //ako je static, onda je drugi element u nizu dataType
                                }

                                //Podesavamo vrednost visibility choice box-a
                                if(visibility.equals("-"))
                                    accessLevelChoiceBox.setSelectedItem("Private");

                                else if(visibility.equals("#"))
                                    accessLevelChoiceBox.setSelectedItem("Protected");

                                else if(visibility.equals("+"))
                                    accessLevelChoiceBox.setSelectedItem("Public");

                                else if(visibility.equals("~"))
                                    accessLevelChoiceBox.setSelectedItem("Package private");

                                //Podesavamo vrednost dataType choice box-a
                                if(dataType.equals("int"))
                                    dataTypeChoiceBox.setSelectedItem("int");

                                else if(dataType.equals("double"))
                                    dataTypeChoiceBox.setSelectedItem("double");

                                else if(dataType.equals("String"))
                                    dataTypeChoiceBox.setSelectedItem("String");

                                else if(dataType.equals("boolean"))
                                    dataTypeChoiceBox.setSelectedItem("boolean");

                                else if(dataType.equals("char"))
                                    dataTypeChoiceBox.setSelectedItem("char");

                                else if(dataType.equals("float"))
                                    dataTypeChoiceBox.setSelectedItem("float");

                                else if(dataType.equals("long"))
                                    dataTypeChoiceBox.setSelectedItem("long");

                                else if(dataType.equals("short"))
                                    dataTypeChoiceBox.setSelectedItem("short");

                                else if(dataType.equals("byte"))
                                    dataTypeChoiceBox.setSelectedItem("byte");

                                //Podesavamo vrednost textField-a
                                textField.setText(nameOfAtribute);

                                //Podesavamo vrednost static choice box-a
                                if(isStatic)
                                    staticChoiceBox.setSelectedItem("Static");

                                else
                                    staticChoiceBox.setSelectedItem("Non-Static");
                            }
                        }
                    }
                });

                updateButton.addActionListener(e -> {
                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1) {
                        // Uzmi selektovani element iz liste
                        String selectedData = listModel.getElementAt(selectedIndex);

                        // Uzmi nove vrednosti iz komponenti
                        String newName = textField.getText();
                        String newVisibility = (String) accessLevelChoiceBox.getSelectedItem();
                        Visibility newVisibility1 = Visibility.PACKAGE_PRIVATE; //visibility koji cu proslediti konstruktoru atributa
                        String newDataType = (String) dataTypeChoiceBox.getSelectedItem();
                        boolean newIsStatic = staticChoiceBox.getSelectedItem().equals("Static");

                        if(newVisibility.equals("Private"))
                        {
                            newVisibility = "-";
                            newVisibility1 = Visibility.PRIVATE;
                        }

                        else if(newVisibility.equals("Protected"))
                        {
                            newVisibility = "#";
                            newVisibility1 = Visibility.PROTECTED;
                        }

                        else if(newVisibility.equals("Public"))
                        {
                            newVisibility = "+";
                            newVisibility1 = Visibility.PUBLIC;
                        }

                        else if(newVisibility.equals("Package private"))
                        {
                            newVisibility = "~";
                            newVisibility1 = Visibility.PACKAGE_PRIVATE;
                        }

                        //Pravimo novi string koji ce biti ubacen u listu
                        StringBuilder updatedString = new StringBuilder();
                        updatedString.append(newVisibility).append(" ");
                        updatedString.append(newName).append("(): ");

                        if (newIsStatic)
                            updatedString.append("static ");

                        updatedString.append(newDataType);

                        //Updejtujemo listu
                        listModel.setElementAt(updatedString.toString(), selectedIndex);

                        //Updejtujemo listu classContents klase
                        this.klasa.updateClassContent(this.klasa.getClassContents().get(selectedIndex), new Metod(newName, newVisibility1, newIsStatic, newDataType, null));
                    }
                    System.out.println("Update button clicked");
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                });

                removeButton.addActionListener(e -> {

                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1)
                    {
                        listModel.remove(selectedIndex);
                        this.klasa.deleteClassContent(this.klasa.getClassContents().get(selectedIndex));
                    }
                });
            });



        }
        else if(hit && this.interfejs != null){
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Add Method Dialog");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout());

                JPanel leftPanel = new JPanel(new GridBagLayout());
                leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel fieldNameLabel = new JLabel("Method name:");
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(150, 25));

                JLabel accessLevelLabel = new JLabel("Access Level:");
                String[] accessLevels = {"Private", "Protected", "Public", "Package private"};
                JComboBox<String> accessLevelChoiceBox = new JComboBox<>(accessLevels);
                accessLevelChoiceBox.setSelectedIndex(0);

                JLabel dataTypeLabel = new JLabel("Return Type:");
                // ChoiceBox for data types
                String[] dataTypes = {"int", "double", "String", "boolean", "char", "float", "long", "short", "byte"};
                JComboBox<String> dataTypeChoiceBox = new JComboBox<>(dataTypes);
                dataTypeChoiceBox.setSelectedIndex(0);

                JLabel staticLabel = new JLabel("Static:");
                String[] staticOptions = {"Non-Static", "Static"};
                JComboBox<String> staticChoiceBox = new JComboBox<>(staticOptions);
                staticChoiceBox.setSelectedIndex(0);

                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;

                leftPanel.add(fieldNameLabel, gbc);
                gbc.gridy++;
                leftPanel.add(textField, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(accessLevelLabel, gbc);
                gbc.gridy++;
                leftPanel.add(accessLevelChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(dataTypeLabel, gbc);
                gbc.gridy++;
                leftPanel.add(dataTypeChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(10, 0, 0, 0);
                leftPanel.add(staticLabel, gbc);
                gbc.gridy++;
                leftPanel.add(staticChoiceBox, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(20, 0, 0, 0);
                leftPanel.add(addButton, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                leftPanel.add(cancelButton, gbc);

                JPanel rightPanel = new JPanel(new BorderLayout());

                JList<String> classFieldsList = new JList<>();
                classFieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                classFieldsList.setLayoutOrientation(JList.VERTICAL);
                classFieldsList.setVisibleRowCount(-1);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                classFieldsList.setModel(listModel);

                rightPanel.add(new JScrollPane(classFieldsList), BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel();
                JButton updateButton = new JButton("Update");
                JButton removeButton = new JButton("Remove");
                buttonsPanel.add(updateButton);
                buttonsPanel.add(removeButton);
                rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

                mainPanel.add(leftPanel, BorderLayout.WEST);
                mainPanel.add(rightPanel, BorderLayout.CENTER);

                frame.add(mainPanel);

                frame.setVisible(true);

                if(!this.interfejs.getMetods().isEmpty())
                {
                    for(ClassContent cc : this.interfejs.getMetods())
                    {
                        if(cc instanceof Atribut)
                        {
                            Atribut atribut = (Atribut) cc;
                            StringBuilder sb = new StringBuilder();

                            if(atribut.getVisibility() == Visibility.PRIVATE)
                                sb.append("- ");

                            else if(atribut.getVisibility() == Visibility.PROTECTED)
                                sb.append("# ");

                            else if(atribut.getVisibility() == Visibility.PUBLIC)
                                sb.append("+ ");

                            else if(atribut.getVisibility() == Visibility.PACKAGE_PRIVATE)
                                sb.append("~ ");

                            sb.append(atribut.getName() + "(): ");

                            if(atribut.isStatic())
                                sb.append("static ");

                            sb.append(atribut.getDataType());

                            listModel.addElement(sb.toString());
                        }
                    }
                }

                cancelButton.addActionListener(e -> {
                    System.out.println("Cancel button clicked");
                    frame.dispose();
                });

                addButton.addActionListener(e -> {
                    StringBuilder sb = new StringBuilder(); //string builder za listModel

                    //parametri za konstruktor atributa
                    String nameOfField = textField.getText();
                    Visibility visibility = Visibility.PACKAGE_PRIVATE; //defaultna vrednost
                    Boolean isStatic = false;
                    String dataType = "int"; //defaultna vrednost

                    if(accessLevelChoiceBox.getSelectedItem().equals("Private"))
                    {
                        visibility = Visibility.PRIVATE;
                        sb.append("- ");
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Protected"))
                    {
                        sb.append("# ");
                        visibility = Visibility.PROTECTED;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Public"))
                    {
                        sb.append("+ ");
                        visibility = Visibility.PUBLIC;
                    }

                    else if(accessLevelChoiceBox.getSelectedItem().equals("Package private"))
                    {
                        sb.append("~ ");
                        visibility = Visibility.PACKAGE_PRIVATE;
                    }

                    sb.append(textField.getText() + "(): ");

                    if(staticChoiceBox.getSelectedItem().equals("Static"))
                    {
                        isStatic = true;
                        sb.append("static ");
                    }

                    if(dataTypeChoiceBox.getSelectedItem().equals("int"))
                    {
                        dataType = "int";
                        sb.append("int");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("double"))
                    {
                        dataType = "double";
                        sb.append("double");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("String"))
                    {
                        dataType = "String";
                        sb.append("String");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("boolean"))
                    {
                        dataType = "boolean";
                        sb.append("boolean");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("char"))
                    {
                        dataType = "char";
                        sb.append("char");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("float"))
                    {
                        dataType = "float";
                        sb.append("float");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("long"))
                    {
                        dataType = "long";
                        sb.append("long");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("short"))
                    {
                        dataType = "short";
                        sb.append("short");
                    }

                    else if(dataTypeChoiceBox.getSelectedItem().equals("byte"))
                    {
                        dataType = "byte";
                        sb.append("byte");
                    }

                    ClassContent atribut = new Metod(nameOfField, visibility, isStatic, dataType, null);

                    if(this.interfejs.getMetods().isEmpty())
                    {
                        this.interfejs.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(interfejs, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (if)");
                    }

                    else if(this.interfejs.getMetods().contains(atribut))
                        AppCore.getInstance().getMessageGenerator().generate(EventType.FIELD_ALREADY_EXISTS);


                    else
                    {
                        this.interfejs.addClassContent(atribut); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(interfejs, atribut);
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (else)");
                    }
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    System.out.println("Dodat novi atribut" + sb.toString());
                });

                classFieldsList.getSelectionModel().addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = classFieldsList.getSelectedIndex();

                        if (selectedIndex != -1)
                        {
                            String selectedData = listModel.getElementAt(selectedIndex);
                            System.out.println(selectedData);

                            String[] parts = selectedData.split("(): ");

                            if (parts.length == 2)
                            {
                                String[] atributeDetails1 = parts[0].split(" ");
                                String visibility = atributeDetails1[0];
                                String nameOfAtribute = atributeDetails1[1];

                                String[] atributeDetails2 = parts[1].split(" ");
                                String dataType = atributeDetails2[0];
                                boolean isStatic = false;

                                //atributeDetails2 ce imati duzinu = 2 kada je atribut static
                                if(atributeDetails2.length == 2)
                                {
                                    isStatic = true;
                                    dataType = atributeDetails2[1]; //ako je static, onda je drugi element u nizu dataType
                                }

                                //Podesavamo vrednost visibility choice box-a
                                if(visibility.equals("-"))
                                    accessLevelChoiceBox.setSelectedItem("Private");

                                else if(visibility.equals("#"))
                                    accessLevelChoiceBox.setSelectedItem("Protected");

                                else if(visibility.equals("+"))
                                    accessLevelChoiceBox.setSelectedItem("Public");

                                else if(visibility.equals("~"))
                                    accessLevelChoiceBox.setSelectedItem("Package private");

                                //Podesavamo vrednost dataType choice box-a
                                if(dataType.equals("int"))
                                    dataTypeChoiceBox.setSelectedItem("int");

                                else if(dataType.equals("double"))
                                    dataTypeChoiceBox.setSelectedItem("double");

                                else if(dataType.equals("String"))
                                    dataTypeChoiceBox.setSelectedItem("String");

                                else if(dataType.equals("boolean"))
                                    dataTypeChoiceBox.setSelectedItem("boolean");

                                else if(dataType.equals("char"))
                                    dataTypeChoiceBox.setSelectedItem("char");

                                else if(dataType.equals("float"))
                                    dataTypeChoiceBox.setSelectedItem("float");

                                else if(dataType.equals("long"))
                                    dataTypeChoiceBox.setSelectedItem("long");

                                else if(dataType.equals("short"))
                                    dataTypeChoiceBox.setSelectedItem("short");

                                else if(dataType.equals("byte"))
                                    dataTypeChoiceBox.setSelectedItem("byte");

                                //Podesavamo vrednost textField-a
                                textField.setText(nameOfAtribute);

                                //Podesavamo vrednost static choice box-a
                                if(isStatic)
                                    staticChoiceBox.setSelectedItem("Static");

                                else
                                    staticChoiceBox.setSelectedItem("Non-Static");
                            }
                        }
                    }
                });

                updateButton.addActionListener(e -> {
                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1) {
                        // Uzmi selektovani element iz liste
                        String selectedData = listModel.getElementAt(selectedIndex);

                        // Uzmi nove vrednosti iz komponenti
                        String newName = textField.getText();
                        String newVisibility = (String) accessLevelChoiceBox.getSelectedItem();
                        Visibility newVisibility1 = Visibility.PACKAGE_PRIVATE; //visibility koji cu proslediti konstruktoru atributa
                        String newDataType = (String) dataTypeChoiceBox.getSelectedItem();
                        boolean newIsStatic = staticChoiceBox.getSelectedItem().equals("Static");

                        if(newVisibility.equals("Private"))
                        {
                            newVisibility = "-";
                            newVisibility1 = Visibility.PRIVATE;
                        }

                        else if(newVisibility.equals("Protected"))
                        {
                            newVisibility = "#";
                            newVisibility1 = Visibility.PROTECTED;
                        }

                        else if(newVisibility.equals("Public"))
                        {
                            newVisibility = "+";
                            newVisibility1 = Visibility.PUBLIC;
                        }

                        else if(newVisibility.equals("Package private"))
                        {
                            newVisibility = "~";
                            newVisibility1 = Visibility.PACKAGE_PRIVATE;
                        }

                        //Pravimo novi string koji ce biti ubacen u listu
                        StringBuilder updatedString = new StringBuilder();
                        updatedString.append(newVisibility).append(" ");
                        updatedString.append(newName).append("(): ");

                        if (newIsStatic)
                            updatedString.append("static ");

                        updatedString.append(newDataType);

                        //Updejtujemo listu
                        listModel.setElementAt(updatedString.toString(), selectedIndex);

                        //Updejtujemo listu classContents klase
                        this.interfejs.updateClassContent(this.interfejs.getMetods().get(selectedIndex), new Metod(newName, newVisibility1, newIsStatic, newDataType, null));
                    }
                    System.out.println("Update button clicked");
                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                });

                removeButton.addActionListener(e -> {

                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1)
                    {
                        listModel.remove(selectedIndex);
                        this.interfejs.deleteClassContent(this.interfejs.getMetods().get(selectedIndex));
                    }
                });
            });
        }
        else if(hit && this.umlEnum != null){
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Add Constant Dialog");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);

                JPanel mainPanel = new JPanel(new BorderLayout());

                JPanel leftPanel = new JPanel(new GridBagLayout());
                leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel fieldNameLabel = new JLabel("Enum type:");
                JTextField textField = new JTextField();
                textField.setPreferredSize(new Dimension(150, 25));

                JButton addButton = new JButton("Add");
                JButton cancelButton = new JButton("Cancel");

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.anchor = GridBagConstraints.WEST;

                leftPanel.add(fieldNameLabel, gbc);
                gbc.gridy++;
                leftPanel.add(textField, gbc);

                gbc.gridy++;
                gbc.insets = new Insets(20, 0, 0, 0);
                leftPanel.add(addButton, gbc);
                gbc.gridx = 0;
                gbc.gridy++;
                leftPanel.add(cancelButton, gbc);

                JPanel rightPanel = new JPanel(new BorderLayout());

                JList<String> classFieldsList = new JList<>();
                classFieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                classFieldsList.setLayoutOrientation(JList.VERTICAL);
                classFieldsList.setVisibleRowCount(-1);

                DefaultListModel<String> listModel = new DefaultListModel<>();
                classFieldsList.setModel(listModel);

                rightPanel.add(new JScrollPane(classFieldsList), BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel();
                JButton removeButton = new JButton("Remove");
                buttonsPanel.add(removeButton);
                rightPanel.add(buttonsPanel, BorderLayout.SOUTH);

                mainPanel.add(leftPanel, BorderLayout.WEST);
                mainPanel.add(rightPanel, BorderLayout.CENTER);

                frame.add(mainPanel);

                frame.setVisible(true);


                cancelButton.addActionListener(e -> {
                    System.out.println("Cancel button clicked");
                    frame.dispose();
                });

                addButton.addActionListener(e -> {
                    StringBuilder sb = new StringBuilder(); //string builder za listModel

                    sb.append(textField.getText());

                    if(this.umlEnum.getConstants().isEmpty())
                    {
                        this.umlEnum.getConstants().add(textField.getText()); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(umlEnum, new Atribut(textField.getText(), Visibility.PUBLIC, false, "int"));
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (if)");
                    }

                    else if(this.umlEnum.getConstants().contains(textField.getText()))
                        AppCore.getInstance().getMessageGenerator().generate(EventType.FIELD_ALREADY_EXISTS);
                    else {
                        this.umlEnum.getConstants().add(textField.getText()); //selektovanoj klasi dodajemo atribut
                        listModel.addElement(sb.toString()); //dodajemo atribut u listu
                        Command newCommand = new AddAtributeCommand(umlEnum, new Atribut(textField.getText(), Visibility.PUBLIC, false, "int"));
                        packageView.getCurrentDiagramPanel().getDiagram().getCommandManager().addCommand(newCommand);
                        System.out.println("Dodat atribut u listu classContents klase (else)");
                    }

                    packageView.setPanelPainters(new ArrayList<>());
                    packageView.setPanelSelectionPainters(new ArrayList<>());
                    packageView.panelRepaint();
                    packageView.panelOutsideRefresh();
                    System.out.println("Dodat novi atribut" + sb.toString());
                });

                classFieldsList.getSelectionModel().addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = classFieldsList.getSelectedIndex();

                        if (selectedIndex != -1)
                        {
                            String selectedData = listModel.getElementAt(selectedIndex);
                            System.out.println(selectedData);


                            }
                    }

                });

                removeButton.addActionListener(e -> {

                    int selectedIndex = classFieldsList.getSelectedIndex();

                    if (selectedIndex != -1)
                    {
                        listModel.remove(selectedIndex);
                        this.umlEnum.getConstants().remove(this.umlEnum.getConstants().get(selectedIndex));
                    }
                });
            });
        }
        else{
            System.out.println("MISS");
        }
        hit = false;

    }

    @Override
    public void stateMouseDragged(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateMouseReleased(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMouseDragged(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMousePressed(int x, int y, PackageView packageView) {

    }

    @Override
    public void stateRightMouseReleased(int x, int y, PackageView packageView) {

    }

    public void popup(){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAddField = new JButton("Add Field");
        JButton btnAddMethod = new JButton("Add Method");

        btnAddField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addType = AddType.ATRIBUTE_ADD;
                System.out.println("Add Field button pressed");
            }
        });

        btnAddMethod.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                addType = AddType.METHOD_ADD;
                System.out.println("Add Method button pressed");
            }
        });

        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(btnAddField);
        toolBar.addSeparator();
        toolBar.add(btnAddMethod);
        toolBar.add(Box.createHorizontalGlue());

        JOptionPane.showMessageDialog(MainFrame.getInstance(), toolBar, "Add Content", JOptionPane.PLAIN_MESSAGE);
    }
    public void popupReduced(){
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnAddMethod = new JButton("Add Method");

        btnAddMethod.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                System.out.println("Add Method button pressed");
                addType = AddType.METHOD_ADD;
            }
        });

        toolBar.add(Box.createHorizontalGlue());
        toolBar.addSeparator();
        toolBar.add(btnAddMethod);
        toolBar.add(Box.createHorizontalGlue());

        JOptionPane.showMessageDialog(MainFrame.getInstance(), toolBar, "Add Content", JOptionPane.PLAIN_MESSAGE);
    }


}

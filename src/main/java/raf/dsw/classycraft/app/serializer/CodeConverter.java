package raf.dsw.classycraft.app.serializer;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Connection;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.InterClass;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.Visibility;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.core.eventHandler.EventType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeConverter{
    Project project;
    public CodeConverter(Project project) {
        this.project = project;
    }
    public void convert(){
        String parentDirPath = "src/main/resources/saved_codes/";
        String codeDirName = project.getName();
        File parentDir = new File(parentDirPath);
        File newFolder = new File(parentDir, codeDirName);

        checkName(parentDir, newFolder, codeDirName);

        boolean created = newFolder.mkdir();
        if (created) {
            createReadMe(newFolder);
            createSubFolderTree(newFolder, project);
            AppCore.getInstance().getMessageGenerator().generate(EventType.CODE_GENERATED);
        } else {
            if(!newFolder.exists()) {
                AppCore.getInstance().getMessageGenerator().generate(EventType.UNKNOWN);
                return;
            }
            else {
                AppCore.getInstance().getMessageGenerator().generate(EventType.CODE_ALREADY_EXISTS);
                return;
            }
        }

    }
    private void createReadMe(File newFolder){
        String readMePath = newFolder.getPath() + "/README.md";
        File readMe = new File(readMePath);
        String readMeContent = "# Project name: " + project.getName() + "\n\n# Project author: " + project.getAuthor();
        try {
            readMe.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(readMe))) {
                writer.write(readMeContent);
                System.out.println("README.md file created and updated successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the README.md file.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while creating the README.md file.");
            e.printStackTrace();
        }
    }
    private void checkName(File parentDir, File newFolder, String codeDirName){
        if(newFolder.exists()) {
            newFolder = (new File(parentDir, codeDirName + "(1)"));
        }
    }
    private void createSubFolderTree(File newFolder, Project project){
        File resourcesFolder = new File(newFolder, "resources");
        File mainFolder = new File(newFolder, "main");
        resourcesFolder.mkdir();
        mainFolder.mkdir();

        for (ClassyNode p : project.getChildren()) {
            if(p instanceof ClassyNodeComposite){
                ClassyNodeComposite pk = (ClassyNodeComposite) p;
                if(pk instanceof Package){
                    Package pkg = (Package) pk;
                    createPackageFolder(mainFolder, pkg, "");
                }
            }
        }
    }
    private void createPackageFolder(File parentFolder, Package pkg, String parentPackagePath) {
        File packageFolder = new File(parentFolder, pkg.getName());
        packageFolder.mkdir();

        String currentPackagePath = parentPackagePath.isEmpty() ? pkg.getName() : parentPackagePath + "." + pkg.getName();

        for (Object child : pkg.getChildren()) {
            if (child instanceof Package) {
                createPackageFolder(packageFolder, (Package) child, currentPackagePath);
            } else if (child instanceof Diagram) {
                createJavaFile(packageFolder, (Diagram) child, currentPackagePath);
            }

        }
    }
    private void createJavaFile(File packageFolder, Diagram diagram, String packagePath) {
        for(DiagramElement element: diagram.getDiagramElements()) {
            if(!(element instanceof InterClass)) continue;
            else {
                String fn = element.getName().split(":")[1];
                String fileName = fn + ".java";
                File javaFile = new File(packageFolder, fileName);

                try {
                    javaFile.createNewFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(javaFile))) {
                        writer.write("package " + packagePath + ";\n\n");
                        writer.write("// Java file for diagram: " + diagram.getName() + "\n");
                        writer.write(javaCode((InterClass) element, diagram));
                        System.out.println("Created Java file: " + fileName);
                    }
                } catch (IOException e) {
                    System.err.println("Error while creating/writing to file: " + fileName);
                    e.printStackTrace();
                }
            }
        }
    }
    private String relevantConnections(InterClass element, Diagram diagram){
        StringBuilder sb = new StringBuilder();
        List<Connection> connections = new ArrayList<>();
        for(DiagramElement de: diagram.getDiagramElements()){
            if(de instanceof Connection){
                Connection c = (Connection) de;
                if(c.getFromElement().equals(element) || c.getToElement().equals(element))
                    connections.add(c);
            }
        }
        for(Connection c: connections){
            if(c instanceof Agregacija){
                sb.append("A-");
                if(c.getFromElement().equals(element)) {
                    sb.append("F/");
                    sb.append(c.getFromElement().getName().split(":")[1]);
                }
                else {
                    sb.append("T");
                }
            }
            else if(c instanceof Kompozicija){
                sb.append("K-");
                if(c.getFromElement().equals(element)) {
                    sb.append("F/");
                    sb.append(c.getFromElement().getName().split(":")[1]);
                }
                else {
                    sb.append("T");}
            }
           else if(c instanceof Generalizacija){
                sb.append("G-");
                if(c.getFromElement().equals(element))
                    sb.append("F");
                else {
                    sb.append("T/");
                    sb.append(c.getFromElement().getName().split(":")[1]);
                }
            }
           else if(c instanceof Zavisnost){
                sb.append("Z-");
                if(c.getFromElement().equals(element))
                    sb.append("F");
                else sb.append("T");
            }
           sb.append("\n");
        }
        return sb.toString();
    }
    private String javaCode(InterClass element,Diagram diagram){
        int type = 0; // 1=class, 2=interface, 3=enum
        StringBuilder sb = new StringBuilder();
        String identificator = "";
        if(element instanceof Klasa){
            identificator = "class ";
            type = 1;
        }
        else if(element instanceof Interfejs){
            identificator = "interface ";
            type = 2;
        }
        else{
            identificator = "enum ";
            type = 3;
        }
        String conModifier = "";
        String checker = relevantConnections(element, diagram);
        System.out.println(checker);
        if(checker.contains("G-T")){
            System.out.println(checker);
            if(checker.contains("T")) {
                conModifier = "extends " + checker.split("/")[1];
                conModifier = conModifier.split("\n")[0];
            }
        }
        //Kao naslovni deo
        sb.append("public " + identificator + element.getName().split(":")[1] + " " +conModifier + " {\n");

        //Kod za atribute prvo
        if(type == 1) {
            Klasa tmpK = (Klasa) element;
            if(checker.contains("A-F")){
                sb.append("\t");
                String tmp = checker.split("/")[1];
                tmp = tmp.substring(0, tmp.length() - 1);
                sb.append("private " + tmp.toUpperCase() + " " + tmp.toLowerCase() + ";\n");
            }
            if(checker.contains("K-F")){
                sb.append("\t");
                String tmp = checker.split("/")[1];
                tmp = tmp.substring(0, tmp.length() - 1);
                sb.append("private " + tmp.toUpperCase() + " " + tmp.toLowerCase() + ";\n");
            }
            for (ClassContent cc : tmpK.getClassContents()) {
                if (cc instanceof Atribut) {
                    Atribut tmpA = (Atribut) cc;
                    sb.append("\t");
                    if (tmpA.getVisibility().equals(Visibility.PRIVATE)) {
                        sb.append("private ");
                    } else if (tmpA.getVisibility().equals(Visibility.PROTECTED)) {
                        sb.append("protected ");
                    } else if (tmpA.getVisibility().equals(Visibility.PUBLIC)) {
                        sb.append("public ");
                    } else if (tmpA.getVisibility().equals(Visibility.PACKAGE_PRIVATE)) {
                        sb.append("default private ");
                    }
                    sb.append(tmpA.getDataType() + " " + tmpA.getName() + ";\n");
                }
            }
            for (ClassContent cc : tmpK.getClassContents()) {
                if (cc instanceof Metod) {
                    Metod tmpM = (Metod) cc;
                    sb.append("\t");
                    if (tmpM.getVisibility().equals(Visibility.PRIVATE)) {
                        sb.append("private ");
                    } else if (tmpM.getVisibility().equals(Visibility.PROTECTED)) {
                        sb.append("protected ");
                    } else if (tmpM.getVisibility().equals(Visibility.PUBLIC)) {
                        sb.append("public ");
                    } else if (tmpM.getVisibility().equals(Visibility.PACKAGE_PRIVATE)) {
                        sb.append("default private ");
                    }
                    sb.append(tmpM.getReturnType() + " " + tmpM.getName() + "() {\n");
                    sb.append("\t\t//CODE HERE\n");
                    sb.append("\t}\n\n");
                }
            }
        }
        else if(type == 3){
            UmlEnum tmpE = (UmlEnum) element;
            for (String ec : tmpE.getConstants()) {
                sb.append("\t");
                sb.append(ec + ",\n");
            }
            sb.append("\n");
        }
        else if(type == 2){
            Interfejs tmpI = (Interfejs) element;
            for (ClassContent cc : tmpI.getMetods()) {
                Metod tmpM = (Metod) cc;
                sb.append("\t");
                if (tmpM.getVisibility().equals(Visibility.PRIVATE)) {
                    sb.append("private ");
                } else if (tmpM.getVisibility().equals(Visibility.PROTECTED)) {
                    sb.append("protected ");
                } else if (tmpM.getVisibility().equals(Visibility.PUBLIC)) {
                    sb.append("public ");
                } else if (tmpM.getVisibility().equals(Visibility.PACKAGE_PRIVATE)) {
                    sb.append("default private ");
                }
                sb.append(tmpM.getReturnType() + " " + tmpM.getName() + "() {\n");
                sb.append("\t\t//CODE HERE\n");
                sb.append("\t}\n\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }
}


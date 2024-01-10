package raf.dsw.classycraft.app.serializer;

import raf.dsw.classycraft.app.AppCore;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Agregacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Generalizacija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Kompozicija;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.connectionSubElements.Zavisnost;
import raf.dsw.classycraft.app.classyRepository.implementation.subElements.interClassSubElements.*;
import raf.dsw.classycraft.app.core.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.dsw.classycraft.app.core.eventHandler.EventBus;
import raf.dsw.classycraft.app.core.eventHandler.EventType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.serializer.customDeserializers.*;
import raf.dsw.classycraft.app.serializer.customSerializers.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JacksonSerializer implements Serializer {

    private final ObjectMapper objectMapper;

    public JacksonSerializer(ProjectExplorer projectExplorer) {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        SimpleModule module = new SimpleModule();

        // Serializers
        module.addSerializer(Project.class, new ProjectSerializer(this.objectMapper));
        module.addSerializer(Package.class, new PackageSerializer());
        module.addSerializer(Diagram.class, new DiagramSerializer());
        module.addSerializer(Agregacija.class, new AgregacijaSerializer());
        module.addSerializer(Generalizacija.class, new GeneralizacijaSerializer());
        module.addSerializer(Kompozicija.class, new KompozicijaSerializer());
        module.addSerializer(Zavisnost.class, new ZavisnostSerializer());
        module.addSerializer(Klasa.class, new KlasaSerializer());
        module.addSerializer(Interfejs.class, new InterfejsSerializer());
        module.addSerializer(UmlEnum.class, new EnumSerializer());
        module.addSerializer(Metod.class, new MetodaSerializer());
        module.addSerializer(Atribut.class, new AtributSerializer());

        // Deserializers
        module.addDeserializer(Project.class, new ProjectDeserializer(projectExplorer));
        module.addDeserializer(Package.class, new PackageDeserializer());
        module.addDeserializer(Diagram.class, new DiagramDeserializer());
        module.addDeserializer(Agregacija.class, new AgregacijaDeserializer());
        module.addDeserializer(Generalizacija.class, new GeneralizacijaDeserializer());
        module.addDeserializer(Kompozicija.class, new KompozicijaDeserializer());
        module.addDeserializer(Zavisnost.class, new ZavisnostDeserializer());
        module.addDeserializer(Klasa.class, new KlasaDeserializer());
        module.addDeserializer(Interfejs.class, new InterfejsDeserializer());
        module.addDeserializer(UmlEnum.class, new EnumDeserializer());
        module.addDeserializer(Metod.class, new MetodaDeserializer());
        module.addDeserializer(Atribut.class, new AtributDeserializer());

        objectMapper.registerModule(module);
    }

    @Override
    public void saveProject(String filePath, ClassyNode project) throws IOException {
        if(!(project instanceof Project)) {
            AppCore.getInstance().getMessageGenerator().generate(EventType.NOT_PROJECT);
            return;
        }
        else if(!((Project) project).isChanged()) {
            System.out.println("Project not changed");
            AppCore.getInstance().getMessageGenerator().generate(EventType.PROJECT_NOT_CHANGED);
            return;
        }
        ((Project) project).setChanged(false);
        objectMapper.writeValue(new File(filePath),  project);
        AppCore.getInstance().getMessageGenerator().generate(EventType.PROJECT_SAVED);
    }

    @Override
    public Object openProject(String filePath) throws IOException {
        System.out.println("Opening project: " + filePath);
        Project project = objectMapper.readValue(new FileReader(filePath), Project.class);
        EventBus.getInstance().notifySubscriber( project, EventType.PROJECT_LOADED);
        System.out.println("Project opened: " + project.getName());
        return project;
    }
}

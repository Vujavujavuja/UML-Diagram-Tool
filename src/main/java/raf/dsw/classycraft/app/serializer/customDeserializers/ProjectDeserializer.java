package raf.dsw.classycraft.app.serializer.customDeserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;
import raf.dsw.classycraft.app.classyRepository.implementation.ProjectExplorer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDeserializer extends JsonDeserializer<Project> {
    private final ProjectExplorer projectExplorer;
    public ProjectDeserializer(ProjectExplorer projectExplorer) {
        this.projectExplorer = projectExplorer;
    }
    @Override
    public Project deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
        String name = rootNode.get("name").asText();
        String author = rootNode.get("author").asText();

        Project project = new Project(null, name);
        project.setAuthor(author);

        List<ClassyNode> children = new ArrayList<>();
        JsonNode childrenNode = rootNode.get("children");
        if (childrenNode.isArray()) {
            for (JsonNode childNode : childrenNode) {
                Package aPackage = ctxt.readValue(childNode.traverse(jsonParser.getCodec()), Package.class);
                aPackage.setParent(project);
                children.add(aPackage);
            }
        }
        project.setChildren(children);

        return project;
    }
}

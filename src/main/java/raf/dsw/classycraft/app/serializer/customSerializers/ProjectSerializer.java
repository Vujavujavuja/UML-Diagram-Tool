package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyRepository.implementation.Package;
import raf.dsw.classycraft.app.classyRepository.implementation.Project;

import java.io.IOException;

public class ProjectSerializer extends JsonSerializer<Project> {
    private final ObjectMapper objectMapper;
    public ProjectSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public void serialize(Project project, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", project.getName());
        jsonGenerator.writeStringField("author", project.getAuthor());
        jsonGenerator.writeArrayFieldStart("children");
        for (ClassyNode child : project.getChildren()) {
            if (child instanceof ClassyNodeComposite) {
                ClassyNodeComposite composite = (ClassyNodeComposite) child;
                if(composite instanceof Package)
                    objectMapper.writeValue(jsonGenerator, child);
            }
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}

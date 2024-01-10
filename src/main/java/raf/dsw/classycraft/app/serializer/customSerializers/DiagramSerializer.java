package raf.dsw.classycraft.app.serializer.customSerializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import raf.dsw.classycraft.app.classyRepository.implementation.Diagram;
import raf.dsw.classycraft.app.classyRepository.implementation.DiagramElement;

import java.io.IOException;

public class DiagramSerializer extends JsonSerializer<Diagram> {
    @Override
    public void serialize(Diagram diagram, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("typePD", "diagram");

        jsonGenerator.writeStringField("name", diagram.getName());

        jsonGenerator.writeArrayFieldStart("diagramElements");
        for (DiagramElement element : diagram.getDiagramElements()) {
            jsonGenerator.writeObject(element);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
